package com.zw.restaurantmanagementsystem.dto;

import lombok.Data;

import java.sql.Date;
import java.util.List;

/**
 * 会议日期模型
 */
@Data
public class MultiPersonConferenceUserMeetingDateDTO {
    private String userUuid;
    private List<Date> meetingDates;
    private Date startDate;
    private Date endDate;
}