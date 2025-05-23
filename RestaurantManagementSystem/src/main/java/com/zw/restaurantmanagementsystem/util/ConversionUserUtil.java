package com.zw.restaurantmanagementsystem.util;

import cn.hutool.core.bean.BeanUtil;
import com.zw.restaurantmanagementsystem.dto.LoginDTO;
import com.zw.restaurantmanagementsystem.dto.UserDTO;
import com.zw.restaurantmanagementsystem.vo.User;

public class ConversionUserUtil {

    public static User convertToVo(UserDTO userDTO) {
        return BeanUtil.copyProperties(userDTO, User.class);
    }

    public static UserDTO userResponseDTO(User userDO){
        UserDTO userResponseDTO = BeanUtil.copyProperties(userDO, UserDTO.class);

        return userResponseDTO;
    }

    public static User convertToVo(LoginDTO loginDTO) {
        return BeanUtil.copyProperties(loginDTO, User.class);
    }

    public static LoginDTO loginDTO(User userDO){
        LoginDTO userResponseDTO = BeanUtil.copyProperties(userDO, LoginDTO.class);

        return userResponseDTO;
    }
}
