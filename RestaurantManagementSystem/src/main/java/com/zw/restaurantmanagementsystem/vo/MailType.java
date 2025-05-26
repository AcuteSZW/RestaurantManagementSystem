package com.zw.restaurantmanagementsystem.vo;

import lombok.Getter;

@Getter
public enum MailType {
    REGISTRATION("账户注册", """
        您好！您的验证码为：<%s>
        该验证码用于账号注册/登录验证，有效期为5分钟，请尽快使用。
        请勿将验证码泄露给他人，以保障您的账户安全。
        如非本人操作，请忽略此条信息。感谢您的支持与信任！
    """),
    PASSWORD_RESET("密码找回", """
        您好！您的验证码为：<%s>
        该验证码用于重置密码，有效期为10分钟，请尽快使用。
        请勿将验证码泄露给他人，以保障您的账户安全。
        如非本人操作，请忽略此条信息。感谢您的支持与信任！
    """);

    private final String subject;
    private final String contentTemplate;

    MailType(String subject, String contentTemplate) {
        this.subject = subject;
        this.contentTemplate = contentTemplate;
    }

}