package com.xufei.springcloud.test.annotaion;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class TestSerializableClone {

    public static void main(String[] args) {
        SPerson p1 = new SPerson(1, "ConstXiong", new SFood("米饭"));//创建 SPerson 对象 p1
        SPerson p2 = (SPerson)p1.cloneBySerializable();//克隆 p1
        p2.setName("其不答");//修改 p2 的 name 属性
        p2.getFood().setName("面条");//修改 p2 的自定义引用类型 food 属性
        System.out.println(p1);//修改 p2 的自定义引用类型 food 属性被改变，p1的自定义引用类型 food 属性未随之改变，说明p2的food属性，只拷贝了引用和 food 对象
        System.out.println(p2);
    }

}

class SPerson implements  Serializable {

    private static final long serialVersionUID = -7710144514831611031L;

    private int pid;

    private String name;

    private SFood food;

    public SPerson(int pid, String name, SFood food) {
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

    /**
     * 通过序列化完成克隆
     * @return
     */
    public Object cloneBySerializable() {
        /**
         * 调用 ByteArrayInputStream 或 ByteArrayOutputStream 对象的 close 方法没有任何意义，这两个基于内存的流只要垃圾回收器清理对象就能够释放资源，
         * 这一点不同于对外部资源（如文件流）的释放
         */
        Object obj = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            obj = ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public String toString() {
        return "Person [pid:"+pid+", name:"+name+", food:"+food.getName()+"]";
    }

    public SFood getFood() {
        return food;
    }

    public void setFood(SFood food) {
        this.food = food;
    }

}
class SFood implements Serializable {

    private static final long serialVersionUID = -3443815804346831432L;

    private String name;

    public SFood(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}