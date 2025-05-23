package com.zw.restaurantmanagementsystem.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;


// util/JwtUtil.java
@Component
public class JwtUtil {
//    @Value("${jwt.secret}")
    private static final String SECRET_KEY = "6AqoED9grFkE1s3hqU4SuflVI9wFhIlDOKFWwwYdYtYi02A+Zo0+t952/gGQdByxPETVvID90+qRHsOKcRuW7w==";
//    private static final String SECRET_KEY = "your-restaurant-management-system-secret-key-2023";
    private static final long EXPIRATION = 86400000L; // 24小时

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
