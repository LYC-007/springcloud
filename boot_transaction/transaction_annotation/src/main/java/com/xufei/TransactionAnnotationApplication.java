package com.xufei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAspectJAutoProxy(exposeProxy=true)//开启aspectj动态代理功能，以后所有动态代理都是aspectj创建的（即使没有接口也可以创建动态代理）
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan("com.xufei.mapper")
public class TransactionAnnotationApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransactionAnnotationApplication.class);
    }
}
