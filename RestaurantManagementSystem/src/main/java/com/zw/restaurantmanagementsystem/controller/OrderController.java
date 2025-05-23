package com.zw.restaurantmanagementsystem.controller;

import com.zw.restaurantmanagementsystem.dto.OrderDTO;
import com.zw.restaurantmanagementsystem.service.OrderService;
import com.zw.restaurantmanagementsystem.util.ResponseResult;
import com.zw.restaurantmanagementsystem.vo.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 订单控制层
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/search")
    public ResponseResult<List<Orders>> searchOrder(@RequestBody OrderDTO order) {
        List<Orders> ordersList = orderService.searchOrder(order);
        return ResponseResult.success(ordersList);
    }

    @PostMapping("/add")
    public ResponseResult<String> addOrder(@RequestBody OrderDTO order) {
        String s = orderService.insertOrder(order);
        return ResponseResult.success(s);
    }

    @PostMapping("/delete")
    public ResponseResult<String> deleteOrder(@RequestBody OrderDTO order) {
        String s = orderService.cancelOrder(order);
        return ResponseResult.success(s);
    }

    //订单支付
    @PostMapping("/pay")
    public ResponseResult<String> payOrder(@RequestBody OrderDTO orderDTO) {
        String s = orderService.payOrder(orderDTO);
        return ResponseResult.success(s);
    }

}
