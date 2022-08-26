package com.xufei.file.two;

import org.junit.Test;

import java.io.*;

/**
 * 处理流之一：缓冲流的使用
 * 1.缓冲流：
 * BufferedInputStream
 * BufferedOutputStream
 * BufferedReader
 * BufferedWriter
 * 2.作用：提供流的读取、写入的速度,提高读写速度的原因：内部提供了一个缓冲区
 * 3. 处理流，就是“套接”在已有的流的基础上。
 */
public class BufferedTest {
    @Test
    public void BufferedStreamTest() throws IOException {//实现非文本文件的复制
        File srcFile = new File("爱情与友情.jpg");
        File destFile = new File("爱情与友情3.jpg");
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));
        byte[] buffer = new byte[10];
        int len;
        while ((len = bis.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
    }

    //实现文件复制的方法
    public void copyFileWithBuffered(String srcPath, String destPath) throws IOException {
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));
        byte[] buffer = new byte[1024];
        int len;
        while ((len = bis.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
    }
    @Test
    public void testBufferedReaderBufferedWriter() throws IOException {
        //使用BufferedReader和BufferedWriter实现文本文件的复制
        BufferedReader br = new BufferedReader(new FileReader(new File("dbcp.txt")));
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("dbcp1.txt")));
        //方式一：使用char[] 数组
//        char[] cbuf = new char[1024];
//        int len;
//        while ((len = br.read(cbuf)) != -1) {
//            bw.write(cbuf, 0, len);
//        }
        //方式二：使用String
        String data;
        while ((data = br.readLine()) != null) {
            //方法一：
            //bw.write(data + "\n");//data中不包含换行符
            //方法二：
            bw.write(data);//data中不包含换行符
            bw.newLine();//提供换行的操作
        }
    }

}
