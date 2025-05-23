package com.zw.restaurantmanagementsystem.dto;

import cn.hutool.poi.excel.ExcelPicUtil;
import com.zw.restaurantmanagementsystem.util.ExceptionUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    // 用户ID（雪花算法生成）
    private Long userId;

    // 全局唯一标识符
    private String uuid;

    // 用户名（字母数字+下划线，4-20位）
    private String username;

    // BCrypt加密密码
    private String passwordHash;

    // 已验证邮箱（唯一）
    @NotBlank(message = ExceptionUtil.UserMessage.EMAIL_NOT_NULL)
    private String email;

    // 加密存储联系电话
    private String phone;

    // 头像OSS地址
    private String avatarUrl;

    // 用户角色（1=顾客 2=员工 3=管理员 4=供应商）
    private Integer userType;

    // 权限位掩码
    private Long permissionMask;

    // 所属部门（员工专用）
    private Integer departmentId;

    // 账户状态（1=正常 2=锁定 3=未验证）
    private Integer status;

    // 连续登录失败次数
    private Integer failedAttempts;

    // TOTP加密密钥
    private String mfaSecret;

    // 最后登录IP（支持IPv6）
    private String lastLoginIp;

    // 最后登录时间（yyyy-MM-dd HH:mm:ss）
    private String lastLoginTime;

    // 时区设置
    private String timezone;

    // 界面语言（1=中文 2=英文）
    private Integer preferredLang;

    // 通知偏好（逗号分隔数字 1=SMS 2=EMAIL 3=PUSH）
    private String notificationPrefs;

    // 创建时间（yyyy-MM-dd HH:mm:ss）
    private String createdAt;

    // 修改时间（yyyy-MM-dd HH:mm:ss）
    private String updatedAt;

    // 乐观锁版本号
    private Integer version;

    private String code;
}
