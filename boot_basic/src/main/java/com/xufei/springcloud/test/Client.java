package com.xufei.springcloud.test;
// 实现 Cloneable 接口的原型抽象类 Prototype
class Prototype implements Cloneable {
    // 重写 clone 方法
    public Prototype clone(){
        Prototype prototype = null;
        try{
            prototype = (Prototype)super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return prototype;
    }
}
// 实现原型类
class ConcretePrototype extends Prototype{
    public void show(){
        System.out.println(" 原型模式实现类 ");
    }
}
public class Client {
    public static void main(String[] args){
        ConcretePrototype cp = new ConcretePrototype();
        for(int i=0; i< 10; i++){
            ConcretePrototype clonecp = (ConcretePrototype)cp.clone();
            clonecp.show();
        }
    }
}