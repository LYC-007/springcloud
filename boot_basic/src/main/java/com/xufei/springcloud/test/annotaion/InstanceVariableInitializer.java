package com.xufei.springcloud.test.annotaion;
public class InstanceVariableInitializer {

    private int i = 1;
    private int j = i + 1;
    //实例变量初始化、实例代码块初始化 以及 构造函数初始化
    public InstanceVariableInitializer(int var){
        System.out.println(i);
        System.out.println(j);
        this.i = var;
        System.out.println(i);
        System.out.println(j);
    }
    {               // 实例代码块
        i=2;
        j += 3;
    }

    /**
     * 我们在定义（声明）实例变量的同时，还可以直接对实例变量进行赋值或者使用实例代码块对其进行赋值。如果我们以这两种方式为实例变量进行初始化，那么它们将在构造函数执行之前完成这些初始化操作。
     * 实际上，如果我们对实例变量直接赋值或者使用实例代码块赋值，那么编译器会将其中的代码放到类的构造函数中去，
     * 并且这些代码会被放在对超类构造函数的调用语句之后(还记得吗？Java要求构造函数的第一条语句必须是超类构造函数的调用语句)，构造函数本身的代码之前。
     * @param args
     */
    public static void main(String[] args) {
        new InstanceVariableInitializer(8);
    }
}/* Output:
            1
            5
            8
            5
 *///:~