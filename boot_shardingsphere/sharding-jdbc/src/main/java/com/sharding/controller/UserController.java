package com.sharding.controller;

import com.sharding.model.User;
import com.sharding.repository.UserRepository;
import com.sharding.util.RandomName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/test")
    public void contextLoads() {
        for (int i = 10; i < 30; i++) {
            User user = new User();
            user.setId(i+1);
            user.setAge(i+2);
            user.setName(RandomName.randomName(true,3));
            userRepository.save(user);
        }
        List<User> userList = userRepository.findAll();
        System.out.println("用户的数量=" + userList.size());
    }

}
