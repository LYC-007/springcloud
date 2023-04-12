package com.xufei.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations="classpath:consumer.xml")
@SpringBootApplication
public class ConsumerApplication8001 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication8001.class);
    }
}
