package com.xufei.springcloud.设计模式.装饰者模式.coffee;

public class Decorator extends Drink {//装饰者
    private Drink obj;

    public Decorator(Drink obj) { //组合
        // TODO Auto-generated constructor stub
        this.obj = obj;
    }


    @Override
    public float cost() {//计算价格
        // TODO Auto-generated method stub
        // getPrice 自己价格
        return super.getPrice() + obj.cost();
    }

    @Override
    public String getDes() {//商品描述
        // TODO Auto-generated method stub
        // obj.getDes() 输出被装饰者的信息
        return des + " " + getPrice() + " && " + obj.getDes();
    }
}
//具体的Decorator， 这里就是调味品
class Chocolate extends Decorator {//巧克力
    public Chocolate(Drink obj) {
        super(obj);
        setDes(" 巧克力 ");
        setPrice(3.0f); // 调味品 的价格
    }
}
class Milk extends Decorator {//牛奶

    public Milk(Drink obj) {
        super(obj);
        // TODO Auto-generated constructor stub
        setDes(" 牛奶 ");
        setPrice(2.0f);
    }

}
class Soy extends Decorator {//豆浆
    public Soy(Drink obj) {
        super(obj);
        // TODO Auto-generated constructor stub
        setDes(" 豆浆  ");
        setPrice(1.5f);
    }
}




