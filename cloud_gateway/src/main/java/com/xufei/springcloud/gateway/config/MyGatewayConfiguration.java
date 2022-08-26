package com.xufei.springcloud.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @Author: XuFei
 * @Date: 2022/8/22 15:28
 */

@Configuration
public class MyGatewayConfiguration {
    @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        org.springframework.web.cors.CorsConfiguration corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
        //1、配置跨域
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsWebFilter(source);
    }
//    public MyGatewayConfiguration(){
//        GatewayCallbackManager.setBlockHandler(new BlockRequestHandler() {
//            //网关限流了请求，就会调用此回调
//            @Override
//            public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable t) {
//                //  R error = R.error(BizCodeEnum.TO_MANY_REQUEST.getCode(), BizCodeEnum.TO_MANY_REQUEST.getMessage());
//                String errorJson = JSON.toJSONString("设置限流了你想给前端返回的数据");
//                Mono<ServerResponse> body = ServerResponse.ok().body(Mono.just(errorJson), String.class);
//                return body;
//            }
//        });
//    }
}
