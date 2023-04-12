package com.xufei;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 数据脱敏项目
 */
@MapperScan("com.xufei.mapper")
@SpringBootApplication
public class DataMaskingApplication {
    private static final Logger LOGGER= LoggerFactory.getLogger(DataMaskingApplication.class);
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext run = SpringApplication.run(DataMaskingApplication.class);
        ConfigurableEnvironment environment = run.getEnvironment();

        LOGGER.info("\n[-------------------------------------]\n"
                +"\t项目启动成功:\thttp://{}:{}/addCustomer" +
                "\n[-------------------------------------]", InetAddress.getLocalHost().getHostAddress(),environment.getProperty("server.port"));

    }
}
