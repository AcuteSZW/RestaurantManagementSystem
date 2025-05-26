package com.zw.restaurantmanagementsystem.util;

// 异常消息常量工具类
public final class ExceptionUtil {
    private ExceptionUtil() {} // 禁止实例化

    // 用户相关异常消息
    public static final class UserMessage {
        public static final String REGISTER_FAILED = "Failed register";
        public static final String REGISTER_SUCCESS = "Success register";
        public static final String EMAIL_EXIST = "Registration failed. The email address already exists";
        public static final String LOGIN_FAILED = "Invalid username or password";
        public static final String USER_NOT_FOUND = "User not found";
        public static final String EMAIL_NOT_NULL = "email is not null";
        public static final String USERNAME_NOT_NULL = "username is not null";
        //email和userName为空
        public static final String EMAIL_AND_USERNAME_NOT_NULL = "email or username is not null";
        public static final String PASSWORD_NOT_NULL = "password is not null";
        //password错误
        public static final String PASSWORD_ERROR = "password error";
        public static final String SEND_TYPE_ERROR = "send error";
    }

    // 系统通用异常消息
    public static final class SystemMessage {
        public static final String SERVER_ERROR = "System error, please contact administrator";
        public static final String PARAMS_INVALID = "Invalid request parameters";
        public static final String DATA_DUPLICATE = "Data duplication detected";
    }

    // 订单相关异常消息（示例扩展）
    public static final class OrderMessage {
        public static final String OUT_OF_STOCK = "Insufficient inventory";
        public static final String PAYMENT_FAILED = "Payment processing failed";
    }
}