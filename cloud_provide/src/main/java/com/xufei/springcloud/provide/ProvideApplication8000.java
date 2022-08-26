package com.xufei.springcloud.provide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: XuFei
 * @Date: 2022/8/23 14:51
 */

@EnableDiscoveryClient
@SpringBootApplication
public class ProvideApplication8000 {
    public static void main(String[] args) {
        SpringApplication.run(ProvideApplication8000.class);
    }
}
