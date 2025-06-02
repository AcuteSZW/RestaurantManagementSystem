package com.zw.restaurantmanagementsystem.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MeetingStatsVO {
    private LocalDate date;
    private Integer currentUser;
    private Integer num;
}