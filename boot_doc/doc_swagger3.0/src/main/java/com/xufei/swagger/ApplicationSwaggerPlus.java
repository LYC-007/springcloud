package com.xufei.swagger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 官网说明 http://springfox.github.io/springfox/docs/current
 * 全面配置参照 http://springfox.github.io/springfox/docs/current/#quick-start-guides
 */
@SpringBootApplication
public class ApplicationSwaggerPlus {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationSwaggerPlus.class);

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext run = SpringApplication.run(ApplicationSwaggerPlus.class);
        ConfigurableEnvironment environment = run.getEnvironment();
        logger.info("\n[----------------------------------------------------------]\n\t" +
                        "swagger文档界面:\thttp://{}:{}/swagger-ui/index.html" +
                        "\n[----------------------------------------------------------]",
                InetAddress.getLocalHost().getHostAddress(),
                environment.getProperty("server.port"));
        logger.info("\n[----------------------------------------------------------]\n\t" +
                        "knife4j接口测试界面:\thttp://{}:{}/doc.html" +
                        "\n[----------------------------------------------------------]",
                InetAddress.getLocalHost().getHostAddress(),
                environment.getProperty("server.port"));
    }
}
