package com.zw.restaurantmanagementsystem.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zw.restaurantmanagementsystem.dao.DishMapper;
import com.zw.restaurantmanagementsystem.vo.CsvData;
import com.zw.restaurantmanagementsystem.vo.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private CsvListener csvListener;

    public int csvToDatabase() {
        //读取I:\python\data\csv文件夹中所有的csv文件,将所有文件名装到一个list里面
        List<String> strings = readFile();
        if (strings == null || strings.isEmpty()) {
            return 1;
        }
        //将里面的文件数据读取存放数据库
        // 读取 CSV 文件
        strings.forEach(fileName -> {
            EasyExcel.read("I:\\python\\data\\csv\\"+fileName, CsvData.class, csvListener)
                    .excelType(ExcelTypeEnum.CSV)  // 指定文件类型为 CSV
//                .charset("UTF-8")  // 避免乱码（根据文件实际编码修改）
                    .sheet()  // 指定工作表（默认第一个 sheet）
                    .doRead();
        });

        return 0;
    }

    private List<String> readFile() {
        String folderPath = "I:/python/data/csv";

        try {
            List<String> csvFiles = Files.list(Path.of(folderPath))
                    .filter(path -> path.toString().endsWith(".csv"))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .toList();

            System.out.println("发现CSV文件：" + csvFiles);
            return csvFiles;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Seasonal Recommendation
     *
     * @param restaurantId The ID of the restaurant
     * @return A list of recommended dishes
     */
    public String[] seasonalRecommendation(int restaurantId) {
        // Get the current month
        Month currentMonth = LocalDate.now().getMonth();

        // Define recommended categories based on the season
        String[] recommendedCategories;
        if (currentMonth == Month.MARCH || currentMonth == Month.APRIL || currentMonth == Month.MAY) {
            recommendedCategories = new String[]{"Light", "Cold Dishes", "Drinks"}; // Spring
        } else if (currentMonth == Month.JUNE || currentMonth == Month.JULY || currentMonth == Month.AUGUST) {
            recommendedCategories = new String[]{"Cold Drinks", "Desserts", "Noodles"}; // Summer
        } else if (currentMonth == Month.SEPTEMBER || currentMonth == Month.OCTOBER || currentMonth == Month.NOVEMBER) {
            recommendedCategories = new String[]{"Nourishing Soups", "Roast Duck", "Pumpkin Cake"}; // Autumn
        } else {
            recommendedCategories = new String[]{"Hot Pot", "Stews", "Hot Drinks"}; // Winter
        }

        // Query dishes that match the criteria
//        LambdaQueryWrapper<MenuItem> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(MenuItem::getRestaurantId, restaurantId)
//                .in(StringUtils.isNotBlank(recommendedCategories[0]), MenuItem::getCategory, recommendedCategories)
//                .gt(MenuItem::getQuantity, 0); // Only recommend dishes with available stock

        return recommendedCategories;
    }

    /**
     * Holiday Promotion
     *
     * @param restaurantId The ID of the restaurant
     * @return A list of promotional dishes
     */
    public List<MenuItem> holidayPromotion(int restaurantId) {
        // Assume today is a holiday like New Year's Day or Mid-Autumn Festival
        LocalDate today = LocalDate.now();
        boolean isHoliday = today.getDayOfMonth() == 1 && today.getMonth() == Month.JANUARY; // Example: New Year's Day

//        if (!isHoliday) {
//            return List.of(); // Return an empty list if it's not a holiday
//        }

        // Query dishes suitable for holidays (e.g., dumplings, mooncakes)
        LambdaQueryWrapper<MenuItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MenuItem::getRestaurantId, restaurantId)
                .in(MenuItem::getName, "Dumplings", "Rice Cakes", "Fish", "Mooncakes") // Example: New Year/Mid-Autumn Festival related dishes
                .gt(MenuItem::getQuantity, 0); // Only recommend dishes with available stock

        return dishMapper.selectList(queryWrapper);
    }

    //库存监控
    public String inventoryMonitoring(MenuItem dishDTO) {
        LambdaQueryWrapper<MenuItem> queryWrapper = new LambdaQueryWrapper<>();
        if (dishDTO.getRestaurantId() != null) {
            queryWrapper.eq(MenuItem::getRestaurantId, dishDTO.getRestaurantId());
        }
        List<MenuItem> menuItems = dishMapper.selectList(queryWrapper);
        StringBuilder result = new StringBuilder();
        for (MenuItem menuItem : menuItems) {
            if (menuItem.getQuantity() < 10) {
                result.append(menuItem.getName()).append(": ").append("Inventory is less than 10, current quantity:").append(menuItem.getQuantity()).append("    \n");
            }
        }
        if (StrUtil.isBlank(result.toString())){
            result = new StringBuilder("Inventory sufficient");
        }
        return result.toString();
    }
    public int insertDish(MenuItem dish) {
        return dishMapper.insert(dish);
    }
    public int updateDish(MenuItem dish) {
        return dishMapper.updateById(dish);
    }
    public int deleteDish(Integer dishId) {
        return dishMapper.deleteById(dishId);
    }
    public List<MenuItem> search(MenuItem menuItem) {
        LambdaQueryWrapper<MenuItem> queryWrapper = new LambdaQueryWrapper<>();
        if (menuItem.getName() != null && !menuItem.getName().isEmpty()) {
            queryWrapper.like(MenuItem::getName,menuItem.getName());
        }
        if (menuItem.getRestaurantId() != null) {
            queryWrapper.eq(MenuItem::getRestaurantId,menuItem.getRestaurantId());
        }

        return dishMapper.selectList(queryWrapper);
    }
}
