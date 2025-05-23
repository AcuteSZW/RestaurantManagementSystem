package com.zw.restaurantmanagementsystem.util;

import cn.hutool.core.bean.BeanUtil;
import com.zw.restaurantmanagementsystem.dto.RestaurantDTO;
import com.zw.restaurantmanagementsystem.vo.Restaurant;

public class ConversionRestaurantUtil {

    public static Restaurant convertToVo(RestaurantDTO restaurantDTO) {
        return BeanUtil.copyProperties(restaurantDTO, Restaurant.class);
    }

    public static RestaurantDTO userResponseDTO(Restaurant restaurant){
        return BeanUtil.copyProperties(restaurant, RestaurantDTO.class);
    }
}
