package com.room2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class SpringNettyWebsocketApplicationPlus {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringNettyWebsocketApplicationPlus.class);
    public static void main(String[] args) throws UnknownHostException {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringNettyWebsocketApplicationPlus.class, args);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        LOGGER.info("\n[----------------------------------------------------------]\n\t" +
                        "聊天室启动成功！游客击登录:\thttp://{}:{}/tourist" +
                        "\n[----------------------------------------------------------",
                InetAddress.getLocalHost().getHostAddress(),
                environment.getProperty("server.port"));
        LOGGER.info("\n[----------------------------------------------------------]\n\t" +
                        "聊天室启动成功！客服点击登录:\thttp://{}:{}/" +
                        "\n[----------------------------------------------------------",
                InetAddress.getLocalHost().getHostAddress(),
                environment.getProperty("server.port"));

    }
}

