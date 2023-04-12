package com.xufei.springcloud.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @Author: XuFei
 * <p>
 * xttuk   ikjdj  qpgea
 * 78177 83525  75435
 * @Date: 2022/9/20 10:26
 */
public class ExceptionTest {

    public static void main(String[] args) {

        int j = 0;
        for (int i = 0; i < 100; i++) {
            j = j++;
            System.out.println(j);
        }
        System.out.println(j);
    }

}
