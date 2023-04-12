package com.xufei.security.service.serviceImpl;


import com.xufei.security.service.LoginService;
import com.xufei.utils.RedisCacheUtils;
import com.xufei.security.entity.LoginUser;
import com.xufei.security.entity.User;
import com.xufei.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCacheUtils redisCache;


    @Override
    public String login(User user) {

        //通过UsernamePasswordAuthenticationToken获取用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        //AuthenticationManager委托机制对authenticationToken 进行用户认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //如果认证没有通过，给出对应的提示
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败！");
        }
        //如果认证通过，拿到这个当前登录用户信息
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

        //获取当前用户的userid
        String userid = loginUser.getUser().getId().toString();
        //如果认证通过，使用user生成jwt
        String jwt = JwtUtil.createJWT(userid);
        //把完整的用户信息存入redis  userid为key   用户信息为value
        //redisCache.setCacheObject("login:"+userid,loginUser);
        redisCache.setCacheObject("login:"+userid,loginUser,10, TimeUnit.SECONDS);
        //把jwt返回给前端
        return jwt;
    }

    @Override
    public void logout() {
        //从SecurityContextHolder中的userid
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();

        //根据userid找到redis对应值进行删除
        redisCache.deleteObject("login:"+userid);
    }

}

