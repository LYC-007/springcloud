package com.swagger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 我们使用Swagger来进行接口调试、前端提供接口文档，但是Swagger并没有实际上那么方便，比如我们在发送Post请求时，参数选填还是非常不友好，那么有没有更好的工具呢？
 * 那么，knife4j将是一个不错的选择。
 *
 * 什么是knife4j？
 * knife4j是为Java MVC框架集成Swagger生成Api文档的增强解决方案,前身是swagger-bootstrap-ui,具有小巧,轻量,并且功能强悍的优点。Knife4j提供两大核心功能:
 * 1.文档说明：根据Swagger的规范说明，详细列出接口文档的说明，包括接口地址、类型、请求示例、请求参数、响应示例、响应参数、响应码等信息，使用swagger-bootstrap-ui能根据该文档说明，对该接口的使用情况一目了然。
 * 2.在线调试：提供在线接口联调的强大功能，自动解析当前接口参数,同时包含表单验证，调用参数可返回接口响应内容、headers、Curl请求命令实例、响应时间、响应状态码等信息，帮助开发者在线调试，而不必通过其他测试工具测试接口是否正确,简洁、强大。
 */
@SpringBootApplication
public class SwaggerApplication {
    private static final Logger logger = LoggerFactory.getLogger(SwaggerApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext run = SpringApplication.run(SwaggerApplication.class);
        ConfigurableEnvironment environment = run.getEnvironment();
        logger.info("\n[----------------------------------------------------------]\n\t" +
                        "swagger文档界面:\thttp://{}:{}/swagger-ui.html" +
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
