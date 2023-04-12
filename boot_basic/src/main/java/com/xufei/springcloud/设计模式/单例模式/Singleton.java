package com.xufei.springcloud.设计模式.单例模式;


/**
 * 这种模式可能会在没有使用类对象的情况下，一直占用堆内存。
 */
public final class Singleton {// 饿汉模式
    private static Singleton instance=new Singleton();// 自行创建实例
    private Singleton(){}// 构造函数
    public static Singleton getInstance(){// 通过该函数向整个系统提供实例
        return instance;
    }
}