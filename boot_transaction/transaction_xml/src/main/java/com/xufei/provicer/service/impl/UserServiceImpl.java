package com.xufei.provicer.service.impl;

import com.xufei.mapper.UserMapper;
import com.xufei.provicer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public void save(){

        userMapper.save(createUser());
    }


    /**
     * 模拟数据
     */
    public static Map<String,Object> createUser(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", "walking" + new Random().nextInt(Integer.MAX_VALUE));
        map.put("sex", new Random().nextInt(1));
        map.put("age", new Random().nextInt(80));
        return map;
    }
}
