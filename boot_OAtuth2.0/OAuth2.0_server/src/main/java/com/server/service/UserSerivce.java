package com.server.service;


import com.server.model.UserInfo;

/**
 *  UserSerivce
 */
public interface UserSerivce {

    /**
     * 根据用户名查询用户信息
     */
    public UserInfo queryByUserName(String username);
}
