package com.xufei.service.impl;



import com.xufei.constant.User;
import com.xufei.mapper.UserMapper;
import com.xufei.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public Long add(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public User findByName(String name) {
        return userMapper.findByName(name);
    }
}

