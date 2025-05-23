package com.zw.restaurantmanagementsystem.util;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import org.slf4j.MDC;

@Getter
@Setter
@Accessors(chain = true)
public class ResponseResult<T> {
    private int code;
    private String message;
    private T data;
    private String traceId;  // 链路追踪ID

    // 私有构造方法
    private ResponseResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.traceId = MDC.get("traceId");  // 自动从MDC获取
    }

    // 成功响应（无数据）
    public static <T> ResponseResult<T> success() {
        return success(null);
    }

    // 成功响应（含数据）
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(
                HttpStatus.OK.value(),
                "Operation successful",
                data
        );
    }

    // 错误响应
    public static <T> ResponseResult<T> error(int code, String message) {
        return new ResponseResult<>(
                code,
                message,
                null
        );
    }

    // 业务自定义消息（链式调用）
    public ResponseResult<T> message(String message) {
        this.message = message;
        return this;
    }
}
