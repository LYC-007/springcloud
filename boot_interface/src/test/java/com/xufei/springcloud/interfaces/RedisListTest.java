package com.xufei.springcloud.interfaces;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisListTest {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void test1(){
        //存入List数据 做左边推入一个 如果键不存在 则创建一个空的并左推入
        redisTemplate.opsForList().leftPush("list1","1");
        //存入List数据 做左边推入多个 如果键不存在 则创建一个空的并左推入
        redisTemplate.opsForList().leftPushAll("list1","88","999");
        //存入List数据 做右边推入一个 如果键不存在 则创建一个空的并右推入
        redisTemplate.opsForList().rightPush("list1","3");
        //存入List数据 做右边推入多个 如果键不存在 则创建一个空的并右推入
        redisTemplate.opsForList().leftPushAll("list1","77","6666");
        //返回指定List数据下标的值
        System.out.println(redisTemplate.opsForList().index("",2));
        //移除2个指定List数据元素内容为1
        redisTemplate.opsForList().remove("list1",2,"1");
        //左边推出一个
        System.out.println(redisTemplate.opsForList().leftPop("list1"));
        //移除集合中左边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出。
        System.out.println(redisTemplate.opsForList().leftPop("list1",2, TimeUnit.SECONDS));
        //右边推出一个
        System.out.println(redisTemplate.opsForList().rightPop("list1"));
        //移除集合中右边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出。
        System.out.println(redisTemplate.opsForList().rightPop("list1",2, TimeUnit.SECONDS));
        //给指定键List数据下标为1的元素替换成2
        redisTemplate.opsForList().set("list1",1,"2");
        //查看指定键List数据元素个数
        redisTemplate.opsForList().size("list1");
        //获取指定健List数据 从开始到结束下标
        redisTemplate.opsForList().range("list1",0,-1).forEach(System.out::println);
        //移除列表的最后一个元素，并将该元素添加到另一个列表(如果这另一个List不存在就创建一个空的添加)并返回
        redisTemplate.opsForList().rightPopAndLeftPush("list1","list2");
        // 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
        redisTemplate.opsForList().rightPopAndLeftPush("list1","list2",80, TimeUnit.SECONDS);


    }

    @Test
    public void test2(){
        //指定键List数据右边推出一个元素
        System.out.println(redisTemplate.opsForList().rightPop("list1"));
        //指定键List数据右边推出一个元素，如果List元素 等待10秒 10秒内没有元素就不操作，有就推出
        System.out.println(redisTemplate.opsForList().rightPop("list1",10, TimeUnit.SECONDS));
        //指定键List数据左边推出一个元素，如果List元素 等待10秒 10秒内没有元素就不操作，有就推出
        System.out.println(redisTemplate.opsForList().leftPop("list1"));
        //指定键List数据左边推出一个元素
        System.out.println(redisTemplate.opsForList().leftPop("list1",10, TimeUnit.SECONDS));
        //给指定键List数据下标为1的元素替换成2
        redisTemplate.opsForList().set("list1",1,"2");
        //查看指定键List数据元素个数
        redisTemplate.opsForList().size("list1");
        //如果存在该键的List数据 则向左推入一个元素 不存在的话不操作
        redisTemplate.opsForList().leftPushIfPresent("list1","1");
        //如果存在该键的List数据 则向右推入一个元素 不存在的话不操作
        redisTemplate.opsForList().rightPushIfPresent("list1","1");

    }

}
