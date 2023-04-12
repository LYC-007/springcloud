package com.xufei.limit.rate;

import com.xufei.limit.annotation.SemaphoreLimit;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 *
 * 使用java.util.concurrent.Semaphore(信号量)，可以用来控制同时访问特定资源的线程数量，常用于限流场景,常用方法:
 *      1.acquire()	获取一个许可证，在获取到许可证、或者被其他线程调用中断之前线程一直处于阻塞状态。
 *      2.acquire(int permits)	一次性获取多个许可证，在获取到多个许可证、或者被其他线程调用中断、或超时之前线程一直处于阻塞状态。
 *      3.acquireUninterruptibly()	获取一个许可证，在获取到许可证之前线程一直处于阻塞状态（忽略中断）。
 *      4.tryAcquire()	尝试获取许可证，返回获取许可证成功或失败，不阻塞线程。
 *      5.tryAcquire(long timeout, TimeUnit unit)	尝试获取许可证，在超时时间内循环尝试获取，直到尝试获取成功或超时返回，不阻塞线程。
 *      6.release()	释放一个许可证，唤醒等待获取许可证的阻塞线程。
 *      7.release(int permits)	一次性释放多个许可证。
 *      8.drainPermits()	清空许可证，把可用许可证数置为0，返回清空许可证的数量。
 * Semaphore有两个构造函数：
 *      1.第一个构造函数接收一个int型参数permits，表示初始化许可证的数量，并且默认使用非公平锁。
 *      2.第二个构造函数接收两个参数，第二个boolean型参数fair可以用来选择是使用公平锁还是非公平锁。
 **/
@Component
public class InitSemaphoreLimit implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(RestController.class);
        beanMap.forEach((k, v) -> {
            Class<?> controllerClass = v.getClass();
            //获取所有声明的方法
            Method[] allMethods = controllerClass.getSuperclass().getDeclaredMethods();
            for (Method method : allMethods) {
                //判断方法是否使用了限流注解
                if (method.isAnnotationPresent(SemaphoreLimit.class)) {
                    //获取配置的限流量,实际值可以动态获取,配置key,根据key从配置文件获取
                    int value = method.getAnnotation(SemaphoreLimit.class).value();
                    String key = method.getAnnotation(SemaphoreLimit.class).limitKey();
                    if(key.equals("")){
                        key=method.getName();
                    }
                    System.out.println("SemaphoreLimitKey:" +key+",许可证数是"+value);
                    //key作为key.value为具体限流量,传递到切面的map中
                    com.xufei.limit.aspect.SemaphoreLimitAspect.semaphoreMap.put(key, new Semaphore(value));
                }
            }
        });
    }
}
