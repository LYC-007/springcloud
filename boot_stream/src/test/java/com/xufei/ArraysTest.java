package com.xufei;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArraysTest {
    public void test(){
        List<String> stooges = Arrays.asList("Larry", "Moe", "Curly");
        int[] arr = new int[5];//新建一个大小为5的数组
        Arrays.fill(arr,4);//给所有值赋值4
        Arrays.fill(arr, 2,4,6);//给第2位（0开始）到第4位（不包括）赋值6
        Arrays.sort(arr);  //默认升序
        Arrays.sort(arr,0,3);//给第0位（0开始）到第3位（不包括）排序
        String str = Arrays.toString(arr); // Arrays类的toString()方法能将数组中的内容全部打印出来
        System.out.println(Arrays.binarySearch(arr, 30));//二分查找法找指定元素的索引值（下标）,数组一定是排好序的，否则会出错。找到元素，只会返回最后一个位置
        System.out.println(Arrays.binarySearch(arr, 0,3,30));//从0到3位（不包括3）查找
        int[] arr3 = Arrays.copyOf(arr, 3); //截取arr数组的3个元素赋值给新数组
        int []arr4 = Arrays.copyOfRange(arr,1,3);//从第1位(包括)截取到第3位（不包括）

        int[] arr1 = {1,2,3};
        int[] arr2 = {1,2,3};
        System.out.println(Arrays.equals(arr1,arr2));//输出：true      Arrays.equals重写了equals，所以，这里能比较元素是否相等。
        System.out.println(arr1.equals(arr2));// false     equals比较的是两个对象的地址，不是里面的数
        System.out.println(arr1 == arr2);// false

        String[] strArray = new String[] { "z", "a", "C" };//字符串排序，先大写后小写
        List<String> stooge = Arrays.asList(strArray);

        Arrays.sort(strArray);//输出： [C, a, z]
        Arrays.sort(strArray, String.CASE_INSENSITIVE_ORDER);//严格按字母表顺序排序，也就是忽略大小写排序 Case-insensitive sort
        Arrays.sort(strArray, Collections.reverseOrder());//反向排序， Reverse-order sort
        Arrays.sort(strArray, String.CASE_INSENSITIVE_ORDER);//忽略大小写反向排序 Case-insensitive reverse-order sort
        Collections.reverse(Arrays.asList(strArray));// 忽略大小写反向排序 Case-insensitive reverse-order sort
    }

}
