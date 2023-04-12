package com.xufei.springcloud.test.annotaion;

class Foo {
    int i = 1;
    Foo() {
        System.out.println(i);//2
        int x = getValue();
        System.out.println(x);// 0 为什么呢？
        /**
         * 输出是0，为什么呢？因为在执行Foo的构造函数的过程中，由于Bar重载了Foo中的getValue方法，所以根据Java的多态特性可以知道，
         * 其调用的getValue方法是被Bar重载的那个getValue方法。但由于这时Bar的构造函数还没有被执行，因此此时j的值还是默认值0，因此此处输出是0。
         */
    }
    {
        i = 2;
    }
    protected int getValue() {
        return i;
    }
}
//子类
class Bar extends Foo {
    int j = 1;
    Bar() {
        j = 2;
    }
    {
        j = 3;
    }
    @Override
    protected int getValue() {
        return j;
    }
}
public class ConstructorExample {
    public static void main(String... args) {
        Bar bar = new Bar();
        System.out.println(bar.getValue());//2
    }
}