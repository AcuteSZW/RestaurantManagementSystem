package com.zw.restaurantmanagementsystem;

import com.zw.restaurantmanagementsystem.vo.ConsistentHashRing;
import com.zw.restaurantmanagementsystem.vo.CsvData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 初始化节点（例如2个节点）
        List<String> nodes = getNodes(2);

        // 创建哈希环（每个节点2个虚拟节点）
        ConsistentHashRing<String, CsvData> ring = new ConsistentHashRing<>(nodes, 500);

        // 创建测试数据
        List<CsvData> csvDataList = getTestData(10);

        // 将数据插入哈希环
        for (CsvData data : csvDataList) {
            ring.put(data.getTelephone(), data);
        }

        // 查询各节点数据
        System.out.println("初始化后各节点数据：");
        nodes.forEach(node -> {
            printNodeData(ring, node);
        });
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
            nodes.add("node_" + i);
        }
        return nodes;
    }

    /**
     * 生成num条数据（确保手机号唯一）
     * @param num 需要生成的数据条数（应>=0）
     * @return 包含唯一手机号的测试数据列表
     * @throws IllegalArgumentException 当num为负数时抛出
     */
    private static List<CsvData> getTestData(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("生成数量不能为负数");
        }

        List<CsvData> csvDataList = new ArrayList<>(num);
        long basePhoneNumber = 138_0000_0000L; // 基准手机号

        for (int i = 0; i < num; i++) {
            // 生成唯一手机号：基准号+序号
            String uniquePhone = String.valueOf(basePhoneNumber + i);
            csvDataList.add(new CsvData("测试用户" + i, uniquePhone, "测试公司" + i));
        }
        return csvDataList;
    }



    // 辅助方法：打印节点数据
    private static void printNodeData(ConsistentHashRing<String, CsvData> ring, String node) {
        List<CsvData> data = ring.getDataForNode(node);
        System.out.println("节点 " + node + " 的数据数量：" + data.size());
        for (CsvData d : data) {
            System.out.println("姓名：" + d.getName() + "，电话：" + d.getTelephone() + "，公司：" + d.getCompany());
        }
    }
}