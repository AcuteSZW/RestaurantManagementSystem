package com.zw.restaurantmanagementsystem.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zw.restaurantmanagementsystem.dao.UserMapper;
import com.zw.restaurantmanagementsystem.dto.LoginDTO;
import com.zw.restaurantmanagementsystem.util.*;
import com.zw.restaurantmanagementsystem.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserService {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserMapper userMapper;

    public String forgotPassword(User user) {
        if (user.getEmail() == null){
            return "Please enter your email address";
        }
        if (user.getCode() == null){
            return "Please enter the verification code";
        }
        if (user.getPasswordHash() == null){
            return "Please enter your new password";
        }
        try {
            String email = user.getEmail();
            String storedCode = redisUtil.get(email);
            boolean isValid = StrUtil.equals(storedCode, user.getCode());

            if (!isValid) {
                return "Please check if the verification code has expired or not. The verification code does not match.";
            }

            // 立即删除已使用的验证码
            redisUtil.delete(email);

            LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
            int updated = userMapper.update(updateWrapper
                    .set(User::getPasswordHash, user.getPasswordHash())
                    .eq(User::getEmail, email)
                    // 确保邮箱存在且仅更新一条记录
                    .last("LIMIT 1"));

            return updated > 0 ? "update success" : "update fail";
        } catch (Exception e) {
            // 记录日志
            return "fail";
        }
    }

    public String sendResetPasswordEmail(User user){
        if (user.getEmail() != null){
            String s = RandomUtil.randomString(8);
            String yourSelfMailAddress = user.getEmail();
            String mailSubject = "Password retrieval(密码找回)";
            String mailContent = """ 
            [Restaurant Management System] Dear user, hello! Your verification code is: <%s>
            This verification code is used for account registration/login verification and is valid for 5 minutes. Please use it as soon as possible.
            Please do not disclose the verification code to others to ensure the security of your account.
            If it is not your own operation, please ignore this message. Thank you for your support and trust in [Restaurant Management System]!
            
            【餐厅管理系统】尊敬的用户，您好！您的验证码为：<%s>
            该验证码用于账号注册/登录验证，有效期为5分钟，请尽快使用。
            请勿将验证码泄露给他人，以保障您的账户安全。
            如非本人操作，请忽略此条信息。感谢您对【餐厅管理系统】的支持与信任！
            """.formatted(s, s);  // 使用双占位符
            redisUtil.setEx(user.getEmail(), s, 5, TimeUnit.MINUTES);
            MailUtil.send(yourSelfMailAddress, mailSubject, mailContent, false);

            //发送邮件
            return "email send success";
        }
        return "Please enter your email address";
    }
    public LoginDTO login(User user){
        //参数校验
        if (StringUtils.isBlank(user.getEmail())) {
            throw new BusinessException(ExceptionUtil.UserMessage.EMAIL_NOT_NULL);
        }
        if (StringUtils.isBlank(user.getPasswordHash())) {
            throw new BusinessException(ExceptionUtil.UserMessage.PASSWORD_NOT_NULL);
        }

        // 构建MP查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.nested(wq -> wq
                .eq("email", user.getEmail())
        );

        // 查询用户
        User dbUser = userMapper.selectOne(queryWrapper);
        if (dbUser == null) {
            throw new BusinessException(ExceptionUtil.UserMessage.USER_NOT_FOUND);
        }

        // 直接比较密码哈希值（根据需求调整）
        if (!StrUtil.equals(dbUser.getPasswordHash(),user.getPasswordHash())) {
            throw new BusinessException(ExceptionUtil.UserMessage.PASSWORD_ERROR);
        }

        // 生成JWT
        String token = jwtUtil.generateToken(dbUser.getUsername());

        // 转换返回结果
        LoginDTO loginDTO = ConversionUserUtil.loginDTO(dbUser);
        loginDTO.setToken(token);
        loginDTO.setPasswordHash(null);
        return loginDTO;
    }

    //用户注册
    public String register(User user) {
        try {
            // 检查用户唯一性（假设已实现根据email查询的方法）
            if (existsByUsername(user.getEmail())) {
                return ExceptionUtil.UserMessage.EMAIL_EXIST;
            }
            int insert = userMapper.insert(user);
            return insert == 1 ? ExceptionUtil.UserMessage.REGISTER_SUCCESS: ExceptionUtil.UserMessage.REGISTER_FAILED;
        } catch (Exception e) {
            // 记录异常日志
            log.error("Registration failed", e);
            return ExceptionUtil.SystemMessage.SERVER_ERROR;
        }
    }


    public boolean existsByUsername(String email) {
        // 方式2：Lambda表达式（推荐）
        return userMapper.selectCount(new LambdaQueryWrapper<User>()
                .eq(User::getEmail, email)) > 0;
    }



}
