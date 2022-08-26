package com.xufei.springcloud.openfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//OpenFeign使用文档(注意版本):https://docs.spring.io/spring-cloud-openfeign/docs/2.2.6.RELEASE/reference/html/#timeout-handling
//OpenFeign的GitHub地址:https://github.com/spring-cloud/spring-cloud-openfeign
//SpringCloud的GitHub地址:https://github.com/spring-projects/spring-cloud/wiki#release-notes
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class OpenfeignApplication8001 {
    public static void main(String[] args) {
        SpringApplication.run(OpenfeignApplication8001.class);
    }
}
