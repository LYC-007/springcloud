package com.xufei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


/**
 *
 * 登录默认的用户名为:user
 */
@SpringBootApplication
public class SecurityApplication8001 {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SecurityApplication8001.class);
        System.out.println("查看具体的过滤器！");
    }
}
