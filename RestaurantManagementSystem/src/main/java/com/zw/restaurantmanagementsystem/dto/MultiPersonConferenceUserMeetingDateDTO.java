package com.zw.restaurantmanagementsystem.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 会议日期模型
 */
@Data
public class MultiPersonConferenceUserMeetingDateDTO {
    private String userUuid;
    private List<Date> meetingDates;
}