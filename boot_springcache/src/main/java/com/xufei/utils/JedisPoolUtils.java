package com.xufei.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

//JedisPool工具类
public class JedisPoolUtils {

    /**
     * maxActive:(高版本改名为MaxTotal) ---- 控制一个pool可分配多少个jedis实例，如果赋值为-1则为不限制
     * maxWait:(高版本改名为MaxWaitMillis) ---- 表示当借一个jedis实例，最大等待时间，如果超过等待事件，则抛出JedisConnectionException
     * maxIdle: ---- 控制一个pool最少有多少个状态为(Idle)空闲的实例时就要挂了
     * whenExhaustedAction: ---- 表示当pool中的jedis实例被借用完，pool要采取的的操作，默认有三种：
     * -WHEN_EXHAUSTED-FAIL -> 表示无jedis实例时，直接抛出NoSuchElementException
     * -WHEN_EXHAUSTED-BLOCK -> 表示阻塞住或达到maxwait直接抛出JedisConnectionException
     * -WHEN_EXHAUSTED-GROW -> 表示新建一个jedis实例，也就是说设置的maxactive无用
     * testOnBorrow ---- 获得一个jedis实例的时候是否检查可用性(ping()) 如果为true 则得到的jedis实例时可用的
     * testOnReturn ---- 还给一个jedis实例的时候是否检查可用性(ping())
     */
    private static volatile JedisPool jedisPool = null;

    public static JedisPool getJedisPoolInstance() {
        synchronized (JedisPoolUtils.class) {
            if(jedisPool == null) {
                JedisPoolConfig poolConfig = new JedisPoolConfig();
                poolConfig.setMaxWaitMillis(100*1000);
                poolConfig.setMaxIdle(32);
                poolConfig.setTestOnBorrow(true);
                poolConfig.setMaxTotal(1000);
                jedisPool = new JedisPool(poolConfig,"47.102.197.194",6379);
            }
        }
        return jedisPool;
    }

    public static void release(Jedis jedis) {
        if(null != jedisPool) {
			/*
			    高版本2.9以上弃用了
			    jedisPool.returnBrokenResource(jedis);
                jedisPool.returnResource(jedis);
                jedisPool.returnResourceObject(jedis);
			*/
            jedis.close();
        }
    }

}
