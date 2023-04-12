package com.xufei.springcloud.test.demo;

/**
 * @Author: XuFei
 * @Date: 2022/10/10 15:18
 */
public class Test {
    public static void main( String[] args ) {
        IDecorator decorator = new Decorator();
        IDecorator curtainDecorator = new CurtainDecorator(decorator);
        curtainDecorator.decorate();

    }
}
