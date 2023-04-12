package com.consumer.annotation;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo(scanBasePackages = "com.consumer.annotation")
@SpringBootApplication
public class AnnotationConsumer8004 {

    public static void main(String[] args) {
        SpringApplication.run(AnnotationConsumer8004.class);
    }
}
