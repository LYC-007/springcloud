package com.xufei.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.xufei"})
public class SwaggerApplication8004 {

    public static void main(String[] args) {
        SpringApplication.run(SwaggerApplication8004.class);
    }
}
