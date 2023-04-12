package com.xufei.limit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 本模块介绍了3中限流方法:
 * 1.Google的guava，用于单机限流
 * 2.Redission的分布式限流
 * 3.java concurrent包下的Semaphore信号量实现限流
 * 做分布式限流，调研的限流框架大概有：
 * 1、spring cloud gateway集成redis限流,但属于网关层限流
 * 2、阿里Sentinel,功能强大、带监控平台
 * 3、srping cloud hystrix，属于接口层限流，提供线程池与信号量两种方式
 * 4、其他：redission、手撸代码



 * 注意:Guava的RateLimiter只适用于单机的限流，在互联网分布式项目中可以借助redis或者阿里开源的sentinel中间件来实现限流的功能
 * Guava中的限流实现RateLimiter使用的是令牌桶算法，RateLimiter提供了两种限流实现：
 * 1.平滑突发限流(SmoothBursty)
 * RateLimiter rateLimiter = RateLimiter.create(5);//每秒5个令牌
 * RateLimiter由于会累积令牌，所以可以应对突发流量。
 * RateLimiter在没有足够令牌发放时，采用滞后处理的方式，也就是前一个请求获取令牌所需等待的时间由下一次请求来承受，也就是代替前一个请求进行等待。
 * 2.平滑预热限流(SmoothWarmingUp)
 * RateLimiter rateLimiter = RateLimiter.create(5,5, TimeUnit.SECONDS); //每秒5个令牌，预热期为5秒
 * 平滑预热限流带有预热期的平滑限流，它启动后会有一段预热期，逐步将令牌产生的频率提升到配置的速率
 * 这种方式适用于系统启动后需要一段时间来进行预热的场景
 * 比如，我设置的是每秒5个令牌，预热期为5秒，那么它就不会是0.2左右产生一个令牌。在前5秒钟它不是一个均匀的速率，5秒后恢复均匀的速率
 */


@SpringBootApplication
public class LimitApplication8001 {
    public static void main(String[] args) {
        SpringApplication.run(LimitApplication8001.class);
    }
}
