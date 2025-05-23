package com.zw.restaurantmanagementsystem.controller;

import com.zw.restaurantmanagementsystem.dto.LoginDTO;
import com.zw.restaurantmanagementsystem.dto.UserDTO;
import com.zw.restaurantmanagementsystem.service.UserService;
import com.zw.restaurantmanagementsystem.util.ConversionUserUtil;
import com.zw.restaurantmanagementsystem.util.ResponseResult;
import com.zw.restaurantmanagementsystem.util.SecurityUtil;
import com.zw.restaurantmanagementsystem.vo.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 用户控制器
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // 用户注册
    @PostMapping("/register")
    public ResponseResult<String> register(@Valid @RequestBody UserDTO userDTO) { // 添加参数校验
        User user = ConversionUserUtil.convertToVo(userDTO);
        String registerMessage = userService.register(user);
        return ResponseResult.success(registerMessage); // 使用统一响应结构
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ResponseResult<LoginDTO> login(@RequestBody LoginDTO loginDTO) {
        User user = ConversionUserUtil.convertToVo(loginDTO);
        LoginDTO login = userService.login(user);
        return ResponseResult.success(login);
    }
    /**
     * 邮件发送
     */
    @PostMapping("/forgot-password/send-email")
    public ResponseResult<String> sendResetPasswordEmail(@RequestBody UserDTO userDTO) {
        User user = ConversionUserUtil.convertToVo(userDTO);
        String message = userService.sendResetPasswordEmail(user);
        return ResponseResult.success(message);
    }
    /**NULL
     * 密码找回
     */
    @PostMapping("/forgot-password")
    public ResponseResult<String> forgotPassword(@RequestBody UserDTO userDTO) {
        User user = ConversionUserUtil.convertToVo(userDTO);
        String message = userService.forgotPassword(user);
        return ResponseResult.success(message);
    }

    @GetMapping("/profile")
    public ResponseResult<Object> getUserProfile(HttpServletRequest request) {
        SecurityUtil.checkLogin(request);
        String username = SecurityUtil.getCurrentUser(request);
        // 查询用户信息...
        return ResponseResult.success(username);
    }

}
