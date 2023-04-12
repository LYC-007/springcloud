package com.xufei.controller;



import com.xufei.constant.User;
import com.xufei.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/addUser")
    public String addUser(@RequestParam("phone") String phone) {
        User user = new User();
        user.setCity("shanghai");
        user.setName("nima");
        user.setPhone(phone);
        userService.add(user);
        return String.valueOf(user.getId());

    }


    @GetMapping("/findUser")
    public User findUser(@RequestParam("id") String id) {
        return userService.findById(Long.valueOf(id));
    }
}
