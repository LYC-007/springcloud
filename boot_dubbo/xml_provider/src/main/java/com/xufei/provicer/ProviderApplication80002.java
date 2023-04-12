package com.xufei.provicer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
/**
 * 1、导入依赖；
 * 2.SpringBoot与dubbo整合的三种方式：
 * 1）、导入dubbo-starter，在application.properties配置属性，使用@Service【暴露服务】使用@Reference【引用服务】
 * 2）、保留dubbo xml配置文件;
 * 		导入dubbo-starter，使用@ImportResource导入dubbo的配置文件即可
 * 3）、使用注解API的方式：
 * 		将每一个组件手动创建到容器中,让dubbo来扫描其他的组件
 */
//@EnableDubbo //开启基于注解的dubbo功能
@ImportResource(locations="classpath:provider.xml")
@SpringBootApplication
public class ProviderApplication80002 {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication80002.class);
    }
}
