package com.zw.restaurantmanagementsystem.controller;

import com.zw.restaurantmanagementsystem.dto.MenuItemDTO;
import com.zw.restaurantmanagementsystem.dto.RestaurantDTO;
import com.zw.restaurantmanagementsystem.service.DishService;
import com.zw.restaurantmanagementsystem.util.ConversionDishUtil;
import com.zw.restaurantmanagementsystem.util.ResponseResult;
import com.zw.restaurantmanagementsystem.vo.MenuItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据分析模块
 */
@Slf4j
@RestController
@RequestMapping("/dataAnalysis")
public class DataAnalysisController {
    @Autowired
    private DishService dishService;

    //库存监控
    @PostMapping("/inventoryMonitoring")
    public ResponseResult<String> inventoryMonitoring(@RequestBody MenuItemDTO menuItemDTO){
        MenuItem menuItem = ConversionDishUtil.convertToVo(menuItemDTO);
        String s = dishService.inventoryMonitoring(menuItem);
        return ResponseResult.success(s);
    }
    //热门推荐 seasonalRecommendation
    @PostMapping("/hotRecommendation")
    public ResponseResult<List<MenuItem>> hotRecommendation(@RequestBody RestaurantDTO restaurantDTO){
        List<MenuItem> menuItems = dishService.holidayPromotion(restaurantDTO.getRestaurantId());
        return ResponseResult.success(menuItems);
    }
    //节日促销 seasonalPromotion
    @PostMapping("/seasonalPromotion")
    public ResponseResult<String[]> seasonalPromotion(@RequestBody RestaurantDTO restaurantDTO){
        String[] menuItems = dishService.seasonalRecommendation(restaurantDTO.getRestaurantId());
        return ResponseResult.success(menuItems);
    }

    //csv数据转存放到数据库
    @PostMapping("/csvToDatabase")
    public ResponseResult<String> csvToDatabase(){
        int i = dishService.csvToDatabase();
        if (i == 0){
            return ResponseResult.success("数据导入成功");
        }
        return ResponseResult.error(506,"数据导入失败");
    }
}
