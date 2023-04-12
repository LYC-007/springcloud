package com.xufei.springcloud.test.annotaion;

import java.lang.annotation.*;
import java.lang.reflect.Field;

/**
 * 1： 定义注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface FruitProvider {
    /**
     * 供应商编号
     */
    public int id() default -1;

    /**
     * 供应商名称
     */
    public String name() default "";

    /**
     * 供应商地址
     */
    public String address() default "";
}

//2：注解使用
public class Apple {
    @FruitProvider(id = 1, name = "陕西红富士集团", address = "陕西省西安市延安路")
    private String appleProvider;

    public void setAppleProvider(String appleProvider) {
        this.appleProvider = appleProvider;
    }

    public String getAppleProvider() {
        return appleProvider;
    }

    public static void main(String[] args) {
        FruitInfoUtil.getFruitInfo(Apple.class);
/********输出结果**********/
// 供应商编号：1 供应商名称：陕西红富士集团 供应商地址：陕西省西安市延
    }
}

/**
 * 3：******** 注解处理器
 **********/
class FruitInfoUtil {
    public static void getFruitInfo(Class<?> clazz) {
        String strFruitProvicer = "供应商信息：";
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(FruitProvider.class)) {//isAnnotationPresent(FruitProvider.class)判断属性是否标有FruitProvider注解
                FruitProvider fruitProvider = field.getAnnotation(FruitProvider.class);
                //注解信息的处理地方
                strFruitProvicer = " 供应商编号：" + fruitProvider.id() + " 供应商名称："
                        + fruitProvider.name() + " 供应商地址：" +
                        fruitProvider.address();
                System.out.println(strFruitProvicer);
            }
        }
    }
}