package com.xufei;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 这是一个前后端分类项目，请使用PostMan测试
 */

@SpringBootApplication
public class SecurityApplication8002 {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication8002.class);
    }
    static {
        System.setProperty("druid.mysql.usePingMethod","false");
    }
}
