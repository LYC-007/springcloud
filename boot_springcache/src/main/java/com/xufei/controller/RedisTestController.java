package com.xufei.controller;

import com.xufei.impl.IndexServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/redis")
public class RedisTestController {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    @Autowired
    private IndexServiceImpl indexService;
    @GetMapping("/1")
    public String testRedis() {
        //设置值到redis
        redisTemplate.opsForValue().set("name","lucy");
        //从redis获取值
        return (String) redisTemplate.opsForValue().get("name");
    }

    @GetMapping("/2")
    public void cacheTest(){ //SpringCache测试
     ArrayList<String> arrayList= indexService.find();
     indexService.select();
    }
}
