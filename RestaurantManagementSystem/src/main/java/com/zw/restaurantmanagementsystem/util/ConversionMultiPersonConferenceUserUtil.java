package com.zw.restaurantmanagementsystem.util;

import cn.hutool.core.bean.BeanUtil;
import com.zw.restaurantmanagementsystem.dto.MultiPersonConferenceUserDTO;
import com.zw.restaurantmanagementsystem.vo.MultiPersonConferenceUser;

public class ConversionMultiPersonConferenceUserUtil {
    public static MultiPersonConferenceUser convertToVo(MultiPersonConferenceUserDTO multiPersonConferenceUserDTO) {
        return BeanUtil.copyProperties(multiPersonConferenceUserDTO, MultiPersonConferenceUser.class);
    }
}
