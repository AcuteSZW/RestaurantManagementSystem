package com.zw.restaurantmanagementsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.restaurantmanagementsystem.dto.MultiPersonConferenceUserMeetingDateDTO;
import com.zw.restaurantmanagementsystem.vo.MeetingStatsVO;
import com.zw.restaurantmanagementsystem.vo.MultiPersonConferenceUserMeetingDate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MultiPersonConferenceUserMeetingDateMapper extends BaseMapper<MultiPersonConferenceUserMeetingDate> {

    List<MeetingStatsVO> selectByDates(MultiPersonConferenceUserMeetingDateDTO multiPersonConferenceUserMeetingDateDTO);
}
