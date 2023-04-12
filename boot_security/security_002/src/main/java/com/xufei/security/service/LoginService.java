package com.xufei.security.service;

import com.xufei.security.entity.User;

public interface LoginService {
    String login(User user);

    void logout();

}
