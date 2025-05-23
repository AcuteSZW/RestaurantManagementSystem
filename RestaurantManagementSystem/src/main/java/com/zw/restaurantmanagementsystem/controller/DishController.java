package com.zw.restaurantmanagementsystem.controller;

import com.zw.restaurantmanagementsystem.dto.MenuItemDTO;
import com.zw.restaurantmanagementsystem.service.DishService;
import com.zw.restaurantmanagementsystem.util.ConversionDishUtil;
import com.zw.restaurantmanagementsystem.util.ResponseResult;
import com.zw.restaurantmanagementsystem.vo.MenuItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 菜品管理
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping("/search")
    public ResponseResult<List<MenuItem>> searchDish(@RequestBody MenuItemDTO dish) {
        MenuItem menuItem = ConversionDishUtil.convertToVo(dish);
        List<MenuItem> Menus = dishService.search(menuItem);
        return ResponseResult.success(Menus);
    }

    //菜品发布
    @PostMapping("/add")
    public ResponseResult<String> addDish(@RequestBody MenuItemDTO dish) {
        MenuItem menuItem = ConversionDishUtil.convertToVo(dish);
        int result = dishService.insertDish(menuItem);
        if (result > 0) {
            return ResponseResult.success("The dish was successfully released");
        } else {
            return ResponseResult.error(500, "Dishes publishing failed");
        }
    }

    //菜品修改
    @PostMapping("/update")
    public ResponseResult<String> updateDish(@RequestBody MenuItemDTO dish) {
        MenuItem menuItem = ConversionDishUtil.convertToVo(dish);
        int result = dishService.updateDish(menuItem);
        if (result > 0) {
            return ResponseResult.success("The dish was modified successfully");
        } else {
            return ResponseResult.error(500, "Failed to modify the dish");
        }
    }

    //菜品删除
    @PostMapping("/delete")
    public ResponseResult<String> deleteDish(@RequestBody Integer dishId) {
        int result = dishService.deleteDish(dishId);
        if (result > 0) {
            return ResponseResult.success("The dish was deleted successfully");
        } else {
            return ResponseResult.error(500, "Failed to delete the dish");
        }
    }

}
