package com.zw.restaurantmanagementsystem.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体类
 */
@Data // Lombok 自动生成 getter、setter、toString 等方法
@Accessors(chain = true) // 支持链式调用
@TableName("orders") // MyBatis-Plus 指定表名
public class Orders {

    @TableId(type = IdType.AUTO) // 主键，自增策略
    private Integer orderId; // 订单唯一标识

    private Integer userId; // 下单用户ID

    private Integer restaurantId; // 所属餐厅ID

    private BigDecimal totalAmount; // 订单总金额

    private Integer status; // 订单状态（1=待支付 2=已支付 3=已取消）

    private LocalDateTime createdAt; // 创建时间

    @TableLogic // MyBatis-Plus 逻辑删除注解
    private Integer isDelete; // 逻辑删除标志（0=未删除 1=已删除）
}