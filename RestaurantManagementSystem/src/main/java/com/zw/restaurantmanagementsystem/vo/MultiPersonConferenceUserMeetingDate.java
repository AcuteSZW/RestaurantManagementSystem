package com.zw.restaurantmanagementsystem.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("multi_person_conference_user_meeting_date")
public class MultiPersonConferenceUserMeetingDate {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("user_uuid")
    private String userUuid;

    private Date meetingDate;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}