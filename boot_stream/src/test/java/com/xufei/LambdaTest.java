package com.xufei;

import org.junit.Test;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class LambdaTest {
    ArrayList<String> arrayList = new ArrayList<>();
    Map<String,String > map= new HashMap<>();
    public void test6(){
        map.forEach(new BiConsumer<String, String>() {
            @Override
            public void accept(String s, String s2) {
                System.out.println("k:"+s+"<-------->"+"v:"+s2);
            }
        });
        map.forEach((k,v)->System.out.println("k:"+k+"<-------->"+"v:"+v));
        for (Map.Entry<String,String> entry:map.entrySet()){
            System.out.println(entry.getKey()+"<------>"+entry.getValue());
        }
    }

    /**
     * foreach方法的三种变形
     */
    public void test() {
        arrayList.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                if (s.length() > 3) {
                    System.out.println(s);
                }
            }
        });
        arrayList.forEach(str -> {
            if (str.length() > 4) {
                System.out.println(str);
            }
        });
        for (String str : arrayList) {
            System.out.println(str);
        }
    }

    /**
     *
     */
    public void test3() {
        arrayList.removeIf(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.length() > 3;
            }
        });
        arrayList.removeIf(str->str.length()>3);
        Iterator<String> iterator = arrayList.iterator();
        while (iterator.hasNext()){
            if(iterator.next().length()>3){
                iterator.remove();
            }
        }
    }
    public void test4(){
        arrayList.replaceAll(new UnaryOperator<String>() {
            @Override
            public String apply(String s) {
                if(s.length()>3){
                    return s.toUpperCase();
                }
                return s;
            }
        });
        arrayList.replaceAll(s->{
            if(s.length()>3){
                return s.toUpperCase();
            }
            return s;
        });
    }
    public void test5(){
        arrayList.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        });
        arrayList.sort((str1,str2)->str2.length()-str1.length());

        Collections.sort(arrayList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        });
    }

    @Test
    public void test1() {
        happyTime(500, new Consumer<Double>() {
            @Override
            public void accept(Double aDouble) {
                System.out.println("学习太累了，去天上人间买了瓶矿泉水，价格为：" + aDouble);
            }
        });
        System.out.println("********************");
        happyTime(400, money -> System.out.println("学习太累了，去天上人间喝了口水，价格为：" + money));
    }

    public void happyTime(double money, Consumer<Double> con) {
        con.accept(money);
    }

    @Test
    public void test2() {
        List<String> list = Arrays.asList("北京", "南京", "天津", "东京", "西京", "普京");
        List<String> filterStrs = filterString(list, new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.contains("京");
            }
        });
        System.out.println(filterStrs);

        List<String> filterList = filterString(list, s -> s.contains("京"));
        System.out.println(filterList);
    }

    //根据给定的规则，过滤集合中的字符串。此规则由Predicate的方法决定
    public List<String> filterString(List<String> list, Predicate<String> pre) {
        ArrayList<String> filterList = new ArrayList<>();
        for (String s : list) {
            if (pre.test(s)) {
                filterList.add(s);
            }
        }
        return filterList;
    }

}
