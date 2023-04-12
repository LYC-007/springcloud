package com.xufei.mapper;



import com.xufei.constant.User;

import javax.annotation.Resource;
import java.util.List;
@Resource
public interface UserMapper {

    Long addUser(User user);

    User findById(Long id);

    List<User> list();

    User findByName(String name);
}
