package com.xufei.spirngcloud.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * sync = true 是否同步，true/false。在一个多线程的环境中，某些操作可能被相同的参数并发地调用，这样同一个 value 值可能被多次计算（或多次访问 db），这样就达不到缓存的目的。
 * 针对这些可能高并发的操作，我们可以使用 sync 参数来告诉底层的缓存提供者将缓存的入口锁住，这样就只能有一个线程计算操作的结果值，而其它线程需要等待，这样就避免了 n-1 次数据库访问。
 *
 * sync = true 可以有效的避免缓存击穿的问题。
 */
@Service
public class IndexServiceImpl {
    @Cacheable(value={"category"},key = "#root.methodName",sync = true)
    //代表当前方法的结果需要缓存，如果缓存中有则方法不用调用直接返回缓存中的数据;没有会调用方法;
    public ArrayList<String> find() {
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("1");
        arrayList.add("2");
        return arrayList;
    }
    @Cacheable(value={"category"},key = "#root.methodName",sync = true)
    //代表当前方法的结果需要缓存，如果缓存中有则方法不用调用直接返回缓存中的数据;没有会调用方法;
    public ArrayList<String> select() {  //测试配置文件是否能缓存 null 值;
        return null;
    }
    /*
读模式使用缓存的注解:
         @Cacheable(value={"category"},key = "#root.methodName",sync = true)//代表当前方法的结果需要缓存，如果缓存中有则方法不用调用直接返回缓存中的数据;没有会调用方法;
        说明:
            缓存的分区名字为:"category" ;
            存入到Redis中的“key”为 : "方法的名字-->#root.methodName这个就是取方法名字的表达式（SpEL语法）更多的key生产规则查看官网;
            sync = true
写模式使用缓存的注解:
        A.失效模式:
                @CacheEvict(value = "缓存的分区名字",key = "'保存的名字'")------>触发将数据从缓存中删除的操作;
                value指定要清空的是那个缓存分区;
                key:指定这个缓存分区里面的那个key;
                注意:一次性清空多个key的方式:
                    1):
                    @Caching(evict = {//用来 @Cacheing 组合多个操作
                            @CacheEvict(value = "",key = "''"),
                            @CacheEvict(value = "",key = "''")
                    })
                    2):@CacheEvict(value = "缓存分区的名字",allEntries =true) :将清除缓存分区中的所有“key-value”
        B.双写模式:@CachePut  ---->插入数据时清空以前的旧缓存，并且保存一个更新的新数据在Redis中;
                 (方法返回的数据正好是修改后的,这个方法有修改也有返回，且返回的数据是修改后的)
     */
}
