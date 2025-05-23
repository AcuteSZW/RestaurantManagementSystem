package com.zw.restaurantmanagementsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.restaurantmanagementsystem.vo.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
    int insertBatch(List<OrderDetail> orderDetails);
}
