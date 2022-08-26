package com.xufei.demo;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableAdminServer  //开启监控功能
public class ActuatorServerApplication8010 {

    public static void main(String[] args) {
        SpringApplication.run(ActuatorServerApplication8010.class);
    }
}
