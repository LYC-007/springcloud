package com.xufei.sprincloud.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Sentinel使用的是“信号量”隔离,Hystrix使用的是线程池隔离
 * SentinelGitHub地址:https://github.com/alibaba/Sentinel
 * Sentinel中文官网:https://github.com/alibaba/Sentinel/wiki/%E4%BB%8B%E7%BB%8D
 * Sentinel的控制台下载地址:https://github.com/alibaba/Sentinel/releases
 * SpringCloudAibaba对于Sentinnel版本的说明:https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E
 * Sentinel控制台启动命令:java -jar sentinel-dashboard-1.8.1.jar
 * 登录Sentinel控制台(默认端口8080，登录账号密码均为sentinel):http://localhost:8080
 *
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class SentinelApplication8002 {
    public static void main(String[] args) {
        SpringApplication.run(SentinelApplication8002.class);
    }
}
