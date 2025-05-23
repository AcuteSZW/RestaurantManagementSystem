package com.zw.restaurantmanagementsystem.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zw.restaurantmanagementsystem.dao.OrderDetailMapper;
import com.zw.restaurantmanagementsystem.dao.OrderMapper;
import com.zw.restaurantmanagementsystem.dto.OrderDTO;
import com.zw.restaurantmanagementsystem.vo.OrderDetail;
import com.zw.restaurantmanagementsystem.vo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    //查询订单
    public List<Orders> searchOrder(OrderDTO orderDTO) {
        Orders orders = new Orders();
        orders.setUserId(orderDTO.getUserId());
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId, orderDTO.getUserId());
        List<Orders> ordersList = orderMapper.selectList(queryWrapper);
        if (CollUtil.isEmpty(ordersList)) {
            return new ArrayList<>();
        }
        return ordersList;
    }
    //取消订单
    public String cancelOrder(OrderDTO orderDTO) {
        Orders order = orderMapper.selectById(orderDTO.getRestaurantId());
        if (order == null) {
            return "The order does not exist";
        }
        if (order.getStatus() != 1) {
            return "Order status error";
        }
        order.setStatus(3); // 设置订单状态为已取消 (3=已取消)
        orderMapper.updateById(order);
        return "success cancel";
    }
    //订单支付
    public String payOrder(OrderDTO orderDTO) {
        Orders order = orderMapper.selectById(orderDTO.getOrderId());
        if (order == null) {
            return "The order does not exist";
        }
        if (order.getStatus() != 1) {
            return "Order status error";
        }
        order.setStatus(2); // 设置订单状态为已支付 (2=已支付)
        orderMapper.updateById(order);
        return "success pay";
    }
    //创建订单
    @Transient
    public String insertOrder(OrderDTO orderDTO) {
        try {
            // 1. 创建订单主表记录
            Orders order = new Orders()
                    .setUserId(orderDTO.getUserId())
                    .setRestaurantId(orderDTO.getRestaurantId())
                    .setTotalAmount(orderDTO.getTotalAmount())
                    .setStatus(1) // 默认状态为待支付 (1=待支付)
                    .setCreatedAt(LocalDateTime.now())
                    .setIsDelete(0); // 未删除

            orderMapper.insert(order); // 插入订单主表

            // 2. 获取生成的订单ID
            Integer orderId = order.getOrderId();

            if (orderId == null) {
                throw new RuntimeException("订单ID生成失败");
            }
            List<OrderDetail> orderDetails = orderDTO.getOrderDetails();
            // 3. 创建订单详情记录
            for (OrderDetail detail : orderDetails) {
                detail.setOrderId(orderId)
                        .setIsDelete(0); // 未删除
            }
            ListUtil.partition(orderDetails, 100).forEach(batch -> {
                orderDetailMapper.insertBatch(batch);
            });
        } catch (Exception e) {
            // 捕获异常并回滚事务
            throw new RuntimeException("creat order faild：" + e.getMessage(), e);
        }
        return "success create";
    }
}
