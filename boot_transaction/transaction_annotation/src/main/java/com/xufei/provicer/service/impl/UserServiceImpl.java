package com.xufei.provicer.service.impl;

import com.xufei.mapper.UserMapper;
import com.xufei.provicer.service.UserService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 事务失效案例
     * 因为Spring的默认的事务规则是遇到运行异常（RuntimeException）和程序错误（Error）才会回滚。
     * 如果想针对检查异常进行事务回滚，可以在@Transactional注解里使用 rollbackFor属性明确指定异常。
     */
    @Transactional
    public void insertUser01() throws Exception {
        userMapper.save(createUser());
        // 模拟抛出SQLException异常
        boolean flag = true;
        if (flag) {
            throw new SQLException("发生异常了..");
        }
    }

    /**
     * 事务失效案例
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertUser() {
        userMapper.save(createUser());
        // 模拟抛出SQLException异常
        try {
            // 谨慎：尽量不要在业务层捕捉异常并处理
            // 推荐做法：在业务层统一抛出异常，然后在控制层统一处理。
            //在业务层捕捉异常后，发现事务不生效。 这是许多新手都会犯的一个错误，在业务层手工捕捉并处理了异常，你都把异常“吃”掉了，Spring自然不知道这里有错，更不会主动去回滚数据
            throw new SQLException("发生异常了..");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Transactional
    public void save() {
        userMapper.save(createUser());
    }

    /**
     * 问题：在同一个类里面，编写两个方法，内部调用的时候，会导致事务设置失效，此时 b和c做任何事务设置都没有用，都是和a公用一个事务:原因时没有用到代理对象的缘故;
     * 解决方案：
     * 0）、导入 spring-boot-starter-aop
     * 1）、主启动类：@EnableTransactionManagement(proxyTargetClass = true)
     * 2）、主启动类：@EnableAspectJAutoProxy(exposeProxy=true)开启aspectj动态代理功能，以后所有动态代理都是aspectj创建的（即使没有接口也可以创建动态代理）
     * 3）、以后使用动态代理对象：AopContext.currentProxy() 调用方法
     * 只有写成下面 a方法的格式 b 和 c方法 里面的事务设置才有效果
     */
    @Transactional
    public void a() {

        UserServiceImpl orderServiceTest = (UserServiceImpl) AopContext.currentProxy();
        orderServiceTest.b();
        orderServiceTest.c();
        //此时 b和c做任何事务设置都才有用;
    }


    @Transactional(propagation = Propagation.REQUIRED, timeout = 2)
    public void b() {
        userMapper.save(createUser());
    }

    @Transactional(propagation = Propagation.REQUIRED, timeout = 2)
    public void c() {
        userMapper.save(createUser());
        int i = 1 / 0;
    }
    /**
     * 1、PROPAGATION_REQUIRED：如果当前没有事务，就创建一个新事务，如果当前存在事务，就加入该事务，该设置是最常用的设置。
     * 2、PROPAGATION_SUPPORTS：支持当前事务，如果当前存在事务，就加入该事务，如果当前不存在事务，就以非事务执行。
     * 3、PROPAGATION_MANDATORY：支持当前事务，如果当前存在事务，就加入该事务，如果当前不存在事务，就抛出异常。
     * 4、PROPAGATION_REQUIRES_NEW：创建新事务，无论当前存不存在事务，都创建新事务。
     * 5、PROPAGATION_NOT_SUPPORTED：以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。
     * 6、PROPAGATION_NEVER：以非事务方式执行，如果当前存在事务，则抛出异常。
     * 7、PROPAGATION_NESTED：如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与 PROPAGATION_REQUIRED 类似的操作。
     */

    /**
     * 模拟数据
     */
    public static Map<String, Object> createUser() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "walking" + new Random().nextInt(Integer.MAX_VALUE));
        map.put("sex", new Random().nextInt(1));
        map.put("age", new Random().nextInt(80));
        return map;
    }
}
