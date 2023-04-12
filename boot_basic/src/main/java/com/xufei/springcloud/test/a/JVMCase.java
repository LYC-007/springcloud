package com.xufei.springcloud.test.a;

import lombok.Data;

public class JVMCase {
    public final static String MAN_SEX_TYPE = "man";
    // 静态变量
    public static String WOMAN_SEX_TYPE = "woman";
    public static void main(String[] args) {
        Student stu = new Student();
        stu.setName("nick");
        stu.setSexType(MAN_SEX_TYPE);
        stu.setAge(20);
        JVMCase jvmcase = new JVMCase();
        // 调用静态方法
        print(stu);
        // 调用非静态方法
        jvmcase.sayHello(stu);
    }
    // 常规静态方法
    public static void print(Student stu) {
        System.out.println("name: " + stu.getName() + "; sex:" + stu.getSexType() + "; age:" + stu.getAge());
    }

    // 非静态方法
    public void sayHello(Student stu) {
        System.out.println(stu.getName() + "say: hello");
    }
}
@Data
class Student {
    String name;
    String sexType;
    int age;
}
