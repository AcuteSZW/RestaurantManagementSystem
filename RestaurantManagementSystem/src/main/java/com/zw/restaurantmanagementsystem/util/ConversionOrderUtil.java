package com.zw.restaurantmanagementsystem.util;

import cn.hutool.core.bean.BeanUtil;
import com.zw.restaurantmanagementsystem.dto.MenuItemDTO;
import com.zw.restaurantmanagementsystem.dto.OrderDTO;
import com.zw.restaurantmanagementsystem.vo.MenuItem;

public class ConversionOrderUtil {
    public static MenuItem convertToVo(OrderDTO orderDTO) {
        return BeanUtil.copyProperties(orderDTO, MenuItem.class);
    }
}
