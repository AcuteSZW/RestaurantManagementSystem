package com.zw.restaurantmanagementsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.restaurantmanagementsystem.vo.MenuItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper  extends BaseMapper<MenuItem> {

}
