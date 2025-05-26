package com.zw.restaurantmanagementsystem.vo;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Date;


/**
 * 多人会议用户
 * @author: zhongwei
 * @description: 多人会议用户
 */
@Data
@Accessors(chain = true) // 支持链式调用
@TableName("multi_person_conference_user")
public class MultiPersonConferenceUser {
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
    private int isDelete;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    // 构造函数中初始化 UUID
    public MultiPersonConferenceUser() {
        this.uuid = UUID.randomUUID().toString();
    }
}
