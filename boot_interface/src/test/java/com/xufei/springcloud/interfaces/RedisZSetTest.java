package com.xufei.springcloud.interfaces;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@SpringBootTest
public class RedisZSetTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void test1() {
        //向指定键插入元素 和 分数
        redisTemplate.opsForZSet().add("zset1", "lzj", 20.5);
        //向指定键插入元素 和 分数
        ZSetOperations.TypedTuple<String> objectTypedTuple1 = new DefaultTypedTuple<String>("zset-1", 9.6);
        ZSetOperations.TypedTuple<String> objectTypedTuple2 = new DefaultTypedTuple<String>("zset-2", 9.9);
        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<ZSetOperations.TypedTuple<String>>();
        tuples.add(objectTypedTuple1);
        tuples.add(objectTypedTuple2);
        redisTemplate.opsForZSet().add("zset1", tuples);
        //获取指定键内指定元素的分数
        redisTemplate.opsForZSet().score("zset1", "zset-1");
        //指定键的移除指定元素
        redisTemplate.opsForZSet().remove("zset1", "lzj", "zset-1");
        //通过分数返回有序集合指定区间内的成员个数
        System.out.println(redisTemplate.opsForZSet().count("zset1", 10, 20));
        //通过索引区间返回有序集合成指定区间内的成员，其中有序集成员按分数值递增(从小到大)排序
        redisTemplate.opsForZSet().range("zset1", 0, -1).forEach(System.out::println);
        //返回有序集中指定成员的排名，其中有序集成员按分数值递增(从小到大)顺序排列
        System.out.println(redisTemplate.opsForZSet().rank("zset1", "zset-1"));
        //返回有序集中指定成员的排名，其中有序集成员按分数值递增(从大到小)顺序排列
        redisTemplate.opsForZSet().reverseRank("zset1", "zset-1");
        //通过分数返回有序集合指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列
        redisTemplate.opsForZSet().rangeByScore("zset1", 10, 20).forEach(System.out::println);
        //通过分数返回有序集合指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列 取下标1开始2个元素
        redisTemplate.opsForZSet().rangeByScore("zset1", 10, 20, 1, 2).forEach(System.out::println);
        //通过索引区间返回有序集合成指定区间内的成员，其中有序集成员按分数值递减(从大到小)顺序排列
        redisTemplate.opsForZSet().reverseRange("zset1", 0, -1).forEach(System.out::println);
        ;
        //指定键的分数在10到20之间的元素(从大到小排序)
        redisTemplate.opsForZSet().reverseRangeByScore("zset1", 10, 20).forEach(System.out::println);
        //指定键的分数在10到20之间的元素(从大到小排序) 取下标1开始2个元素
        redisTemplate.opsForZSet().reverseRangeByScore("zset1", 10, 20, 1, 2).forEach(System.out::println);


        //通过索引区间内的成员按分数值递增(从小到大)顺序排列 并且带有分数
        Set<ZSetOperations.TypedTuple<String>> zset1 = redisTemplate.opsForZSet().rangeWithScores("zset1", 0, -1);
        Iterator<ZSetOperations.TypedTuple<String>> iterator1 = zset1.iterator();
        while (iterator1.hasNext()) {
            ZSetOperations.TypedTuple<String> typedTuple = iterator1.next();
            System.out.println("value:" + typedTuple.getValue() + "score:" + typedTuple.getScore());
        }

        //指定键的分数在10到20之间的元素(从小到大排序)并且带有分数
        Set<ZSetOperations.TypedTuple<String>> zset2 = redisTemplate.opsForZSet().rangeByScoreWithScores("zset1", 10, 20);
        Iterator<ZSetOperations.TypedTuple<String>> iterator2 = zset2.iterator();
        while (iterator2.hasNext()) {
            ZSetOperations.TypedTuple<String> typedTuple = iterator2.next();
            System.out.println("value:" + typedTuple.getValue() + "score:" + typedTuple.getScore());
        }

        //通过索引区间返回有序集合成指定区间内的成员对象，其中有序集成员按分数值递减(从大到小)顺序排列
        Set<ZSetOperations.TypedTuple<String>> zset3 = redisTemplate.opsForZSet().reverseRangeWithScores("zset1", 0, -1);
        Iterator<ZSetOperations.TypedTuple<String>> iterator3 = zset3.iterator();
        while (iterator3.hasNext()) {
            ZSetOperations.TypedTuple<String> typedTuple = iterator3.next();
            System.out.println("value:" + typedTuple.getValue() + "score:" + typedTuple.getScore());
        }

        //指定键的分数在10到20之间的元素(从小到大排序)并且带有分数
        Set<ZSetOperations.TypedTuple<String>> zset4 = redisTemplate.opsForZSet().reverseRangeByScoreWithScores("zset1", 10, 20);
        Iterator<ZSetOperations.TypedTuple<String>> iterator4 = zset4.iterator();
        while (iterator4.hasNext()) {
            ZSetOperations.TypedTuple<String> typedTuple = iterator4.next();
            System.out.println("value:" + typedTuple.getValue() + "score:" + typedTuple.getScore());
        }

        //遍历zset
        Cursor<ZSetOperations.TypedTuple<String>> cursor5 = redisTemplate.opsForZSet().scan("zzset1", ScanOptions.NONE);
        while (cursor5.hasNext()) {
            ZSetOperations.TypedTuple<String> item = cursor5.next();
            System.out.println(item.getValue() + ":" + item.getScore());
        }

    }
}
