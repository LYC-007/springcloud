package com.xufei.redislock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 基于 redis的 单线程原子性
 *
 * 主要依赖redis 的setnx()、expire() 这2个函数实现
 * 方法	描述
 * 1.setnx(lockkey, 1)	如果方法返回 0，则说明占位失败；如果返回 1，则说明占位成功
 * 2.expire()	对 lockkey 设置超时时间，为的是避免死锁问题。
 */
@SpringBootApplication
public class RedislockApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedislockApplication.class, args);
    }

}
