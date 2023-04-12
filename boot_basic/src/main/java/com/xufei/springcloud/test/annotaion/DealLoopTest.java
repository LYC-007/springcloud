package com.xufei.springcloud.test.annotaion;

public class DealLoopTest {
    static{
        System.out.println("DealLoopTest...");
    }
    static class DeadLoopClass {
        static {
            if (true) {
                System.out.println(Thread.currentThread()
                        + "init DeadLoopClass");
                while (true) {      // 模拟耗时很长的操作
                }
            }
        }
    }
    public static void main(String[] args) {
        Runnable script = new Runnable() {   // 匿名内部类
            public void run() {
                System.out.println(Thread.currentThread() + " start");
                DeadLoopClass dlc = new DeadLoopClass();
                System.out.println(Thread.currentThread() + " run over");
            }
        };

        Thread thread1 = new Thread(script);
        Thread thread2 = new Thread(script);
        thread1.start();
        thread2.start();
    }
}/* Output:
        DealLoopTest...
        Thread[Thread-1,5,main] start
        Thread[Thread-0,5,main] start
        Thread[Thread-1,5,main]init DeadLoopClass
 *///:~