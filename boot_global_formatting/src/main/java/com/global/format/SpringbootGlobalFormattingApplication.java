package com.global.format;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;


@Slf4j
@SpringBootApplication
public class SpringbootGlobalFormattingApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext run = SpringApplication.run(SpringbootGlobalFormattingApplication.class, args);
        ConfigurableEnvironment environment = run.getEnvironment();
        log.info("\n[----------------------------------------------------------]\n\t" +
                        "聊天室启动成功！客服点击登录:\thttp://{}:{}/test" +
                        "\n[----------------------------------------------------------]",
                InetAddress.getLocalHost().getHostAddress(),
                environment.getProperty("server.port"));
    }

}
