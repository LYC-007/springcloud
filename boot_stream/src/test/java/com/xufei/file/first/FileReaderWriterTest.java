package com.xufei.file.first;

import org.junit.Test;

import java.io.*;

/**
 * 一、流的分类：
 * 1.操作数据单位：字节流、字符流
 * 2.数据的流向：输入流、输出流
 * 3.流的角色：节点流、处理流
 * <p>
 * 二、流的体系结构
 * 抽象基类         节点流（或文件流）                               缓冲流（处理流的一种）
 * InputStream     FileInputStream   (read(byte[] buffer))        BufferedInputStream (read(byte[] buffer))
 * OutputStream    FileOutputStream  (write(byte[] buffer,0,len)  BufferedOutputStream (write(byte[] buffer,0,len) / flush()
 * Reader          FileReader (read(char[] cbuf))                 BufferedReader (read(char[] cbuf) / readLine())
 * Writer          FileWriter (write(char[] cbuf,0,len)           BufferedWriter (write(char[] cbuf,0,len) / flush()
 */
public class FileReaderWriterTest {
    public static void main(String[] args) {
        File file = new File("hello.txt");//相较于当前工程
        System.out.println(file.getAbsolutePath());
    }

    @Test
    public void testFileReader() throws IOException {
        File file = new File("hello.txt");//相较于当前Module
        FileReader fr = new FileReader(file);
        int data;
        while ((data = fr.read()) != -1) {//read()的理解：返回读入的一个字符。如果达到文件末尾，返回-1(一个字符字符的读取)
            System.out.print((char) data);
        }

    }
    //对read()操作升级：使用read的重载方法
    @Test
    public void testFileReader1() throws IOException {
        File file = new File("hello.txt");
        FileReader fr = new FileReader(file);
        char[] cbuf = new char[5];
        int len;
        while ((len = fr.read(cbuf)) != -1) {//read(char[] cbuf):返回每次读入cbuf数组中的字符的个数。如果达到文件末尾，返回-1
            System.out.print(new String(cbuf, 0, len));
        }
    }
    @Test
    public void testFileWriter() throws IOException {
        File file = new File("hello1.txt");
        //FileWriter(file,false) / FileWriter(file):对原有文件的覆盖
        //FileWriter(file,true):不会对原有文件覆盖，而是在原有文件基础上追加内容
        FileWriter fw = new FileWriter(file, false);
        //从内存中写出数据到硬盘的文件里。File对应的硬盘中的文件如果不存在，在输出的过程中，会自动创建此文件。
        fw.write("I have a dream!\n");
        fw.write("you need to have a dream!");
    }

    @Test
    public void testFileReaderFileWriter() throws IOException {
        File srcFile = new File("hello.txt");
        File destFile = new File("hello2.txt");
        FileReader fr = new FileReader(srcFile);
        FileWriter fw =  new FileWriter(destFile);
        char[] cbuf = new char[5];
        int len;
        while ((len = fr.read(cbuf)) != -1) {
            //每次写出len个字符
            fw.write(cbuf, 0, len);
        }

    }

}
