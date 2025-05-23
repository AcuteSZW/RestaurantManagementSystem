package com.zw.restaurantmanagementsystem.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CsvData {
    @ExcelProperty("姓名")  // CSV文件中的第一列列名
    private String name;
    
    @ExcelProperty("电话")  // CSV文件中的第二列列名
    private String telephone;
    
    @ExcelProperty("公司")  // CSV文件中的第三列列名
    private String company;
}