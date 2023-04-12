package com.xufei.security.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xufei.security.entity.LoginUser;
import com.xufei.security.entity.User;
import com.xufei.security.mapper.MenuMapper;
import com.xufei.security.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 *
 * UserDetailsService的默认实现类InMemoryUserDetailsManager是在内存里面去查找用户名和密码;
 * 这里我们自定义查找类实现到数据库中去查找用户名和密码
 * 这个类主要做两件事:
 *      1.根据用户名去查询对于的用户及这个用户对应的权限信息;
 *      2.把上面查询到的用户信息和权限信息封装成UserDetails对象;
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    //实现UserDetailsService接口，重写UserDetails方法，自定义用户的信息从数据中查询
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //（认证，即校验该用户是否存在）查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);

        if (Objects.isNull(user)){//如果没有查询到用户
            throw new RuntimeException("用户名或者密码错误");
        }


        //查询对应用户的权限信息
        //定义一个权限集合
        //   List<String> list = new ArrayList<String>(Arrays.asList("test","admin"));
        List<String> list = menuMapper.selectPermsByUserId(user.getId());
        //把数据封装成UserDetails返回
        return new LoginUser(user,list);
    }
}
