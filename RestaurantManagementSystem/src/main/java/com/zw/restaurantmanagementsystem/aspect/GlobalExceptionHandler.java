package com.zw.restaurantmanagementsystem.aspect;

import com.zw.restaurantmanagementsystem.util.BusinessException;
import com.zw.restaurantmanagementsystem.util.ResponseResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseResult<Void> handleBusinessException(BusinessException ex) {
        if (ex.getCode() == 401) {
            return ResponseResult.error(401, "请先登录");
        } else if (ex.getCode() == 403) {
            return ResponseResult.error(403, "无访问权限");
        } else {
            return ResponseResult.error(ex.getCode(), ex.getMessage());
        }
    }
}