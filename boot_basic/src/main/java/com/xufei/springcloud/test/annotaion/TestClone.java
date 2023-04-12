package com.xufei.springcloud.test.annotaion;




public class TestClone {
    public static void main(String[] args) throws CloneNotSupportedException {
        Person p1 = new Person(1, "ConstXiong");//创建对象 Person p1
        Person p2 = (Person)p1.clone();//克隆对象 p1
        p2.setName("其不答");//修改 p2的name属性，p1的name未变
        System.out.println(p1);
        System.out.println(p2);
    }
}
class Person implements Cloneable {
    private int pid;
    private String name;
    public Person(int pid, String name) {
        this.pid = pid;
        this.name = name;
        System.out.println("Person constructor call");
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Person [pid:"+pid+", name:"+name+"]";
    }

}