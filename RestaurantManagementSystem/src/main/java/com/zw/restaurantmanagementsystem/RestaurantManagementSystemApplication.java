package com.zw.restaurantmanagementsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.zw.restaurantmanagementsystem.dao")
@ServletComponentScan
public class RestaurantManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantManagementSystemApplication.class, args);
    }

}
