package com.server.service.impl;

import com.server.dao.UserMapper;
import com.server.model.UserInfo;
import com.server.service.UserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  UserServiceImpl
 */
@Service
public class UserServiceImpl implements UserSerivce {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserInfo queryByUserName(String username) {
        return userMapper.queryUserByUserName(username);
    }
}
