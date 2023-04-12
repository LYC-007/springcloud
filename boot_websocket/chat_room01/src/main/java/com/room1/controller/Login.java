package com.room1.controller;

import com.room1.po.User;
import com.room1.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@Controller
public class Login {
    @Autowired
    LoginService loginservice;

    @RequestMapping("/loginvalidate")
    public String loginvalidate(@RequestParam("username") String username, @RequestParam("password") String pwd, HttpSession httpSession) {
        if (username == null)
            return "login";
        String realpwd = loginservice.getpwdbyname(username);
        if (pwd.equals(realpwd)) {
            long uid = loginservice.getUidbyname(username);
            httpSession.setAttribute("uid", uid);
            return "chatroom";
        } else
            return "fail";
    }

    @RequestMapping(value = "/currentuser", method = RequestMethod.GET)
    @ResponseBody
    public User currentuser(HttpSession httpSession) {
        Long uid = (Long) httpSession.getAttribute("uid");
        String name = loginservice.getnamebyid(uid);
        return new User(uid, name);
    }
}
