package com.xufei.springcloud.juc.base;


import java.util.concurrent.TimeUnit;

//如果线程处于被阻塞状态（例如处于sleep, wait, join 等状态），在别的线程中调用当前线程对象的interrupt方法，那么线程将立即退出被阻塞状态（中断状态将被清除），
// 并抛出一个InterruptedException异常。
public class DemoThread {
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "\t" + "中断标志位：" + Thread.currentThread().isInterrupted() + "程序终止");
                    break;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // Thread.currentThread().interrupt();  假如加了这个，程序可以终止，只会爆异常
                }
                System.out.println("-----hello InterruptDemo03" + Thread.currentThread().isInterrupted());
            }
        }, "t1");
        t1.start();
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(t1::interrupt).start();
    }
}


