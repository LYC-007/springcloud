package com.xufei.springcloud.test.annotaion;


public class TestManalDeepClone {

    public static void main(String[] args) throws Exception {
        DPerson p1 = new DPerson(1, "ConstXiong", new DFood("米饭"));//创建Person 对象 p1
        DPerson p2 = (DPerson)p1.clone();//克隆p1
        p2.setName("其不答");//修改p2的name属性
        p2.getFood().setName("面条");//修改p2的自定义引用类型 food 属性
        System.out.println(p1);//修改p2的自定义引用类型 food 属性被改变，p1的自定义引用类型 food 属性也随之改变，说明p2的food属性，只拷贝了引用，没有拷贝food对象
        System.out.println(p2);
    }

}

class DPerson implements Cloneable {

    private int pid;

    private String name;

    private DFood food;

    public DPerson(int pid, String name, DFood food) {
        this.pid = pid;
        this.name = name;
        this.food = food;
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
    public Object clone() throws CloneNotSupportedException {
        DPerson p = (DPerson)super.clone();
        p.setFood((DFood)p.getFood().clone());
        return p;
    }

    @Override
    public String toString() {
        return "Person [pid:"+pid+", name:"+name+", food:"+food.getName()+"]";
    }

    public DFood getFood() {
        return food;
    }

    public void setFood(DFood food) {
        this.food = food;
    }

}

class DFood implements Cloneable{

    private String name;

    public DFood(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}