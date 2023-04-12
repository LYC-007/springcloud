package com.xufei.springcloud.juc.base;


import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * @auther zzyy
 * @create 2022-01-12 16:03
 */
public class ThreadBaseDemo {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock =new ReentrantLock();
        ReentrantReadWriteLock readWriteLock=new ReentrantReadWriteLock();
        Condition condition = lock.newCondition();

        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        boolean oldValue = atomicBoolean.getAndSet(false);
        System.out.println(atomicBoolean.get());
        System.out.println(oldValue);

    }
}

// java = C++ ---》  (C++)-- = java