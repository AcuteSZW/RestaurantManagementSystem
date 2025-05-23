package com.zw.restaurantmanagementsystem.controller;

import com.zw.restaurantmanagementsystem.dto.RestaurantDTO;
import com.zw.restaurantmanagementsystem.service.RestaurantService;
import com.zw.restaurantmanagementsystem.util.ConversionRestaurantUtil;
import com.zw.restaurantmanagementsystem.util.ResponseResult;
import com.zw.restaurantmanagementsystem.vo.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 餐厅控制器
 */
@Slf4j
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    /**
     * 餐厅搜索
     */
    @RequestMapping("/search")
    public ResponseResult<List<Restaurant>> search(@RequestBody RestaurantDTO restaurantDTO) {
        Restaurant restaurant = ConversionRestaurantUtil.convertToVo(restaurantDTO);
        log.info("餐厅搜索");
        List<Restaurant> search = restaurantService.search(restaurant);
        return ResponseResult.success(search);
    }
}
