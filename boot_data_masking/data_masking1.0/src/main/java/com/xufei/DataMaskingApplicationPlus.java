package com.xufei;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
public class DataMaskingApplicationPlus {
    public static void main(String[] args) throws UnknownHostException {

        ConfigurableApplicationContext run = SpringApplication.run(DataMaskingApplicationPlus.class);
        ConfigurableEnvironment environment = run.getEnvironment();

        log.info("\n[----------------------------------------------------------]\n\t" +
                        "启动成功:\thttp://{}:{}/1" +
                        "\n[----------------------------------------------------------]",
                InetAddress.getLocalHost().getHostAddress(),
                environment.getProperty("server.port"));



    }
}
