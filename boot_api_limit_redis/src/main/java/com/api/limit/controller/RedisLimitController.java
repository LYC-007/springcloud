package com.api.limit.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Slf4j
@RestController
public class RedisLimitController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Resource(name = "slideLimitRedisScript")
    private RedisScript<Long> slideLimitRedisScript;
    @Resource(name = "tokenRedisScript")
    private RedisScript<Long> tokenRedisScript;

    /**
     * 滑动窗口算法
     * ab -n 500 -c 10 http://192.168.136.1:8003/1
     */
    @GetMapping("/1")
    public void slideLimitRedisScriptEST() {


        // 假设以下是通过注解获取到的配置, 或者其他地方获取到的配置

        // (限流时间内允许的) 最大请求
        long max = 10L;
        // 缓存key
        String key = "zhihao123";
        // 滑动窗口时间(限流时间范围，也就是3秒内 处理5个请求 )
        long period = 1L;
        // 缓存的过期时间, 转成毫秒
        long expiredTime = TimeUnit.SECONDS.toMillis(period);
        // 当前时间戳
        long currentTimeMillis = System.currentTimeMillis();
        // 当前时间 - 间隔时间 = 滑动窗口外数据过期时间
        long slideExpiredTime = currentTimeMillis - expiredTime;
        // 执行lua脚本判断是否达到限流    脚本对象,   keys集合,  参数集合(参数顺序根据脚本索引放)
        Long result = redisTemplate.execute(
                slideLimitRedisScript,
                Collections.singletonList(key),
                String.valueOf(currentTimeMillis), //当前时间戳
                String.valueOf(slideExpiredTime), //当前key 滑动窗口之外过期时间
                String.valueOf(max),//最大限流次数
                String.valueOf(expiredTime)//缓存过期时间
        );
        result = Optional.ofNullable(result).orElse(-1L);
        if (result.intValue() == 0L) {
            log.info("限流了！！");
        } else {
            log.info("正常执行！！");
        }
    }

    /**
     * 在测试令牌桶算法之前先做 令牌桶的初始化
     * 令牌桶的初始化方法
     */
    @GetMapping("/2")
    public void initKey() {
        String key = "zhihao1230";
        if (redisTemplate.hasKey(key)) {
            log.info("已经做过初始化了！！！！");
        } else { // 不存在, 进行初始化
            BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(key);
            hashOps.put("last_grant_time", String.valueOf(System.currentTimeMillis()));// 上一次添加令牌时间
            hashOps.put("curr_permits", String.valueOf(10L));// 当前令牌数
            hashOps.put("bucket_cap", String.valueOf(10L));// 令牌桶
            hashOps.put("rate", String.valueOf(1L));// 每隔多少时间生成多少令牌 (目前脚本里面是  1/s)
        }
    }

    /**
     * 在测试令牌桶算法之前先做 令牌桶的初始化
     *
     * 令牌桶算法测试
     */
    @GetMapping("/3")
    public void tokenRedisScriptEST() {
        String key = "zhihao1230";
        long currentTimeMillis = System.currentTimeMillis();
        Long result = redisTemplate.execute(tokenRedisScript, Collections.singletonList(key),
                String.valueOf(1L), String.valueOf(currentTimeMillis));
        result = Optional.ofNullable(result).orElse(-1L);
        if (result.intValue() == 0L) {
            log.info("限流了！！");
        } else {
            log.info("正常执行！！");
        }
    }

}
