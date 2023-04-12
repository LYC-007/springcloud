package com.xufei.zookeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 每个客户端对某个方法加锁时，在 Zookeeper 上与该方法对应的指定节点的目录下，生成一个唯一的临时有序节点。
 * 判断是否获取锁的方式很简单，只需要判断有序节点中序号最小的一个。 当释放锁的时候，只需将这个临时节点删除即可。
 * 同时，其可以避免服务宕机导致的锁无法释放，而产生的死锁问题。
 */
@SpringBootApplication
public class ZookeeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZookeeperApplication.class, args);
    }

}
