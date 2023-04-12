package com.room1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
//import org.springframework.web.socket.server.standard.ServerEndpointExporter;
/**
 * WebScoket配置处理器
 */
@Configuration
public class WebSocketConfig implements WebMvcConfigurer {
    /**
     * ServerEndpointExporter 作用
     * <p>
     * 这个Bean会自动注册使用@ServerEndpoint注解声明的websocket endpoint
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /**
     * ·
     * 视图映射:发送一个请求，直接跳转到一个页面
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/logout").setViewName("login");
        registry.addViewController("/").setViewName("login");

    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // classpath表示在resource目录下，/static/** 表示在URL路径中访问如
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
