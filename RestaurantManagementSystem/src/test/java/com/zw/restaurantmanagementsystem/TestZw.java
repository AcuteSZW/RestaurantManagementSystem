package com.zw.restaurantmanagementsystem;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;

public class TestZw {

    @Test
    public void test() {
//        JSONUtil.parseObj(user).set("code", code).set("type", type);
        JSONObject set = JSONUtil.createObj().set("code", "code").set("type", "type");
        System.out.println(set);
    }
}
