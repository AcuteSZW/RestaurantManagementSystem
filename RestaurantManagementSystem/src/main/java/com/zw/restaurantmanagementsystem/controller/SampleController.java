package com.zw.restaurantmanagementsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class SampleController {

    @GetMapping("/test")
    public Map<String, Object> test(@RequestParam(required = false) String name) {
        Map<String, Object> response = new HashMap<>();
        log.info("Received request with name: {}", name);
        response.put("message", "Hello, " + (name == null ? "World" : name));
        return response;
    }

    @PostMapping("/create")
    public Map<String, Object> create(@RequestBody Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", body);
        return response;
    }
}