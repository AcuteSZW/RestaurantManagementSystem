package com.zw.restaurantmanagementsystem.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zw.restaurantmanagementsystem.dao.MultiPersonConferenceUserMapper;
import com.zw.restaurantmanagementsystem.dto.LoginDTO;
import com.zw.restaurantmanagementsystem.dto.MultiPersonConferenceUserDTO;
import com.zw.restaurantmanagementsystem.util.*;
import com.zw.restaurantmanagementsystem.vo.MailType;
import com.zw.restaurantmanagementsystem.vo.MultiPersonConferenceUser;
import com.zw.restaurantmanagementsystem.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 多人会议服务
 */
@Service
@Slf4j
public class MultiPersonConferenceService {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MultiPersonConferenceUserMapper multiPersonConferenceUserMapper;

    /**
     * 注册
     *
     * @param multiPersonConferenceUser
     * @return
     */
    public String register(MultiPersonConferenceUser multiPersonConferenceUser) {
        try {
            // 检查用户唯一性（假设已实现根据email查询的方法）
            if (existsByUsername(multiPersonConferenceUser.getEmail())) {
                return ExceptionUtil.UserMessage.EMAIL_EXIST;
            }

            int insert = multiPersonConferenceUserMapper.insert(multiPersonConferenceUser);
            return insert == 1 ? ExceptionUtil.UserMessage.REGISTER_SUCCESS : ExceptionUtil.UserMessage.REGISTER_FAILED;
        } catch (Exception e) {
            // 记录异常日志
            log.error("Registration failed", e);
            return ExceptionUtil.SystemMessage.SERVER_ERROR;
        }
    }

    public String sendEmail(MultiPersonConferenceUserDTO multiPersonConferenceUserDTO) {
        //判断类型组装邮件内容
        return switch (multiPersonConferenceUserDTO.getSendType()) {
            case "1" -> sendTypeEmail(multiPersonConferenceUserDTO, MailType.REGISTRATION);
            case "2" -> sendTypeEmail(multiPersonConferenceUserDTO, MailType.PASSWORD_RESET);
            default -> ExceptionUtil.UserMessage.SEND_TYPE_ERROR;
        };
    }

    /**
     * 登录
     * @param multiPersonConferenceUser
     * @return
     */
    public String login(MultiPersonConferenceUser multiPersonConferenceUser) {
        //参数校验
        if (StringUtils.isBlank(multiPersonConferenceUser.getEmail())) {
            throw new BusinessException(ExceptionUtil.UserMessage.EMAIL_NOT_NULL);
        }
        if (StringUtils.isBlank(multiPersonConferenceUser.getPassword())) {
            throw new BusinessException(ExceptionUtil.UserMessage.PASSWORD_NOT_NULL);
        }

        // 构建MP查询条件
        QueryWrapper<MultiPersonConferenceUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.nested(wq -> wq
                .eq("email", multiPersonConferenceUser.getEmail())
                .eq("password", multiPersonConferenceUser.getPassword())
        );

        // 查询用户
        MultiPersonConferenceUser dbUser = multiPersonConferenceUserMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNull(dbUser)) {
            throw new BusinessException(ExceptionUtil.UserMessage.USER_NOT_FOUND);
        }

        // 生成JWT
        String token = jwtUtil.generateToken(dbUser.getUsername());
        //登录后有一小时的有效期
        redisUtil.setEx(multiPersonConferenceUser.getEmail(),  token, 60 * 60, TimeUnit.SECONDS);

        return token;
    }

    public String logout(String username) {
        return "登出成功";
    }

    public String search(String username) {
        return "查询成功";
    }

    public String add(String username, String password) {
        return "添加成功";
    }

    public String book(String username, String password) {
        return "预定成功";
    }

    /**
     * 如果用户存在
     *
     * @param email
     * @return
     */
    public boolean existsByUsername(String email) {
        // 方式2：Lambda表达式（推荐）
        return multiPersonConferenceUserMapper.selectCount(new LambdaQueryWrapper<MultiPersonConferenceUser>()
                .eq(MultiPersonConferenceUser::getEmail, email)) > 0;
    }

    /**
     * 发送邮件
     *
     * @param user
     * @return
     */
    private String sendTypeEmail(MultiPersonConferenceUserDTO user, MailType mailType) {
        if (StrUtil.isBlank(user.getEmail())) {
            return "请输入您的电子邮件地址";
        }

        String code = RandomUtil.randomString(8);
        String email = user.getEmail();
        String subject = mailType.getSubject();
        String content = mailType.getContentTemplate().formatted(code);

        // 存储到 Redis
        redisUtil.setEx(email, code, 5, TimeUnit.MINUTES);

        // 发送邮件
        MailUtil.send(email, subject, content, false);

        return "电子邮件发送成功";
    }
}
