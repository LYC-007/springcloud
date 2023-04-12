package com.xufei.springcloud.juc.volatiles;

import java.util.concurrent.TimeUnit;

/*
    JMM是规范,有个细则叫happen-before,happen-before中用来保证有序性的是volatile、synchronized关键字
    volatile 是 Java 虚拟机提供轻量级的同步机制，类似于synchronized 但是没有其强大。
*/

public class VolatileNoAtomicDemo
{
    public static void main(String[] args)
    {
        MyNumber myNumber = new MyNumber();

        for (int i = 1; i <=10; i++) {
            new Thread(() -> {
                for (int j = 1; j <=1000; j++) {
                    myNumber.addPlusPlus();
                }
            },String.valueOf(i)).start();
        }
        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println(myNumber.number);
    }
}
class MyNumber {
    volatile int number;
    public void addPlusPlus()
    {
        number++;
    }
}