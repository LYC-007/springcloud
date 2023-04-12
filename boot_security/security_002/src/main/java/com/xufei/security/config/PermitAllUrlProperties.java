package com.xufei.security.config;

import com.xufei.security.annotation.Anonymous;

import org.apache.commons.lang3.RegExUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 设置Anonymous注解允许匿名访问的url
 * 该配置类 项目启动就会被加载
 * 实现了 ApplicationContextAware 类 就可以在本类获取 bean 对象
 * 凡是继承 InitializingBean 的类，在初始化bean的时候都会执行 afterPropertiesSet 方法
 * <p>
 * <p>
 * 注意：1、在Spring初始化bean的时候，如果该bean实现了InitializingBean接口，并且同时在配置文件中指定了init-method，
 * 系统则是先调用afterPropertieSet()方法，然后再调用init-method中指定的方法
 * Spring为bean提供了两种初始化bean的方式，实现 InitializingBean 接口，
 * 实现afterPropertiesSet方法，或者在配置文件中通过init-method指定，两种方式可以同时使用。
 * 2、实现InitializingBean接口是直接调用afterPropertiesSet方法，
 * 比通过反射调用 init-method 指定的方法效率要高一点，但是init-method方式消除了对spring的依赖。
 * 3、如果调用 afterPropertiesSet 方法时出错，则不调用init-method指定的方法。
 * <p>
 * BeanPostProcessor，该接口中有两个方法，InitializingBean发挥作用的时机就在这两个方法之间。从语义也很好看出来，
 * postProcessBeforeInitialization -> initializingBean -> postProcessAfterInitialization
 * afterPropertiesSet 发生作用的时机是当前类的实例化的时候，而 BeanPostProcessor 则是所有类，这也是为什么 afterPropertiesSet 的函数中没有参数
 * <p>
 * postProcessBeforeInitialization方法在bean初始化之前执行， postProcessAfterInitialization方法在bean初始化之后执行。
 * <p>
 * 构造函数 -> postProcessBeforeInitialization -> @PostConstruct ->  initializingBean -> init-method  -> postProcessAfterInitialization
 * 由此可见初始化Bean的先后顺序为：
 * Bean 本身的构造函数
 * BeanPostProcessor 的 postProcessBeforeInitialization方法
 * 类中添加了注解 @PostConstruct 的方法
 * InitializingBean 的 afterPropertiesSet 方法
 * init-method
 * BeanPostProcessor 的 postProcessAfterInitialization 方法
 * Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法)
 */
@Configuration
public class PermitAllUrlProperties implements InitializingBean, ApplicationContextAware {
    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");


    private ApplicationContext applicationContext;

    private List<String> urls = new ArrayList<>();

    public String ASTERISK = "*";

    @Override
    public void afterPropertiesSet() {
        /**
         * RequestMappingHandlerMapping的作用是在容器启动后将系统中所有控制器方法的请求条件（RequestMappingInfo）和控制器方法(HandlerMethod)的对应关系注册
         * 到RequestMappingHandlerMapping Bean的内存中，待接口请求系统的时候根据请求条件和内存中存储的系统接口信息比对，再执行对应的控制器方法。
         */
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // HandlerMethod 类用于封装控制器方法信息，包含类信息、方法Method对象、参数、注解等信息，具体的接口请求是可以根据封装的信息调用具体的方法来执行业务逻辑；
        // RequestMappingInfo其实就是将我们熟悉的@RequestMapping注解的信息数据封装到了RequestMappingInfo POJO对象之中，然后和HandlerMethod做映射关系存入缓存之中；
        // 可以参考博客：https://blog.csdn.net/yaomingyang/article/details/107026595
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

        map.keySet().forEach(info -> {
            HandlerMethod handlerMethod = map.get(info);

            // 获取方法上边的注解 替代path variable 为 *
            Anonymous method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Anonymous.class);
            Optional.ofNullable(method).ifPresent(anonymous -> {
                if (info.getPatternsCondition() != null) {
                    info.getPatternsCondition().getPatterns()
                            .forEach(url -> urls.add(RegExUtils.replaceAll(url, PATTERN, ASTERISK)));
                }
            });

            // 获取类上边的注解, 替代path variable 为 *
            Anonymous controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Anonymous.class);
            Optional.ofNullable(controller).ifPresent(anonymous -> {
                if (info.getPatternsCondition() != null) {
                    info.getPatternsCondition().getPatterns()
                            .forEach(url -> urls.add(RegExUtils.replaceAll(url, PATTERN, ASTERISK)));
                }
            });
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}


