package com.xufei.springcloud.test.a;

import java.util.concurrent.locks.StampedLock;

public class Point {
    private double x, y;
    private final StampedLock s1 = new StampedLock();
    void move(double deltaX, double deltaY) {
        // 获取写锁
        long stamp = s1.writeLock();//一个写线程获取写锁的过程中，首先是通过WriteLock 获取一个票据 stamp，WriteLock 是一个独占锁，同时只有一个线程可以获取该锁，当一个线程获取该锁
        //后，其它请求的线程必须等待，当没有线程持有读锁或者写锁的时候才可以获取到该锁。请求该锁成功后会返回一个stamp 票据变量，用来表示该锁的版本
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            // 释放写锁
            s1.unlockWrite(stamp);//当释放该锁的时候，需要 unlockWrite 并传递参数 stamp。
        }
    }

    /*
    相比于 RRW，StampedLock 获取读锁只是使用与或操作进行检验，不涉及 CAS 操作，即使第一次乐观锁获取失败，也会马上升级至悲观锁，
    这样就可以避免一直进行 CAS操作带来的 CPU 占用性能的问题，因此 StampedLock 的效率更高。
     */
    double distanceFormOrigin() {
        // 乐观读操作
        long stamp = s1.tryOptimisticRead();//首先线程会通过乐观锁 tryOptimisticRead 操作获取票据 stamp ，如果当前没有线程持有写锁，则返回一个非 0 的 stamp 版本信息。
        //线程获取该 stamp 后，将会拷贝一份共享资源到方法栈，在这之前具体的操作都是基于方法栈的拷贝数据。

        double currentX = x, currentY = y;
        //判断读期间是否有写操作
        if (!s1.validate(stamp)) {//还需要调用 validate，验证之前调用tryOptimisticRead 返回的 stamp 在当前是否有其它线程持有了写锁，
            // 如果是，那么 validate 会返回 0，升级为悲观锁；否则就可以使用该 stamp 版本的锁对数据进行操作。
            stamp = s1.readLock();// 升级为悲观读
            try {
                currentX = x;
                currentY = y;
            } finally {
                s1.unlockRead(stamp);
            }
        }
        return Math.sqrt(currentX * currentX + currentY);
    }
}