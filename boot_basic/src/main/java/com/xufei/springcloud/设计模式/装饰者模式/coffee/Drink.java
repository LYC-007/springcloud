package com.xufei.springcloud.设计模式.装饰者模式.coffee;

/**
 * 星巴克咖啡订单项目（咖啡馆）：
 *      1) 咖啡种类/单品咖啡：Espresso(意大利浓咖啡)、ShortBlack、LongBlack(美式咖啡)、Decaf(无因咖啡)
 *      2) 调料：Milk、Soy(豆浆)、Chocolate
 *      3) 要求在扩展新的咖啡种类时，具有良好的扩展性、改动方便、维护方便
 *      4) 使用OO的来计算不同种类咖啡的费用: 客户可以点单品咖啡，也可以单品咖啡+调料组合。
 */

public abstract class Drink {
    public String des; // 描述
    private float price = 0.0f;
    public String getDes() {
        return des;
    }
    public void setDes(String des) {
        this.des = des;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    //计算费用的抽象方法
    //子类来实现
    public abstract float cost();
}


