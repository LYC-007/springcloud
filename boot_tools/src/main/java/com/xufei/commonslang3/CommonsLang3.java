package com.xufei.commonslang3;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class CommonsLang3 {

    public static void main(String[] args) {

        //随机生成n位数数字
        System.out.println(RandomStringUtils.randomNumeric(6));

        //在指定字符串中生成长度为n的随机字符串
        System.out.println(RandomStringUtils.random(5, "abcdefghijk"));

        //指定从字符或数字中生成随机字符串
        System.out.println(RandomStringUtils.random(3, true, false));
        System.out.println(RandomStringUtils.random(3, false, true));
        System.out.println(DateFormatUtils.format(new Date(), "yyyy年-MM月-dd日"));

    }

    public static void arrayUtils() {
        int[] arrayInt = ArrayUtils.EMPTY_INT_ARRAY;//提供了8中基本数据类型以及包装类以及各种类型的长度为0的空数组。
        Integer[] arrayInteger = new Integer[]{1, 2, 3};
        System.out.println(arrayInteger.toString());//功能基本同java自己的Arrays.toString方法
        Integer[] integers = ArrayUtils.toArray(1, 2, 3);
        Serializable[] serializables = ArrayUtils.toArray(1, 2, "3");
        Arrays.stream(serializables).forEach(System.out::println);

    }
}
