package com.xufei.springcloud.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * execute() 需要 RedisConnection 对象，通过 RedisConnection 操作 Redis 被称为低级抽象（Low-Level Abstractions）
 *
 * opsFor 之类的被称为高级抽象（High-Level Abstractions），是为了提供更友好的模板类，底层还是调用的 execute()，需要 RedisConnection 对象。opsFor 就是对 execute() 的进一步封装。
 *
 */

@SpringBootTest
public class RedisExcuteTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

}
