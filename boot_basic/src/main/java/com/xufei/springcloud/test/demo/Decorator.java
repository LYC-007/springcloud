package com.xufei.springcloud.test.demo;
/**
 * 装修基本类
 * @author admin
 *
 */
public class Decorator implements IDecorator{
    /**
     * 基本实现方法
     */
    public void decorate() {
        System.out.println(" 水电装修、天花板以及粉刷墙。。。");
    }
}
