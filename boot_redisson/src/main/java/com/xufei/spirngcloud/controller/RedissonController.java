package com.xufei.spirngcloud.controller;

import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 *
 * 分布式锁Redisson代码演示
 * 查看官网:https://github.com/redisson/redisson/wiki/%E7%9B%AE%E5%BD%95
 *
 */
@RestController
public class RedissonController {
    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;
    @Autowired
    private RedissonClient redisson;

    @ResponseBody
    @GetMapping(value = "/hello")
    public String hello() {
        //1、获取一把锁，只要锁的名字一样，就是同一把锁，是可重入锁
        RLock myLock = redisson.getLock("my-lock");
        //2、加锁
        //1）、锁的自动续期，如果业务超长，运行期间自动锁上新的30s。不用担心业务时间长，锁自动过期被删掉
        //2）、加锁的业务只要运行完成或者没有释放锁就挂掉了，就不会给当前锁续期，即使不手动解锁，锁默认会在30s内自动过期，不会产生死锁问题
        //只要占锁成功，就会启动一个定时任务【重新给锁设置过期时间，新的过期时间就是看门狗的默认时间-->30秒】
        // 每隔[internalLockLeaseTime 【看门狗默认时间】 / 3] 秒都会自动的再次续期，续成30秒
        //lockWatchdogTimeout = 30 * 1000 【看门狗默认时间】
        myLock.lock();      //阻塞式等待。默认加的锁都是30s
        // myLock.lock(10,TimeUnit.SECONDS); ----->只要我们知道业务执行的时间就建议使用这种方式加锁
        // 如果我们设置了锁的过期时间，就算业务没有完成也会解锁，不会对锁的时间续期，所以自动解锁时间一定要大于业务执行时间

        try {
            System.out.println("加锁成功，执行业务..." + Thread.currentThread().getId());
            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //3、解锁  假设解锁代码没有运行，Redisson会不会出现死锁
            System.out.println("释放锁..." + Thread.currentThread().getId());
            myLock.unlock();//建议不要直接这样写，而是先判断下 是否上了锁，是不是当前线程上的锁

        }
        return "hello";
    }

    /**
     *
     * 获取锁的粒度更小(读写锁演示)
     *
     * 保证一定能读到最新数据，修改期间，写锁是一个排它锁（互斥锁、独享锁），读锁是一个共享锁
     * 写锁没释放读锁必须等待
     * 读 + 读 ：相当于无锁，并发读，只会在Redis中记录好，所有当前的读锁。他们都会同时加锁成功
     * 写 + 读 ：必须等待写锁释放
     * 写 + 写 ：阻塞方式
     * 读 + 写 ：有读锁。写也需要等待
     * 只要有读或者写的存都必须等待
     */
    @GetMapping(value = "/write")
    @ResponseBody
    public String writeValue() {
        String s = "";
        RReadWriteLock readWriteLock = redisson.getReadWriteLock("rw-lock");
        RLock rLock = readWriteLock.writeLock();
        try {
            //1、改数据加写锁，读数据加读锁
            rLock.lock();
            s = UUID.randomUUID().toString();
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            ops.set("writeValue", s);
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }

        return s;
    }

    @GetMapping(value = "/read")
    @ResponseBody
    public String readValue() {
        String s = "";
        RReadWriteLock readWriteLock = redisson.getReadWriteLock("rw-lock");
        //加读锁
        RLock rLock = readWriteLock.readLock();
        try {
            rLock.lock();
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            s = ops.get("writeValue");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }

        return s;
    }


    /**
     * 分布式闭锁示例
     * 放假、锁门
     * 5个班的同学全部走完，我们才可以锁大门
     */
    @GetMapping(value = "/lockDoor")
    @ResponseBody
    public String lockDoor() throws InterruptedException {

        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.trySetCount(5);  //设置有几个班,只有5个班的人都走了才锁门
        door.await();       //等待闭锁完成

        return "放假了...";
    }

    @GetMapping(value = "/gogogo/{id}")
    @ResponseBody
    public String gogogo(@PathVariable("id") Long id) {
        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.countDown();       //每走一个计数-1
        return id + "班的人都走了...";
    }


    /**
     *
     * 信号量代码示例
     * RSemaphore park = redisson.getSemaphore("park");
     * park.trySetPermits(....);----->向Redis设置park的原始值
     *       先在Redis中存入“park”的值，每调 park() 方法，park的值减一，就相当于车辆开进停车位
     *       每调go() 方法park的值就加一，相当于车开出停车位
     *       如果park的值为0,那么park()将是会阻塞或者直接放回失败;---所以就可以用来做分布式限流
     * 信号量也可以做分布式限流
     */
    @GetMapping(value = "/park")
    @ResponseBody
    public String park() throws InterruptedException {
        RSemaphore park = redisson.getSemaphore("park");
        //park.acquire();     //获取一个信号、获取一个值,占一个车位---->阻塞式获取，一定要等到一个车位
        boolean flag = park.tryAcquire();// park.tryAcquire();尝试获取，没获取到直接返回失败;
        if (flag) {
            //执行业务
        } else {
            return "error";
        }
        return "ok=>" + flag;
    }

    @GetMapping(value = "/go")
    @ResponseBody
    public String go() {
        RSemaphore park = redisson.getSemaphore("park");
        park.release();     //释放一个车位
        return "ok";
    }
}
