package com.xufei.springcloud.aop.aspectj;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

@Order(1)
//当一个方法被多个AOP增强时，定义多个 AOP 执行顺序,Order 越小的越优先执行，但是，After方法反而越后面执行(具体的执行顺序请自己测试)
@Slf4j
@Aspect
@Component
public class LogAOP {
    /**
     * PointCut是指哪些方法需要被执行"AOP"，PointCut表达式可以有以下几种方式:
     * 1.execution([可见性]返回类型[声明类型].方法名(参数)[异常])，其中[]内的是可选的，其他的还支持通配符的使用：
     *      execution([权限修饰符] [返回类型] [类全路径] [方法名称]([参数列表]) )
     *      拦截任意公共方法:execution(public * *(..))
     *      拦截以set开头的任意方法:execution(* set*(..))
     *      拦截类或者接口中的方法:execution(* com.xyz.service.AccountService.*(..))
     *      拦截包中定义的方法，不包含子包中的方法:execution(* com.xyz.service.*.*(..))
     *      拦截包或者子包中定义的方法:execution(* com.xyz.service..*.*(..))
     *      *: 匹配所有
     *      ..: 匹配多个包或多个参数
     *      +: 表示类及其子类
     *      运算符：&&、||、！
     * 2.within  是用来指定类型的，指定类型中的所有方法将被拦截是用来指定类型的，指定类型中的所有方法将被拦截
     *      within(com.demo.service.impl.UserServiceImpl) 匹配UserServiceImpl类对应对象的所有方法调用，并且只能是UserServiceImpl对象，不能是它的子对象
     *      within(com.demo...*)匹配com.demo包及其子包下面的所有类的所有方法的外部调用
     * 3.this  SpringAOP是基于代理的，this就代表代理对象，语法是this(type)，当生成的代理对象可以转化为type指定的类型时表示匹配。
     *      this(com.test.spring.aop.pointcutexp.Intf) 实现了Intf接口的所有类,如果Intf不是接口,限定Intf单个类.
     * 4.target   SpringAOP是基于代理的，target表示被代理的目标对象，当被代理的目标对象可以转换为指定的类型时则表示匹配。
     *      target(com.demo.service.IUserService) 匹配所有被代理的目标对象能够转化成IuserService类型的所有方法的外部调用。
     * 5.args用来匹配方法参数
     *      args() 匹配不带参数的方法
     *      args(java.lang.String) 匹配方法参数是String类型的
     *      args(...) 带任意参数的方法
     *      args(java.lang.String,…) 匹配第一个参数是String类型的，其他参数任意。最后一个参数是String的同理。
     * 6.@within 和 @target  带有相应标注的所有类的任意方法，比如@Transactional
     *      @within(org.springframework.transaction.annotation.Transactional) 拦截带有@Transactional标注的所有类的任意方法.
     *      @target(org.springframework.transaction.annotation.Transactional) 拦截带有@Transactional标注的所有类的任意方法.
     *      注意:@within和@target针对类的注解,@annotation是针对方法的注解
     * 7.@annotation 带有相应标注的任意方法，比如@Transactional或其他自定义注解 @annotation(org.springframework.transaction.annotation.Transactional)
     * 8.@args  参数带有相应标注的任意方法
     *      @args(org.springframework.transaction.annotation.Transactional) 参数带有@Transactional标注的方法.
     */
    @Pointcut(value = "@annotation(com.xufei.springcloud.aop.aspectj.Log)")
    public void pointCut() {
    }

    /**
     * 环绕通知
     * 环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
     * 环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型
     */
    @Around(value = "pointCut()")
    public Object doAroundAdvice(ProceedingJoinPoint point) throws Throwable {
        log.info("方法的名字是:前环绕通知！！！+++++++++++++000000000000000000000000000000000000000000000");
        Object[] args = point.getArgs();    // 获取请求参数
        log.info("interceptor-入参：{}", Arrays.toString(args));  //[User [id=MTAwMTE=, name=emhhb2hlbmc=]]
        String string = JSONObject.toJSONString(args[0]);//注意这里只有一个参数，如果有多个要分别处理
        Map<String, Object> map = JSON.parseObject(string, new TypeReference<Map<String, Object>>() {
        });
        // 把json对象转换为Map集合便于遍历处理参数
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            /*
             * 入参是base64编码，这里进行解码；也可以是其他方式加密，这里进行解密
             */
            byte[] base64decodedBytes = Base64.getDecoder().decode(entry.getValue().toString());
            String valueString = new String(base64decodedBytes, StandardCharsets.UTF_8);
            map.put(entry.getKey(), valueString);
        }
        // 获取接收参数的类全路径
        String classPath = args[0].getClass().getName();
        // 把处理后的参数放回去
        args[0] = JSONObject.parseObject(JSONObject.toJSONString(map), Class.forName(classPath));

        Object proceed = point.proceed(args);  //获取目标方法执行完后的返回值
        //修改返回给前端的数据
        Map<String, String> responseMap = JSONObject.parseObject((String) proceed, new TypeReference<Map<String, String>>() {});
        responseMap.put("status","success");
        log.info("方法的名字是:后环绕通知！！！+++++++++++++000000000000000000000000000000000000000000000");
        return JSONObject.toJSONString(responseMap);
    }

    /**
     * 前置通知，在切点执行之前执行的操作
     */
    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        log.info("方法的名字是:前置通知！！！+++++++++++++++++++++++++");
    }

    /**
     * 后置返回通知
     * 如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
     * 如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     * returning：限定了执行的目标方法的返回值与通知方法参数类型相对应时才能执行后置返回通知，否则不执行,对于returning对应的通知方法参数为Object类型将匹配任何目标返回值
     */
    @AfterReturning(value = "pointCut()", returning = "keys")
    public void doAfterReturningAdvice(JoinPoint joinPoint, Object keys) {
        log.info("方法的名字是:后置返回通知-----------------------------");
        /*
        JoinPoint API测试:
        //1.获取切入点所在目标对象
        Object targetObj = joinPoint.getTarget();
        System.out.println(targetObj.getClass().getName());
        // 2.获取切入点方法的名字
        String methodName = joinPoint.getSignature().getName();
        //System.out.println("切入方法名字：" + methodName);
        // 3. 获取方法上的注解
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            Log log = method.getAnnotation(Log.class);
            //System.out.println("切入方法注解的title:" + log.title());
        }
        //4. 获取方法的参数
        Object[] args = joinPoint.getArgs();
        for (Object o : args) {
            //System.out.println("切入方法的参数：" + o);
        }
        */

    }
    /**
     * 后置异常通知
     * 定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将把目标方法抛出的异常传给通知方法；
     * throwing:限定了只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行，
     * 对于throwing对应的通知方法参数为Throwable类型将匹配任何异常。
     */
    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void doAfterThrowingAdvice(JoinPoint joinPoint, Throwable exception) {
        //目标方法名
        log.info(joinPoint.getSignature().getName());
        if (exception instanceof NullPointerException) {
            log.info("发生了空指针异常!!!!!");
        }
    }
    /**
     * 后置最终通知（目标方法只要执行完了就会执行后置通知方法）
     */
    @After(value = "pointCut()")
    public void doAfterAdvice(JoinPoint joinPoint) {
        log.info("方法的名字是:后置最终通知！！！+++++++++++++++++++++++++");
    }
}
