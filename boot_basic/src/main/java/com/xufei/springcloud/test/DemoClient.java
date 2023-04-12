package com.xufei.springcloud.test;

import java.util.HashMap;
import java.util.Map;

/**
 *享元模式一般可以分为三个角色，分别为 Flyweight（抽象享元类）、ConcreteFlyweight（具体享元类）和 FlyweightFactory（享元工厂类）。
 *      1.抽象享元类通常是一个接口或抽象类，向外界提供享元对象的内部数据或外部数据；
 *      2.具体享元类是指具体实现内部数据共享的类；
 *      3.享元工厂类则是主要用于创建和管理享元对象的工厂类。
 * 举例:
 *      1.线程池就是享元模式的一种实现；
 *      2.将商品存储在应用服务的缓存中，那么每当用户获取商品信息时，则不需要每次都从 redis 缓存或者数据库中获取商品信息，并在内存中重复创建商品信息了。
 *      3.Java 的 String 字符串，在一些字符串常量中，会共享常量池中字符串对象，从而减少重复创建相同值对象，占用内存空间。
 */
public class DemoClient {
    public static void main(String[] args) {
        Flyweight fw0 = FlyweightFactory.getFlyweight("a");
        Flyweight fw1 = FlyweightFactory.getFlyweight("b");
        Flyweight fw2 = FlyweightFactory.getFlyweight("a");
        Flyweight fw3 = FlyweightFactory.getFlyweight("b");
        fw1.operation("abc");
        System.out.printf("[结果 (对象对比)] - [%s]\n", fw0 == fw2);
        System.out.printf("[结果 (内在状态)] - [%s]\n", fw1.getType());
    }

}
// 享元工厂类
class FlyweightFactory {
    private static final Map<String, Flyweight> FLYWEIGHT_MAP = new HashMap<>();// 享元池
    public static Flyweight getFlyweight(String type) {
        if (FLYWEIGHT_MAP.containsKey(type)) {// 如果在享元池中存在对象，则直接获取
            return FLYWEIGHT_MAP.get(type);
        } else {// 在响应池不存在，则新创建对象，并放入到享元池
            ConcreteFlyweight flyweight = new ConcreteFlyweight(type);
            FLYWEIGHT_MAP.put(type, flyweight);
            return flyweight;
        }
    }
}
// 具体享元类
class ConcreteFlyweight implements Flyweight {
    private String type;
    public ConcreteFlyweight(String type) {
        this.type = type;
    }
    @Override
    public void operation(String name) {
        System.out.printf("[类型 (内在状态)] - [%s] - [名字 (外在状态)] - [%s]\n", type,name);
    }
    @Override
    public String getType() {
        return type;
    }
}
// 抽象享元类
interface Flyweight {
    // 对外状态对象
    void operation(String name);
    // 对内对象
    String getType();
}