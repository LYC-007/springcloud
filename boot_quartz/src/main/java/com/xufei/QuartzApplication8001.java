package com.xufei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 SpringBoot 关于 Quartz 的自动配置的类一共有 6 个，分别为：

 1、 JobStoreType：是一个枚举类，定义了 jobsStore 的类型，基于内存和数据库

 2、 QuartzAutoConfiguration：自动配置类，配置了 Quartz 的主要组件，如 SchedulerFactoryBean

 3、 QuartzDataSource：是一个注解类。如果需要注入 Quartz 配置的数据库操作类，需要使用此注解标注。参考 QuartzAutoConfiguration中的用法

 4、 QuartzDataSourceInitializer：该类主要用于数据源初始化后的一些操作，根据不同平台类型的数据库进行选择不同的数据库脚本

 5、 QuartzProperties：该类对应了在 application.yml配置文件以 spring.quartz开头的相关配置

 6、 SchedulerFactoryBeanCustomizer：在自动配置的基础上自定义配置需要实现的此接口。
 */
@SpringBootApplication
public class QuartzApplication8001 {

    public static void main(String[] args) {
        SpringApplication.run(QuartzApplication8001.class);
    }
}
