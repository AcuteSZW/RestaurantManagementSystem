package com.zw.restaurantmanagementsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.restaurantmanagementsystem.vo.CsvData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CsvUserMapper extends BaseMapper<CsvData> {
    @Select("SELECT COUNT(*) FROM information_schema.tables WHERE table_name = #{tableName}")
    int countByUsername(String tableName); // 可在service层判断 >0

    //创建表
    @Select("CREATE TABLE IF NOT EXISTS ${tableName} (id INT AUTO_INCREMENT PRIMARY KEY, telephone VARCHAR(20),name VARCHAR(10), company VARCHAR(60))")
    void createTable(String tableName);

    //批量插入
    void insertBath(List<CsvData> data, String tableName);
}
