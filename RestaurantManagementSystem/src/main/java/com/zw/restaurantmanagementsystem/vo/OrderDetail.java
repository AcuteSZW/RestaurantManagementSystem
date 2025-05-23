package com.zw.restaurantmanagementsystem.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 订单详情实体类
 */
@Data // Lombok 自动生成 getter、setter、toString 等方法
@Accessors(chain = true) // 支持链式调用
@TableName("order_detail") // MyBatis-Plus 指定表名
public class OrderDetail {

    @TableId(type = IdType.AUTO) // 主键，自增策略
    private Integer orderDetailId; // 订单详情唯一标识

    private Integer orderId; // 订单ID

    private Integer menuItemId; // 菜品ID

    private Integer quantity; // 菜品数量

    @TableLogic // MyBatis-Plus 逻辑删除注解
    private Integer isDelete; // 逻辑删除标志（0=未删除 1=已删除）
}