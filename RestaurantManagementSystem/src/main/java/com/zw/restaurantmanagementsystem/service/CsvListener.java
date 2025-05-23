package com.zw.restaurantmanagementsystem.service;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.zw.restaurantmanagementsystem.dao.CsvUserMapper;
import com.zw.restaurantmanagementsystem.util.RedisUtil;
import com.zw.restaurantmanagementsystem.vo.ConsistentHashRing;
import com.zw.restaurantmanagementsystem.vo.CsvData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CsvListener extends AnalysisEventListener<CsvData> {
    private static final int BATCH_SIZE = 100000;
    private final List<CsvData> dataList = new ArrayList<>();
    private final List<String> nodes = getNodes(40);
    private final ConsistentHashRing<String, CsvData> ring = new ConsistentHashRing<>(nodes, 500);

    @Autowired
    private CsvUserMapper csvUserMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void invoke(CsvData data, AnalysisContext context) {
        dataList.add(data);
        if (dataList.size() >= BATCH_SIZE) {
            saveData();
            dataList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (!dataList.isEmpty()) {
            saveData(); // 处理剩余数据
            dataList.clear();
        }
        System.out.println("所有数据解析完成！");
    }

    private void saveData() {
        for (CsvData data : dataList) {
            ring.put(data.getTelephone(), data);
        }

//        System.out.println("初始化后各节点数据：");
        nodes.forEach(node -> {
//            printNodeData(ring, node);
            List<CsvData> data = ring.getDataForNode(node);
            if (isExistsTable(node)){
                //表存在就插入
                csvUserMapper.insertBath(data,node);
            }
            if (!isExistsTable(node)){
                //表不存在就先创建表然后再插入
                csvUserMapper.createTable(node);
                redisUtil.setEx(node, "1", 5, TimeUnit.MINUTES);
                csvUserMapper.insertBath(data,node);
            }

        });

        System.out.println("已存储 " + dataList.size() + " 条数据");
    }

    /**
     * 检查数据库中是否存在该表记录
     * @param name
     * @return
     */
    private boolean isExistsTable(String name) {
        if (redisUtil.hasKey(name)) {
            long expire = redisUtil.getExpire(name, TimeUnit.MINUTES);
            if (expire < 2) {
                redisUtil.expire(name, 5, TimeUnit.MINUTES);
            }

            return true;
        }

        // 检查数据库中是否存在该表记录
        int i = csvUserMapper.countByUsername(name);
        if (i > 0) {
            redisUtil.setEx(name, "1", 5, TimeUnit.MINUTES);
            return true;
        }

        return false;
    }

    /**
     * 生成num个虚拟节点
     * @param num 需要生成的虚拟节点数（应>=0）
     * @return 虚拟节点列表
     * @throws IllegalArgumentException 当num为负数时抛出
     */
    private static List<String> getNodes(int num) {
        List<String> nodes = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            nodes.add("user_" + i);
        }
        return nodes;
    }

    // 辅助方法：打印节点数据
    private static void printNodeData(ConsistentHashRing<String, CsvData> ring, String node) {
        List<CsvData> data = ring.getDataForNode(node);
//        System.out.println("节点 " + node + " 的数据数量：" + data.size());
//        for (CsvData d : data) {
//            System.out.println("姓名：" + d.getName() + "，电话：" + d.getTelephone() + "，公司：" + d.getCompany());
//        }
    }
}
