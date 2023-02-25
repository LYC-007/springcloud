package com.xufei.spirngcloud.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.redisson.config.Config;


@Configuration
public class MyRedissonConfig {
    @Bean(destroyMethod="shutdown")//销毁方法    ---->这是单节点模式，集群模式的Redisson配置参考官网
    public RedissonClient redisson(){
        //1、创建配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.236.131:6379");
        config.useSingleServer().setIdleConnectionTimeout(10000);
        /**
         * {
         *   "singleServerConfig":{
         *     "idleConnectionTimeout":10000,
         *     "pingTimeout":1000,
         *     "connectTimeout":10000,
         *     "timeout":3000,
         *     "retryAttempts":3,
         *     "retryInterval":1500,
         *     "password":"123456",
         *     "subscriptionsPerConnection":5,
         *     "clientName":null,
         *     "address": "redis://127.0.0.1:6379",
         *     "subscriptionConnectionMinimumIdleSize":1,
         *     "subscriptionConnectionPoolSize":50,
         *     "connectionMinimumIdleSize":32,
         *     "connectionPoolSize":64,
         *     "database":14
         *   },
         *   "threads":0,
         *   "nettyThreads":0,
         *   "codec":{
         *     "class":"org.redisson.codec.JsonJacksonCodec"
         *   },
         *   "transportMode":"NIO"
         * }
         */
        //2、根据Config创建出RedissonClient实例
        //Redis url should start with redis:// or rediss://
        return Redisson.create(config);
    }
}

