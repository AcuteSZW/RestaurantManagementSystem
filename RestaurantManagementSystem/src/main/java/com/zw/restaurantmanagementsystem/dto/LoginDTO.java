package com.zw.restaurantmanagementsystem.dto;

import com.zw.restaurantmanagementsystem.util.ExceptionUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    private Integer userId;
    private String email;
    @NotBlank(message = ExceptionUtil.UserMessage.PASSWORD_NOT_NULL)
    private String passwordHash;
    private String username;
    private String token;
}
