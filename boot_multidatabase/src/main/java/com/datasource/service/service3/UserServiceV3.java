package com.datasource.service.service3;


import com.datasource.mapper.UserMapper;
import com.datasource.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceV3 {
    @Autowired
    private UserMapper userDAO;
    public void save() {
        userDAO.save(UserUtil.createUser());
    }
}
