package com.zw.restaurantmanagementsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.restaurantmanagementsystem.vo.Restaurant;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RestaurantMapper extends BaseMapper<Restaurant> {
}
