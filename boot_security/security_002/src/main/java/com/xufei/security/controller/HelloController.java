package com.xufei.security.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("test")
    public String test() {
        return "test hello";
    }
    /**
     * hasAuthority方法只能传入一个权限，用户有该权限时才能进行访问
     */
    @RequestMapping("/hello")
    @PreAuthorize("hasAuthority('test')")
    public String hello() {
        return "hello";
    }

    /**
     * 使用自定义权限校验的方法
     * 在SPEL表达式中使用 @ex相当于获取容器中bean的名字为ex的对象。然后再调用这个对象的hasAuthority方法
     */
    @RequestMapping("hello0")
    @PreAuthorize("@ex.hasAuthority('system:dept:list')")
    public String hello0() {
        return "hello";
    }

    /**
     * hasAnyAuthority方法可以传入多个权限，用户有其中一个权限就可以访问
     */
    @RequestMapping("hello1")
    @PreAuthorize("hasAnyAuthority('admin','test','system:dept:list')")
    public String hello1() {
        return "hello";
    }
    /**
     * hasAnyRole方法任意的角色就可以访问。它内部会把我们传入的参数拼接上“ROLE_”后再去比较，所以这种情况下要用用户对应的权限也要有“ROLE_”这个前缀才可以;
     */
    @RequestMapping("hello2")
    @PreAuthorize("hasAnyRole('admin','system:dept:list')")
    public String hello2() {
        return "hello";
    }
    /**
     * hasRole方法要求有对应的角色才可以访问，但是它内部会把我们传入的参数拼接上“ROLE_”后再去比较，所以这种情况下要用用户对应的权限也要有“ROLE_”这个前缀才可以;
     */
    @RequestMapping("hello3")
    @PreAuthorize("hasRole('system:dept:list')")
    public String hello3() {
        return "hello";
    }






}
