package com.xufei.file.other;

import org.junit.Test;

import java.io.*;
public class OtherStreamTest {
    @Test
    public void test() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        while (true) {
            System.out.println("请输入字符串：");
            String data = br.readLine();
            if ("e".equalsIgnoreCase(data) || "exit".equalsIgnoreCase(data)) {
                System.out.println("程序结束");
                break;
            }
            System.out.println(data.toUpperCase());
        }
    }
    @Test
    public void test2() throws Exception {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(new File("dbcp.txt")),"utf-8");
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(new File("dbcp_gbk.txt")),"gbk");
        char[] cbuf = new char[20];
        int len;
        while((len = isr.read(cbuf)) != -1){
            osw.write(cbuf,0,len);
        }
    }

    /*
    数据流:DataInputStream 和 DataOutputStream,作用：用于读取或写出基本数据类型的变量或字符串
    注意点：读取不同类型的数据的顺序要与当初写入文件时，保存的数据的顺序一致！
     */
    @Test
    public void test4() throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream("data.txt"));
        dos.writeUTF("刘建辰");
        dos.flush();//刷新操作，将内存中的数据写入文件
        dos.writeInt(23);
        dos.flush();
        dos.writeBoolean(true);
        dos.flush();
        dos.close();

        DataInputStream dis = new DataInputStream(new FileInputStream("data.txt"));
        String name = dis.readUTF();
        int age = dis.readInt();
        boolean isMale = dis.readBoolean();
        System.out.println("name = " + name);
        System.out.println("age = " + age);
        System.out.println("isMale = " + isMale);
        dis.close();
    }
}
