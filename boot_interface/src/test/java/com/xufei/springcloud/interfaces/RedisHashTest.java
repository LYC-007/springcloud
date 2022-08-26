package com.xufei.springcloud.interfaces;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class RedisHashTest {


    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void test1(){
        Map<String,String> map = new HashMap<>();
        map.put("lxy","123445");
        map.put("lhm","434564");

        //向指定键推入一个元素（指定键不存在就创建一个空的推入）
        redisTemplate.opsForHash().put("hash1","lzj","1234");

        //向指定键推入多个元素（指定键不存在就创建一个空的推入）
        redisTemplate.opsForHash().putAll("hash1",map);

        //向指定键推入一个元素（仅当lzj不存在时才设置）
        redisTemplate.opsForHash().putIfAbsent("hash1","lzj","1234");

        //获取指定键里面单个元素key为lzj的值
        System.out.println(redisTemplate.opsForHash().get("hash1","lzj"));

        //获取指定键里面多个元素key为特定的值
        redisTemplate.opsForHash().multiGet("hash1", Arrays.asList("lzj","num")).forEach(System.out::println);

        //查看指定键内有没有元素的key是lzj的
        System.out.println(redisTemplate.opsForHash().hasKey("hash1","lzj"));

        //查看键所有元素的Key
        redisTemplate.opsForHash().keys("hash1").forEach(System.out::println);

        //查看键所有的元素
        redisTemplate.opsForHash().entries("hash1").forEach((k,v) -> {System.out.println("k"+k+" _ "+"v"+v);});

        //查看键所有元素的值
        redisTemplate.opsForHash().values("hash1").forEach(System.out::println);;

        //查看指定键的元素的key为lzj的值的长度
        System.out.println(redisTemplate.opsForHash().lengthOfValue("hash1","lzj"));

        //查看指定键有多少个元素
        System.out.println(redisTemplate.opsForHash().size("hash1"));

        //指定键的元素的Key为num的值加整数（如果key不存在创建一个初始为0加整数）
        redisTemplate.opsForHash().increment("hash1","num",1);

        //指定键的元素的Key为num的值加浮点数（如果key不存在创建一个初始为0加浮点数）
        redisTemplate.opsForHash().increment("hash1","num",3.2);

        //指定键 根据key值删除元素
        redisTemplate.opsForHash().delete("hash","lzj");

        //获取集合的游标。通过游标可以遍历整个集合。
        Cursor<Map.Entry<Object, Object>> curosr = redisTemplate.opsForHash().scan("hash1", ScanOptions.NONE);
        while(curosr.hasNext()){
            Map.Entry<Object, Object> entry = curosr.next();
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }


}
