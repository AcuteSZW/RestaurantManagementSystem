package com.zw.restaurantmanagementsystem.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// 餐厅实体类
@Data
@TableName("restaurant")
public class Restaurant {

    // 餐厅唯一标识
    @TableId(value="restaurant_id",type= IdType.AUTO)
    private Integer restaurantId;

    // 餐厅名称
    private String name;

    // 餐厅地址
    private String address;

    // 餐厅所在城市
    private String city;

    // 邮政编码
    private String postalCode;

    // 联系电话
    private String phoneNumber;

    // 电子邮件
    private String email;

    // 餐厅官网链接
    private String website;

    // 纬度（用于地图定位）
    private BigDecimal latitude;

    // 经度（用于地图定位）
    private BigDecimal longitude;

    // 餐厅简介
    private String description;

    // 主打菜系（如中餐、西餐等）
    private String cuisineType;

    // 最大容纳人数
    private Integer capacity;

    // 营业时间（JSON格式存储每天的营业时间）
    private String openingHours; // JSON字符串形式存储

    // 平均评分（用户评价）
    private BigDecimal averageRating;

    // 价格区间（如￥、￥￥、￥￥￥）
    private String priceRange;

    // 创建时间
    private LocalDateTime createdAt;

    // 更新时间
    private LocalDateTime updatedAt;

    @TableLogic
    @TableField(fill = FieldFill.INSERT_UPDATE,select = false)//自动填充，影响插入和更新,select = false查询忽略
    private int isDelete;
}