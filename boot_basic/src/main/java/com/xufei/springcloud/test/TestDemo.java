package com.xufei.springcloud.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 首先，请求 test0 链接 10000 次，之后再请求 test1 链接 10000 次，这个时候我们请求test1 的接口报异常了。
 *
 * 在启动应用程序之前，我们可以通过 HeapDumpOnOutOfMemoryError 和HeapDumpPath 这两个参数开启堆内存异常日志，通过以下命令启动应用程序：
 * java -jar -Xms1000m -Xmx4000m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/heap....
 */
public class TestDemo {
    final static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(100, 100, 1, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>());// 创建线程池，通过线程池，保证创建的线程存活
    final static ThreadLocal<Byte[]> localVariable = new ThreadLocal<Byte[]>();

    @RequestMapping(value = "/test0")
    public String test0(HttpServletRequest request) {
        poolExecutor.execute(new Runnable() {
            public void run() {
                Byte[] c = new Byte[4096 * 1024];
                localVariable.set(c);// 为线程添加变量
            }
        });
        return "OK";
    }

    @RequestMapping(value = "/test1")
    public String test1(HttpServletRequest request) {
        List<Byte[]> temp1 = new ArrayList<>();
        Byte[] b = new Byte[1024 * 20];
        temp1.add(b);// 添加局部变量
        return "OK";
    }
}
