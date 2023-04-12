package com.xufei.limit.rate;

import com.xufei.limit.annotation.RedissionRateLimit;
import com.xufei.limit.aspect.RedissionLimitAspect;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Redission更加方便，使用更加灵活，下面介绍下Redission分布式限流，使用方法:
 * 1、 声明一个限流器
 *      RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
 * 2、 设置速率，5秒中产生3个令牌
 *      rateLimiter.trySetRate(RateType.OVERALL, 3, 5, RateIntervalUnit.SECONDS);
 * 3、试图获取一个令牌，获取到返回true
 *      rateLimiter.tryAcquire(1)
 **/
@Component
public class InitRedsisonRateLimit implements ApplicationContextAware {

    @Autowired
    private RedissonClient client;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(RestController.class);
        beanMap.forEach((k, v) -> {
            Class<?> controllerClass = v.getClass();
            System.out.println(controllerClass.toString());
            System.out.println(controllerClass.getSuperclass().toString());
            //获取所有声明的方法
            Method[] allMethods = controllerClass.getSuperclass().getDeclaredMethods();
            RedissionRateLimit redisRateLimit;
            RRateLimiter rRateLimiter;
            for (Method method : allMethods) {
                //判断方法是否使用了限流注解
                if (method.isAnnotationPresent(RedissionRateLimit.class)) {
                    //获取配置的限流量,实际值可以动态获取,配置key,根据key从配置文件获取
                    redisRateLimit = method.getAnnotation(RedissionRateLimit.class);
                    String key = redisRateLimit.limitKey();
                    if (key.equals("")) {
                        key = method.getName();
                    }
                    System.out.println("RedisRatelimitKey:" + key + ",许可证数是" + redisRateLimit.value());
                    //key作为key.value为具体限流量,传递到切面的map中
                    rRateLimiter = client.getRateLimiter(key);
                    //访问模式    访问数 访问速率  访问时间
                    //访问模式 RateType.PER_CLIENT=单实例共享     RateType.OVERALL=所有实例共享
                    rRateLimiter.trySetRate(RateType.OVERALL, redisRateLimit.value(), redisRateLimit.time(), RateIntervalUnit.SECONDS);
                    RedissionLimitAspect.rateLimitMap.put(key, rRateLimiter);
                }
            }
        });
    }
}
