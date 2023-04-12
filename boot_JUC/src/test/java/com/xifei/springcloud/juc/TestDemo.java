package com.xifei.springcloud.juc;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class TestDemo {
    public static void main(String[] args) {
        Lock  lock=new ReentrantLock();
        Condition condition = lock.newCondition();
    }
}
