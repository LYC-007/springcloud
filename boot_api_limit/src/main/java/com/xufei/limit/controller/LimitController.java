package com.xufei.limit.controller;

import com.xufei.limit.annotation.GuavaLimit;
import com.xufei.limit.annotation.RedisLimit;
import com.xufei.limit.annotation.RedissionRateLimit;
import com.xufei.limit.annotation.SemaphoreLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
public class LimitController {
    @Autowired

    private RedisTemplate redisTemplate;
    /**
     * 设置limitKey=ratelimitKey,并且每秒许可证只有3个
     */
    @GuavaLimit(limitKey = "ratelimitKey",value =3)
    @RequestMapping("/ratelimit")
    public String ratelimit() throws Exception{
        //假设业务处理了1秒
        TimeUnit.SECONDS.sleep(1);
        return "success";
    }

    /**
     * 设置limitKey=semaphoreKey,并且最多允许3个线程同时执行
     */
    @SemaphoreLimit(limitKey ="semaphoreKey", value =3)
    @RequestMapping("/semaphoreLimit")
    public String semaphoreLimit() throws Exception{
        //假设业务处理了1秒
        TimeUnit.SECONDS.sleep(1);
        return "success";
    }

    /**
     * 设置limitKey=redisRatelimit,并且每2秒许可证只有7个
     */
    @RedissionRateLimit(limitKey = "redisRatelimit",value =7,time = 2)
    @RequestMapping("/redisRatelimit")
    public String redisRatelimit(){
        return "success";
    }

    // 10 秒中，可以访问10次
    @RedisLimit(key = "test", time = 10, count = 10)
    @GetMapping("/3")
    public String luaLimiter() {
        RedisAtomicInteger entityIdCounter = new RedisAtomicInteger("entityIdCounter", redisTemplate.getConnectionFactory());
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        return date + " 累计访问次数：" + entityIdCounter.getAndIncrement();

    }

}
