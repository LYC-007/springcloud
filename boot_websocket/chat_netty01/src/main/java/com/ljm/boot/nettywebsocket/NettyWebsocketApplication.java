package com.ljm.boot.nettywebsocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * https://blog.csdn.net/ming19951224/article/details/108555917?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522167547848916800186564442%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=167547848916800186564442&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduend~default-1-108555917-null-null.142^v73^insert_down2,201^v4^add_ask,239^v1^control&utm_term=SpringBoot%20WebSocket%20Netty&spm=1018.2226.3001.4187
 */
@SpringBootApplication
public class NettyWebsocketApplication {

    public static final Logger LOGGER = LoggerFactory.getLogger(NettyWebsocketApplication.class);
    public static void main(String[] args) throws UnknownHostException {

        ConfigurableApplicationContext run = SpringApplication.run(NettyWebsocketApplication.class, args);
        ConfigurableEnvironment environment = run.getEnvironment();
        LOGGER.info("\n[----------------------------------------------------------]\n\t" +
                        "聊天室启动成功！点击进入:\thttp://{}:{}" +
                        "\n[----------------------------------------------------------",
                InetAddress.getLocalHost().getHostAddress(),
                environment.getProperty("server.port"));

    }

}
