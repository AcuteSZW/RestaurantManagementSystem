package com.zw.restaurantmanagementsystem.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.zw.restaurantmanagementsystem.dto.MultiPersonConferenceUserDTO;
import com.zw.restaurantmanagementsystem.dto.MultiPersonConferenceUserMeetingDateDTO;
import com.zw.restaurantmanagementsystem.service.MultiPersonConferenceService;
import com.zw.restaurantmanagementsystem.util.ConversionMultiPersonConferenceUserUtil;
import com.zw.restaurantmanagementsystem.util.ResponseResult;
import com.zw.restaurantmanagementsystem.vo.MultiPersonConferenceUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 多人会议
 * @author: zhongwei
 * @description: 多人会议
 * @date: 2023/9/23
 */
@Slf4j
@RestController
@RequestMapping("/multiPersonConference")
public class MultiPersonConferenceController {
    @Autowired
    private MultiPersonConferenceService multiPersonConferenceService;

    @GetMapping("/helloworld")
    public ResponseResult<String> helloworld() {
        return ResponseResult.success("helloworld");
    }
    //多人会议注册
    @PostMapping("/register")
    public ResponseResult<String> register(@RequestBody MultiPersonConferenceUserDTO multiPersonConferenceUserDTO) {
        MultiPersonConferenceUser multiPersonConferenceUser = ConversionMultiPersonConferenceUserUtil.convertToVo(multiPersonConferenceUserDTO);
        String registerMessage = multiPersonConferenceService.register(multiPersonConferenceUser);
        return ResponseResult.success(registerMessage);
    }
    //发送邮件
    @PostMapping("/send-email")
    public ResponseResult<String> sendEmail(@RequestBody MultiPersonConferenceUserDTO multiPersonConferenceUserDTO) {
        String message = multiPersonConferenceService.sendEmail(multiPersonConferenceUserDTO);
        return ResponseResult.success(message);
    }

    @PostMapping("/check-email")
    public ResponseResult<String> checkEmail(@RequestBody MultiPersonConferenceUserDTO multiPersonConferenceUserDTO) {
        String message = multiPersonConferenceService.checkEmail(multiPersonConferenceUserDTO);
        return ResponseResult.success(message);
    }

    //多人会议登录
    @PostMapping("/login")
    public ResponseResult<MultiPersonConferenceUserDTO> login(@RequestBody MultiPersonConferenceUserDTO multiPersonConferenceUserDTO) {
        MultiPersonConferenceUser multiPersonConferenceUser = ConversionMultiPersonConferenceUserUtil.convertToVo(multiPersonConferenceUserDTO);
        MultiPersonConferenceUserDTO token = multiPersonConferenceService.login(multiPersonConferenceUser,multiPersonConferenceUserDTO);
        return ResponseResult.success(token);
    }
    //多人会议登出
    public ResponseResult<String> logout(String username) {
        return null;
    }
    //多人会议查询
    @PostMapping("/search-book")
    public ResponseResult<String> search(@RequestBody MultiPersonConferenceUserMeetingDateDTO multiPersonConferenceUserMeetingDateDTO) {
        ResponseResult<String> getResult = getResult(multiPersonConferenceUserMeetingDateDTO);
        if (getResult != null){ return getResult;}
        if (multiPersonConferenceUserMeetingDateDTO.getMeetingDates().size()>2){ return ResponseResult.error(600, "最多只能选择2个日期"); }
        String s = multiPersonConferenceService.searchBook(multiPersonConferenceUserMeetingDateDTO);
        return ResponseResult.success(s);
    }
    //多人会议添加
    public ResponseResult<String> add(String username, String password) {
        return null;
    }
    //用户预约会议日期
    @PostMapping("/book")
    public ResponseResult<String> book(@RequestBody MultiPersonConferenceUserMeetingDateDTO multiPersonConferenceUserMeetingDateDTO) {
        String book = multiPersonConferenceService.book(multiPersonConferenceUserMeetingDateDTO);
        return ResponseResult.success(book);
    }
    //用户取消预约会议日期
    @PostMapping("/cancel-book")
    public ResponseResult<String> cancelBook(@RequestBody MultiPersonConferenceUserMeetingDateDTO multiPersonConferenceUserMeetingDateDTO) {
        ResponseResult<String> getResult = getResult(multiPersonConferenceUserMeetingDateDTO);
        if (getResult != null){ return getResult;}
        String s = multiPersonConferenceService.cancelBook(multiPersonConferenceUserMeetingDateDTO);
        return ResponseResult.success(s);
    }

    private static ResponseResult<String> getResult(MultiPersonConferenceUserMeetingDateDTO multiPersonConferenceUserMeetingDateDTO) {
        if (ArrayUtil.isEmpty(multiPersonConferenceUserMeetingDateDTO.getMeetingDates())) {
            return ResponseResult.error(600, "请选择日期");
        }
        if (StrUtil.isBlank(multiPersonConferenceUserMeetingDateDTO.getUserUuid())){
            return ResponseResult.error(600, "请输入用户编号");
        }
        if (multiPersonConferenceUserMeetingDateDTO.getMeetingDates().size() > 1000){
            return ResponseResult.error(600, "最多只能选择1000个日期");
        }
        return null;
    }

    //用户查看预约会议日期
    public ResponseResult<String> checkBook(@RequestBody MultiPersonConferenceUserMeetingDateDTO multiPersonConferenceUserMeetingDateDTO) {

        return null;
    }
}
