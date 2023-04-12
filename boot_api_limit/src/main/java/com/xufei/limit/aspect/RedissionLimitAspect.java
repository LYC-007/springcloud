package com.xufei.limit.aspect;

import com.xufei.limit.annotation.RedissionRateLimit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RRateLimiter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于guava包下的RateLimimiter限流  实现每秒令牌数量控制
 **/
@Component
@Scope
@Aspect
public class RedissionLimitAspect {

    /**
     * 存储限流量和方法必须是static且线程安全
     */
    public static Map<String, RRateLimiter> rateLimitMap = new ConcurrentHashMap<>();

    /**
     * 业务层切点
     */
    @Pointcut("@annotation(com.xufei.limit.annotation.RedissionRateLimit)")
    public void ServiceAspect() {
    }

    @Around("ServiceAspect()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object obj = null;
        try {
            //获取目标对象
            Class<?> clz = joinPoint.getTarget().getClass();
            //tryAcquire()是非阻塞, rateLimiter.acquire()是阻塞的
            Signature signature = joinPoint.getSignature();
            String name = signature.getName();
            String limitKey = getLimitKey(clz, name);
            RRateLimiter rateLimiter = rateLimitMap.get(limitKey);
            if (rateLimiter.tryAcquire()) {
                obj = joinPoint.proceed();
            } else {
                //拒绝了请求（服务降级）
                obj = "Redis The system is busy, please visit after a while";
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return obj;
    }

    private String getLimitKey(Class<?> clz, String methodName) {
        for (Method method : clz.getDeclaredMethods()) {
            //找出目标方法
            if (method.getName().equals(methodName)) {
                //判断是否是限流方法
                if (method.isAnnotationPresent(RedissionRateLimit.class)) {
                    String key= method.getAnnotation(RedissionRateLimit.class).limitKey();
                    if(key.equals("")){
                        key=method.getName();
                    }
                    return key;
                }
            }
        }
        return null;
    }
}
