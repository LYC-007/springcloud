package com.provider.annotation;


import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo(scanBasePackages = "com.provider.annotation.service.impl")
@SpringBootApplication
public class AnnotationProvider8003 {


    public static void main(String[] args) {
        SpringApplication.run(AnnotationProvider8003.class);
    }
}
