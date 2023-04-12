package com.xufei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * SpringBoot中事务配置XML方式:
 *      1.在resources文件夹下创建xml文件。例如：transaction.xml
 *      2.在启动类上添加@ImportResource注解，例如：@ImportResource("classpath:transaction.xml")
 */
@MapperScan("com.xufei.mapper")
@EnableTransactionManagement(proxyTargetClass = true)
@ImportResource("classpath:transaction.xml")// 如果你不想写Xml配置文件可以查看  TxAnoConfig 这个配置类
@SpringBootApplication
public class TransactionXmlApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransactionXmlApplication.class);
    }
}
