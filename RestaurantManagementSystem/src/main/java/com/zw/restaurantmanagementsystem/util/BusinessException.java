package com.zw.restaurantmanagementsystem.util;

import org.slf4j.MDC;

// 自定义业务异常类
public class BusinessException extends RuntimeException {

    private final String traceId; // 调用链路ID
    private final String message; // 错误信息
    private final int code;       // 错误码，默认值为600

    // 构造函数：传入错误信息，默认code为600
    public BusinessException(String message) {
        this(600, message); // 默认code为600
    }

    // 构造函数：传入错误码和错误信息
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        this.traceId = MDC.get("traceId"); // 从MDC中获取traceId
    }

    // 获取traceId
    public String getTraceId() {
        return traceId;
    }

    // 获取错误信息
    @Override
    public String getMessage() {
        return message;
    }

    // 获取错误码
    public int getCode() {
        return code;
    }
}