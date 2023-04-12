package com.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Oauth2ServerApplication授权服务器启动类
 * 模拟微信 服务器
 */
@SpringBootApplication
@MapperScan(basePackages = "com.server.dao")
public class Oauth2ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(Oauth2ServerApplication.class, args);
    }
}