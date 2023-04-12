package com.xufei.spirngcloud.controller;

import com.xufei.spirngcloud.entity.OrderInfo;
import org.redisson.api.RLiveObjectService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 一个 分布式实时对象（Live Object） 可以被理解为一个功能强化后的Java对象。
 * 该对象不仅可以被一个JVM里的各个线程相引用，还可以被多个位于不同JVM里的线程同时引用。
 * Redisson分布式实时对象（Redisson Live Object，简称RLO）运用即时生成的代理类（Proxy）
 * 将一个指定的普通Java类里的所有字段，以及针对这些字段的操作全部映射到一个Redis Hash的数据结构，实现这种理念。
 * 每个字段的get和set方法最终被转译为针对同一个Redis Hash的hget和hset命令，从而使所有连接到同一个Redis节点的所有可以客户端同时对一个指定的对象进行操作。
 * 众所周知，一个对象的状态是由其内部的字段所赋的值来体现的，通过将这些值保存在一个像Redis这样的远程共享的空间的过程，把这个对象强化成了一个分布式对象。
 * 这个分布式对象就叫做Redisson分布式实时对象（Redisson Live Object，简称RLO）。
 *
 * 通过使用RLO，运行在不同服务器里的多个程序之间，共享一个对象实例变得和在单机程序里共享一个对象实例一样了。
 * 同时还避免了针对任何一个字段操作都需要将整个对象序列化和反序列化的繁琐，进而降低了程序开发的复杂性和其数据模型的复杂性：从任何一个客户端修改一个字段的值，处在其他服务器上的客户端（几乎^）即刻便能查看到。
 * 而且实现代码与单机程序代码无异。（^连接到从节点的客户端仍然受Redis的最终一致性的特性限制）
 *
 * 鉴于Redis是一个单线程的程序，针对实时对象的所有的字段操作可以理解为全部是原子性操作，也就是说在读取一个字段的过程不会担心被其他线程所修改。
 *
 * 通过使用RLO，可以把Redis当作一个允许被多个JVM同时操作且不受GC影响的共享堆（Heap Space）。
 *
 *
 * 使用方法:
 * 1.要想获得RLO带来的所有便利，只需要为一个类添加一个@REntity注释，然后再为其中的一个字段添加一个@RId注释即可。
 * 2.通过Redisson对象实例提供的RedissonLiveObjectService服务对象可以很方便的获取RLO实例
 *
 */
@Controller
@RequestMapping
public class LiveObjectController {


    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping("/liveObject")
    @ResponseBody
    public String reduceStock() {
        RLiveObjectService service = redissonClient.getLiveObjectService();
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(1);
        orderInfo.setName("小富111");
        orderInfo.setAge(16);

        OrderInfo orderInfo2 = new OrderInfo();
        orderInfo2.setId(2);
        orderInfo2.setName("小瑞");
        orderInfo2.setAge(16);

         //将orderInfo对象当前的状态持久化到Redis里并与之保持同步。
        if (!service.isExists(orderInfo)) {
            orderInfo = service.persist(orderInfo);
        }
        if (!service.isExists(orderInfo2)) {
            service.persist(orderInfo2);
        }
        //或者取得一个已经存在的RLO实例
        service.get(OrderInfo.class,1);

        // 抛弃orderInfo对象当前的状态，并与Redis里的数据建立连接并保持同步。
        orderInfo.setId(1);
        service.attach(orderInfo);

        // 将orderInfo对象当前的状态与Redis里的数据合并之后与之保持同步。
        service.merge(orderInfo);

        return null;
    }

}
