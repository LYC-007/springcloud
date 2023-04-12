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
public class SpringNettyWebsocketApplication2 {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringNettyWebsocketApplication2.class);
    public static void main(String[] args) throws UnknownHostException {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringNettyWebsocketApplication2.class, args);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        LOGGER.info("\n[----------------------------------------------------------]\n\t" +
                        "聊天室启动成功！点击进入:\thttp://{}:{}/index.html" +
                        "\n[----------------------------------------------------------",
                InetAddress.getLocalHost().getHostAddress(),
                environment.getProperty("server.port"));

    }
}

