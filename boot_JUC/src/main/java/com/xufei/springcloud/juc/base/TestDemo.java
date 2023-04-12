package com.xufei.springcloud.juc.base;

import org.omg.PortableInterceptor.ACTIVE;

import java.lang.invoke.VolatileCallSite;
import java.security.AccessControlContext;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//案例:当前线程的中断标识为true，线程不是立刻停止
public class TestDemo {
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            for(int i = 0;i < 300;i ++){
                System.out.println("---------" + i);
            }
            System.out.println("after t1.interrupt()---第2次----"+Thread.currentThread().isInterrupted());
        },"t1");
        t1.start();
        System.out.println("before t1.interrupt()----"+t1.isInterrupted());
        t1.interrupt();
        try {TimeUnit.MILLISECONDS.sleep(3);} catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("after t1.interrupt()---第1次---"+t1.isInterrupted());
        try {TimeUnit.MILLISECONDS.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}
        System.out.println(t1.isAlive() ?"活着":"结束");
        System.out.println("after t1.interrupt()---第3次---"+t1.isInterrupted());

    }
}

