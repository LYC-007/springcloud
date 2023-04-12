package com.api.limit;


import jodd.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.*;

@Slf4j
@SpringBootTest
public class RedisLimitApplicationtTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Resource(name = "slideLimitRedisScript")
    private RedisScript<Long> slideLimitRedisScript;
    @Resource(name = "tokenRedisScript")
    private RedisScript<Long> tokenRedisScript;

    /**
     * 滑动时间窗口算法测试
     */
    @Test
    public void slideLimitRedisScriptEST() throws Exception {
        // 并发30个线程执行, 使用信号灯模拟同时调用  (预想效果是 前20个请求只有5个成功, 然后后面10个请求5个成功)
        Semaphore semaphore = new Semaphore(30);
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(30);
        for (int i = 0; i < 30; i++) {
            int j = i;
            pool.execute(() -> {
                try {
                    int i1;
                    if (j > 20) {
                        i1 = j + 3100;
                        log.info("");
                        log.info("延迟3秒后执行的线程10个");
                    } else {
                        i1 = j + 50;
                    }

                    // 睡眠
                    ThreadUtil.sleep(1500 + i1);
                    // 获取许可证
                    semaphore.acquire();
                    // 执行方法
                    String result = this.slideLimitRedisScriptMethod();
                    log.info("executeMethod===, i:{}, ===, result:{}", j, result);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    semaphore.release();
                }
            });
        }
        TimeUnit.SECONDS.sleep(15);
    }

    /**
     * 令牌桶的初始化
     * 现在令牌桶算法测试之前先做令牌桶的初始化
     * 初始化缓存,  项目启动完毕初始化或者分布式锁先初始化, 甚至你在这里可以设置过期, 传递过期时间, 脚本里面每次执行都重置一下过期时间, 以节约非热点限流器资源
     */
    @Test
    public void test(){
        String key = "zhihao12333";
        if (!redisTemplate.hasKey(key)){ // 不存在, 进行初始化
            BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(key);
            hashOps.put("last_grant_time",String.valueOf(System.currentTimeMillis()));// 上一次添加令牌时间
            hashOps.put("curr_permits",String.valueOf(10L));// 当前令牌数
            hashOps.put("bucket_cap",String.valueOf(10L));// 令牌桶
            hashOps.put("rate",String.valueOf(1L));// 每隔多少时间生成多少令牌 (目前脚本里面是  1/s)
        }
    }

    /**
     * 令牌算法测试
     * 做之前先执行令牌桶的初始化
     */
    @Test
    public void tokenRedisScriptEST() throws Exception {
        // 并发30个线程执行, 使用信号灯模拟同时调用  (预想效果是 前20个请求只有10个成功, 后10个因为只过去了5秒, 才补充了5个令牌所以是5个成功)
        Semaphore semaphore = new Semaphore(20);
        ExecutorService pool = Executors.newFixedThreadPool(30);
        CountDownLatch countDownLatch = new CountDownLatch(30);
        for (int i = 0; i < 30; i++) {
            int j = i;
            pool.execute(() -> {
                try {
                    int i1;
                    if (j >= 20) {
                        i1 = j + 5100;
                        log.info("延迟5秒后执行的线程10个");
                    } else {
                        i1 = j + 50;
                    }

                    // 睡眠
                    ThreadUtil.sleep(1500 + i1);
                    // 获取许可证
                    semaphore.acquire();
                    // 执行方法
                    String result = this.tokenRedisScriptMethod();
                    log.info("executeMethod===, i:{}, ===, result:{}", j, result);
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    semaphore.release();
                }
            });
        }
        countDownLatch.await();
    }
    @Test
    public void test3(){
        Boolean aBoolean = redisTemplate.hasKey("dsfdfsdfdsf");

        System.out.println(aBoolean);
    }

    private String tokenRedisScriptMethod() {
        String key = "zhihao12333";
        long currentTimeMillis = System.currentTimeMillis();
        Long result = redisTemplate.execute(tokenRedisScript, Collections.singletonList(key),String.valueOf(1L), String.valueOf(currentTimeMillis));
        result = Optional.ofNullable(result).orElse(-1L);
        if (result.intValue() == 0L) {
            return "限流了!!!";
        } else {
            return "正常执行!!!";
        }
    }
    private String slideLimitRedisScriptMethod() {
        // 假设以下是通过注解获取到的配置, 或者其他地方获取到的配置
        // (限流时间内允许的) 最大请求
        long max = 5L;
        // 缓存key
        String key = "zhihao123";
        // 滑动窗口时间(限流时间范围)
        long period = 3L;
        // 限流时间单位, 默认秒
        TimeUnit timeUnit = TimeUnit.SECONDS;
        // 缓存的过期时间, 转成毫秒
        long expiredTime = timeUnit.toMillis(period);
        // 当前时间戳
        long currentTimeMillis = System.currentTimeMillis();
        // 当前时间 - 间隔时间 = 滑动窗口外数据过期时间
        long slideExpiredTime = currentTimeMillis - expiredTime;
        // 执行lua脚本判断是否达到限流    脚本对象,   keys集合,  参数集合(根据脚本索引放)
        Long result = redisTemplate.execute(slideLimitRedisScript, Collections.singletonList(key),
                String.valueOf(currentTimeMillis), String.valueOf(slideExpiredTime), String.valueOf(max),String.valueOf(expiredTime));
        result = Optional.ofNullable(result).orElse(-1L);
        if (result.intValue() == 0L) {
            return "限流了!!!";
        } else {
            return "正常执行!!!";
        }
    }



}



