package com.zw.restaurantmanagementsystem.dto;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
//@NoArgsConstructor
@AllArgsConstructor
public class MultiPersonConferenceUserDTO {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "uuid", fill = FieldFill.INSERT)
    private String uuid;

    private String username;

    private String password;

    private String email;

    private String telephone;

    @TableLogic
    @TableField(fill = FieldFill.INSERT_UPDATE, select = false)
    private Integer isDelete;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    // 发送邮件类型 1.注册 2.登录 3.找回密码
    private String sendType;

    // 构造函数中初始化 UUID
    public MultiPersonConferenceUserDTO() {
        this.uuid = UUID.randomUUID().toString();
    }
}
