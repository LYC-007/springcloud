package com.xufei.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 此模块是解决接口幂等性问题的实例代码
 *
 * interfaces ，interfaces01 都是是通过Token机制解决幂等性问题
 */
@SpringBootApplication
public class InterfaceApplication8009 {
    public static void main(String[] args) {
        SpringApplication.run(InterfaceApplication8009.class);
    }
}
