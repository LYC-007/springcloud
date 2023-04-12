package com.xufei.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Druid的监控配置功能:
 * https://github.com/alibaba/druid/wiki/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98
 *
 * Druid Spring Boot Starter 用于帮助你在Spring Boot项目中轻松集成Druid数据库连接池和监控:
 * https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter
 *
 */
@SpringBootApplication
public class DruidMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DruidMonitorApplication.class, args);
    }

}
