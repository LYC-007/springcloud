package com.xufei.springcloud.test.a;

import com.xufei.springcloud.test.v.v;
import lombok.Data;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * @Author: XuFei
 * @Date: 2022/9/28 19:32
 */
public class D implements Serializable {
    public static void main(String[] args) throws Exception {
        ArrayList<String> arrayList = new ArrayList<>();
        List<String> names = Arrays.asList(" 张三 ", " 李四 ", " 王老五 ", " 李三 ", " 刘老四 ");
        String maxLenStartWithZ = names.stream()
                .filter(name -> name.startsWith(" 张 "))
                .mapToInt(String::length)
                .max()
                .toString();
        System.out.println(System.currentTimeMillis());
        LinkedList<String> strings = new LinkedList<>();
        A a = new A();
        a.test();
        v v = new v();
        v.test();

        test();


    }

    public static void test() throws IOException {
        User user = new User();
        user.setUserName("test");
        user.setPassword("test");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(os);
            out.writeObject(user);
            out.flush();
            out.close();
            byte[] testByte = os.toByteArray();
            os.close();
        }
        long endTime = System.currentTimeMillis();
        System.out.print("ObjectOutputStream 序列化时间：" + (endTime - startTime) + "\n");


        long startTime1 = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(2048);//分配在 java 堆内存，读写效率较低，受到 GC 的影响
            byte[] userName = user.getUserName().getBytes();
            byte[] password = user.getPassword().getBytes();
            byteBuffer.putInt(userName.length);
            byteBuffer.put(userName);
            byteBuffer.putInt(password.length);
            byteBuffer.put(password);
            byteBuffer.flip();//flip 方法将 Buffer 从写模式切换到读模式。调用 flip()方法会将 position 设回 0，并将 limit 设置成之前 position 的值。
            // 换句话说，position 现在用于标记读的位置，limit 表示之前写进了多少个 byte、char 等 （现在能读取多少个 byte、char 等）。
            byte[] bytes = new byte[byteBuffer.remaining()];//返回当前位置与限制之间的元素数
        }
        long endTime1 = System.currentTimeMillis();
        System.out.print("ByteBuffer 序列化时间：" + (endTime1 - startTime1) + "\n");

    }
}

@Data
class User implements Serializable {
    private String userName;
    private String password;
}
