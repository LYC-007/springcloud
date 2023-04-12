package com.xufei.springcloud.test;

public class TestA {
    public static void main(String[] args) {
        TestA testA = new TestA();
        int test = testA.test();

        System.out.println(test);
    }


    public int test(){
        int j=90;
        int method = method(j);

        int g=888;
        return method+g;
    }

    public int method(int j){
        int t=89;
        return t+j;
    }
}
