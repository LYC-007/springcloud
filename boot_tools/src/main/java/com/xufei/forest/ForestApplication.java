package com.xufei.forest;

import com.thebeastshop.forest.springboot.annotation.ForestScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Forest - 声明式HTTP客户端框架
 *
 * Forest有以下特点：
 * 1.以Httpclient和OkHttp为后端框架
 * 2.通过调用本地方法的方式去发送Http请求, 实现了业务逻辑与Http协议之间的解耦
 * 3.相比Feign更轻量，不依赖Spring Cloud和任何注册中心
 * 4.支持所有请求方法：GET, HEAD, OPTIONS, TRACE, POST, DELETE, PUT, PATCH
 * 5.支持灵活的模板表达式
 * 6.支持过滤器来过滤传入的数据
 * 7.基于注解、配置化的方式定义Http请求
 * 8.支持Spring和Springboot集成
 * 9.实现JSON和XML的序列化和反序列化
 * 10.支持JSON转换框架: Fastjson,Jackson, Gson
 * 11.支持JAXB形式的XML转换
 * 12.支持SSL的单向和双向加密
 * 13.支持http连接池的设定
 * 14.可以通过OnSuccess和OnError接口参数实现请求结果的回调
 * 15.配置简单，一般只需要@Request一个注解就能完成绝大多数请求的定义
 * 16.支持异步请求调用
 *
 *
 * GitHub地址:
 *      https://gitee.com/xiexin11/forest/
 * 中文文档地址:
 *      https://gitee.com/xiexin11/forest/#forest---%E5%A3%B0%E6%98%8E%E5%BC%8Fhttp%E5%AE%A2%E6%88%B7%E7%AB%AF%E6%A1%86%E6%9E%B6
 */
@SpringBootApplication
//在启动类里配置代理接口类的扫描包
@ForestScan(basePackages = "com.xufei.forest.client")
public class ForestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForestApplication.class, args);
    }
}