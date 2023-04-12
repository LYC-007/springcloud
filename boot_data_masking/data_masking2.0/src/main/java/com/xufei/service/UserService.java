package com.xufei.service;



import com.xufei.constant.User;

import java.util.List;


public interface UserService {

    List<User> list();

    Long add(User user);

    User findById(Long id);

    User findByName(String name);


}
