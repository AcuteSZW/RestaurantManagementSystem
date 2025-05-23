package com.zw.restaurantmanagementsystem.vo;

import lombok.Data;
import java.util.List;

/**
 * 多人会议用户
 * @author: zhongwei
 * @description: 多人会议用户
 */
@Data
public class MultiPersonConferenceUser {
    //用户名,用于展示
    private String username;
    //密码
    private String password;
    //邮箱，用于登录
    private String email;
    //手机号
    private String telephone;
    //会议日期列表
    private List<String> dates;
}
