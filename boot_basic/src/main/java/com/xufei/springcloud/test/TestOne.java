package com.xufei.springcloud.test;


public class TestOne {
    public static void main(String[] args) {
        String str = FinalClass.str;
        System.out.println(str);
        System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        FinalClass aClass = new FinalClass();
        System.out.println(aClass.str001);
    }
}
class FinalClass {
    /**
     * final修饰的变量在编译时就确定，编译期间能知道它的确切值，则编译器会把它当做编译期常量使用。
     * 也就是说在用到该final变量的地方，相当于直接访问的这个常量，不需要在运行时确定。
     * 回过头来解释上面的现象，final修饰的变量在编译期就决定确定值了，与具体的Class没有联系（可能还有藕断丝连的联系，具体不清楚），
     * 所以在执行String str = FinalClass.str;的时候看似是加载了FinalClass类，其实不然，也就不会触发静态代码块中的语句。
     */
    public  final String str001 = "aaa";
    public static final String str;
    static{
        str="fdf";
    }
    static {
        System.out.println("hello world");
    }

}