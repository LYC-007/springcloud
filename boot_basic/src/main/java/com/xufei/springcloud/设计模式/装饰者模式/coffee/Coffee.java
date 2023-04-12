package com.xufei.springcloud.设计模式.装饰者模式.coffee;

public class Coffee  extends Drink {
    @Override
    public float cost() {
        // TODO Auto-generated method stub
        return super.getPrice();
    }
}
class DeCaf extends Coffee {

    public DeCaf() {
        setDes(" 无因咖啡 ");
        setPrice(1.0f);
    }
}

class Espresso extends Coffee {

    public Espresso() {
        setDes(" 意大利咖啡 ");
        setPrice(6.0f);
    }
}
class LongBlack extends Coffee {

    public LongBlack() {
        super.setDes(" 美式咖啡 ");
        setPrice(5.0f);
    }
}
class ShortBlack extends Coffee {
    public ShortBlack() {
        setDes(" 牛奶咖啡 ");
        setPrice(4.0f);
    }
}


