package com.zw.restaurantmanagementsystem;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.generator.UUIDGenerator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.mail.MailUtil;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.Base64;

public class ManualKeyGenerator {
    @Test
    public void test() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[64];
        random.nextBytes(keyBytes);

        // 将密钥转换为 Base64 编码字符串以便存储
        String encodedKey = Base64.getEncoder().encodeToString(keyBytes);
        System.out.println("Generated Secure Key: " + encodedKey);
    }

    @Test
    public void test2() {
//        UUID uuid = UUID.randomUUID();
        String s = RandomUtil.randomString(8);
        String yourSelfMailAddress = "1296612383@qq.com";
        String mailSubject = "密码找回";
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
        System.out.println(mailContent);
        MailUtil.send(yourSelfMailAddress, mailSubject, mailContent, false);
    }
}