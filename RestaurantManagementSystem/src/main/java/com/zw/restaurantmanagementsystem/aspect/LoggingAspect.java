package com.zw.restaurantmanagementsystem.aspect;

import cn.hutool.core.lang.UUID;
import com.zw.restaurantmanagementsystem.util.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private static final String SENSITIVE_FIELDS = "password|token|secret|creditCard";  // 敏感字段正则

    @Around("execution(* com.zw.restaurantmanagementsystem.controller.*.*(..))")
    public ResponseResult<?> loggingAround(ProceedingJoinPoint joinPoint) throws Throwable {
        final String traceId = UUID.fastUUID().toString();
        MDC.put("traceId", traceId);
        final long startTime = System.currentTimeMillis();

        try {
            logRequestDetails(joinPoint);

            ResponseResult<?> result = (ResponseResult<?>) joinPoint.proceed();
            result.setTraceId(traceId);  // 注入traceId

            logSuccessfulResponse(result, startTime);
            return result;

        } catch (Throwable ex) {
            return handleException(ex, traceId, startTime);
        } finally {
            MDC.clear();
        }
    }

    private void logRequestDetails(ProceedingJoinPoint joinPoint) {
        if (!log.isInfoEnabled()) return;

        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) return;

        HttpServletRequest request = attributes.getRequest();
        log.info("[Request] {} {} | Args: {}",
                request.getMethod(),
                request.getRequestURI(),
                filterSensitiveData(joinPoint.getArgs()));
    }

    private void logSuccessfulResponse(ResponseResult<?> result, long startTime) {
        if (!log.isInfoEnabled()) return;

        long duration = System.currentTimeMillis() - startTime;
        log.info("[Response] code={} | message={} | data={} | duration={}ms",
                result.getCode(),
                result.getMessage(),
                filterSensitiveData(result.getData()),
                duration);
    }

    private ResponseResult<?> handleException(Throwable ex, String traceId, long startTime) {
        long duration = System.currentTimeMillis() - startTime;
        log.error("[Error] duration={}ms | error={}", duration, ex.getMessage(), ex);

        return ResponseResult.error(500, "系统处理异常")
                .setTraceId(traceId)
                .message(ex.getMessage());
    }

    private Object filterSensitiveData(Object target) {
        if (target == null) return null;

        // 对字符串类型进行脱敏
        if (target instanceof String) {
            return ((String) target).replaceAll(
                    "(?i)\"(" + SENSITIVE_FIELDS + ")\":\"[^\"]*\"",
                    "\"$1\":\"***\""
            );
        }

        // 对数组类型递归处理
        if (target.getClass().isArray()) {
            return Arrays.stream((Object[]) target)
                    .map(this::filterSensitiveData)
                    .toArray();
        }

        return target;
    }
}
