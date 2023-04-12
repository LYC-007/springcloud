package com.xufei.databaselook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 利用主键唯一的特性，如果有多个请求同时提交到数据库的话，数据库会保证只有一个操作可以成功，
 * 那么我们就可以认为操作成功的那个线程获得了该方法的锁，当方法执行完毕之后，想要释放锁的话，删除这条数据库记录即可。
 */
@SpringBootApplication
@EnableScheduling
public class DatabaselookApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaselookApplication.class, args);
    }

}
