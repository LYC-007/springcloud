package com.xufei.security.expression;


import com.xufei.security.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限校验的方法
 *
 * 系统默认的权限校验类:SecurityExpressionRoot
 */
@Component("ex")
public class MySecurityExpressionRoot {
    //String authority 这里是后端赋给它的权限
    //从数据库获取登录用户的权限功能  和authority 进行对比
    public boolean hasAuthority(String authority){
        //SecurityContextHolder获取当前用户的Authentication对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取当前用户的权限列表
        List<String> permissions = loginUser.getPermissions();
        //判断用户权限集合中是否存在authority
        return permissions.contains(authority);
    }
}
