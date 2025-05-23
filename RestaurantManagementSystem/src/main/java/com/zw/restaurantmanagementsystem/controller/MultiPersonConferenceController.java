package com.zw.restaurantmanagementsystem.controller;

import com.zw.restaurantmanagementsystem.service.MultiPersonConferenceService;
import com.zw.restaurantmanagementsystem.util.ResponseResult;
import com.zw.restaurantmanagementsystem.vo.MultiPersonConferenceUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 多人会议
 * @author: zhongwei
 * @description: 多人会议
 * @date: 2023/9/23
 */
@Slf4j
@RestController
@RequestMapping("/multiPersonConference")
public class MultiPersonConferenceController {
    @Autowired
    private MultiPersonConferenceService multiPersonConferenceService;

    //多人会议注册
    @PostMapping("/register")
    public ResponseResult<String> register(@RequestBody MultiPersonConferenceUser multiPersonConferenceUser) {
        return null;
    }
    //多人会议登录
    public ResponseResult<String> login(String username, String password) {
        return null;
    }
    //多人会议登出
    public ResponseResult<String> logout(String username) {
        return null;
    }
    //多人会议查询
    public ResponseResult<String> search(String username) {
        return null;
    }
    //多人会议添加
    public ResponseResult<String> add(String username, String password) {
        return null;
    }
    //用户预约会议时间
    public ResponseResult<String> book(String username, String password) {
        return null;
    }
}
