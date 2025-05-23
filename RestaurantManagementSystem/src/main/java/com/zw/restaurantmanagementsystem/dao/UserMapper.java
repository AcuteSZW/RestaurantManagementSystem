package com.zw.restaurantmanagementsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.restaurantmanagementsystem.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * mybatis提供简单的增删改查
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
//    @Select("SELECT COUNT(*) FROM user WHERE username = #{email}")
//    int countByUsername(String email); // 可在service层判断 >0
}