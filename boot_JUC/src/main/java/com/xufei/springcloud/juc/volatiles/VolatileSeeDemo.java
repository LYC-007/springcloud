package com.xufei.springcloud.juc.volatiles;

import java.util.concurrent.TimeUnit;


public class VolatileSeeDemo {
    //static boolean flag = true;
    static volatile boolean flag = true;
    public static void main(String[] args) {
        //现象:加了volatile程序会结束，不加程序会陷入死循环
        //原理:有了volatile后，线程A修改了该值向主内存刷写时会给主内存中的这个值加锁，并且加锁后会清空其他工作内存变量的值，其他工作想要使用必须重新load;
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t -----come in");
            while (flag) {
            }
            System.out.println(Thread.currentThread().getName() + "\t -----flag被设置为false，程序停止");
        }, "t1").start();

        //暂停几秒钟线程
        try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}

        flag = false;

        System.out.println(Thread.currentThread().getName() + "\t 修改完成flag: " + flag);


    }
}
