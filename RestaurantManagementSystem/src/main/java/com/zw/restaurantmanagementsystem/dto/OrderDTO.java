package com.zw.restaurantmanagementsystem.dto;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.zw.restaurantmanagementsystem.vo.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Integer userId; // 下单用户ID

    private Integer orderId;

    private Integer restaurantId; // 所属餐厅ID

    private BigDecimal totalAmount; // 订单总金额

    private Integer status; // 订单状态（1=待支付 2=已支付 3=已取消）

    private LocalDateTime createdAt; // 创建时间

    private List<OrderDetail> orderDetails;
}
