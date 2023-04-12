package com.xufei;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@ServletComponentScan(basePackages = "com.xufei.*")
@SpringBootApplication
public class BasicPlusApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext run = SpringApplication.run(BasicPlusApplication.class);
        ConfigurableEnvironment environment = run.getEnvironment();
        log.info("\thttp://{}:{}", InetAddress.getLocalHost().getHostAddress(),environment.getProperty("server.port"));
    }
}
