package com.xufei.springcloud.设计模式.原型模式;

import java.util.ArrayList;

/**
 * 原型模式是一种浅拷贝
 *
 * 要实现一个原型类，需要具备三个条件：
 *      1.实现 Cloneable 接口
 *      2.重写 Object 类中的 clone 方法
 *      3.在重写的 clone 方法中调用 super.clone()
 */
public class Demo {
    public static void main(String[] args) throws CloneNotSupportedException {
        ArrayList<Student> list = new ArrayList<>();
        for(int i=0; i<10; i++){
            Student student = new Student();
            list.add(student);
        }
        //-------------使用原型模式优化成以下代码--------------//
        Student stu = new Student();
        for(int i=0; i<10; i++){
            Student student =stu.clone();
            list.add(student);
        }
    }
}
class Student implements Cloneable{
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name= name;
    }
    @Override
    protected Student clone(){
        Student clone=null;
        try {
            clone = (Student) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}
