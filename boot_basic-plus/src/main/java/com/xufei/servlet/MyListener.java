package com.xufei.servlet;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * @WebListener注解为声明此类为Listener，无需再进行配置，唯一注意的是，使用注解的方式声明Listener时，
 * 需要再main函数类上添加@ServletComponentScan（basePackages = "此处写明类地址，格式为包名+类名"）
 *
 *@ServletComponentScan(basePackages = "com.yxc.*")
 */
@WebListener
public class MyListener implements ServletRequestListener {
    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

        System.out.println("---------------------------->请求销毁");
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {

        System.out.println("---------------------------->请求创建");
    }
}