package com.zw.restaurantmanagementsystem.util;


import jakarta.servlet.http.HttpServletRequest;

// util/SecurityUtil.java
public class SecurityUtil {
    
    public static void checkLogin(HttpServletRequest request) {
        if (request.getAttribute("CURRENT_USER") == null) {
            throw new BusinessException("login error");
        }
    }
    
    public static String getCurrentUser(HttpServletRequest request) {
        return (String) request.getAttribute("CURRENT_USER");
    }
}
