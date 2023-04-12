package com.xufei.springcloud.test;

import lombok.Data;

import java.util.ArrayList;


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
