package com.xufei.springcloud.test.annotaion;

public class NotInitialization{
    public static void main(String[] args){
        System.out.println(SubClass.value);
    }
}/* Output:
        SSClass
        SClass init!
        123
 *///:~