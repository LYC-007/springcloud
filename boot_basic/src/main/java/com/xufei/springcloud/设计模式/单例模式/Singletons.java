package com.xufei.springcloud.设计模式.单例模式;

import java.util.ArrayList;
import java.util.List;

public final class Singletons {// 懒汉模式 + synchronized 同步锁 + double-check
    private volatile static Singletons instance= null;// 不实例化
    public List<String> list = null;//list 属性

    private Singletons(){
        list = new ArrayList<String>();
    }// 构造函数
    public static Singletons getInstance(){// 加同步锁，通过该函数向整个系统提供实例
        if(null == instance){// 第一次判断，当 instance 为 null 时，则实例化对象，否则直接返
            synchronized (Singletons.class){// 同步锁
                if(null == instance){// 第二次判断
                    instance = new Singletons();// 实例化对象
                }
            }
        }
        return instance;// 返回已存在的对象
    }
}
final class Single {//内部类的方式
    public List<String> list = null;// list 属性
    private Single() {// 构造函数
        list = new ArrayList<String>();
    }
    // 内部类实现
    public static class InnerSingleton {
        private static Single instance=new Single();// 自行创建实例
    }
    public static Single getInstance() {
        return InnerSingleton.instance;// 返回内部类中的静态变量
    }
}

