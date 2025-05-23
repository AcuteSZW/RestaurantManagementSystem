package com.zw.restaurantmanagementsystem.util;

import cn.hutool.core.bean.BeanUtil;
import com.zw.restaurantmanagementsystem.dto.MenuItemDTO;
import com.zw.restaurantmanagementsystem.dto.RestaurantDTO;
import com.zw.restaurantmanagementsystem.vo.MenuItem;
import com.zw.restaurantmanagementsystem.vo.Restaurant;

public class ConversionDishUtil {
    public static MenuItem convertToVo(MenuItemDTO menuItemDTO) {
        return BeanUtil.copyProperties(menuItemDTO, MenuItem.class);
    }
}
