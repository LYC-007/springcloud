package com.xufei.rw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xufei.rw.domain.User;
import com.xufei.rw.mapper.UserMapper;
import com.xufei.rw.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void test() {
        for (int i = 1; i < 10; i++) {
            User user = new User();
            user.setAge(i+2);
            user.setName("许飞"+i);
            userMapper.insert(user);
        }
    }
    @Override
    public void selectCount() {
        System.out.println("用户的数量为:"+userMapper.selectList(new QueryWrapper<>()).size()+"！！！！！！！！！！！！！");
    }

}
