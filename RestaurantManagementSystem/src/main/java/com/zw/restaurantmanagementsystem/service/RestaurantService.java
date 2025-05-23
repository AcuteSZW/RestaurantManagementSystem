package com.zw.restaurantmanagementsystem.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.zw.restaurantmanagementsystem.dao.RestaurantMapper;
import com.zw.restaurantmanagementsystem.vo.Restaurant;
import com.zw.restaurantmanagementsystem.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RestaurantService {

    @Autowired
    private RestaurantMapper restaurantMapper;

    /**
     * 餐厅搜索
     */
    public List<Restaurant> search(Restaurant restaurant) {
        LambdaQueryWrapper<Restaurant> queryWrapper = new LambdaQueryWrapper<>();
        if (restaurant.getName() != null && !restaurant.getName().isEmpty()) {
            queryWrapper.like(Restaurant::getName,restaurant.getName());
        }
        return restaurantMapper.selectList(queryWrapper);
    }
}
