package com.xufei.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * 自定义Error数据
 *
 *
 *
 * Spring Boot 返回的Error信息一共有5条，分别是timestamp、status、error、message、path。
 * 在BasicErrorController的 errorHtml 方法和 error方法中，都是通过getErrorAttributes方法获取 Error信息的。
 * 该方法最终会调用到DefaultErrorAttributes类的 getErrorAttributes方法，而DefaultErrorAttributes类是在 ErrorMvcAutoConfiguration中默认提供的。
 * ErrorMvcAutoConfiguration类的errorAttributes方法源码如下:
 *     @Bean
 *     @ConditionalOnMissingBean(value = {ErrorAttributes.class},search = SearchStrategy.CURRENT)
 *     public DefaultErrorAttributes errorAttributes() {
 *         return new DefaultErrorAttributes();
 *     }
 *从这段源码中可以看出，当系统没有提供 ErrorAttributes时才会采用DefaultErrorAttributes。
 *
 * 因此自定义错误提示时，只需要自己提供一个 ErrorAttributes即可，而 DefaultErrorAttributes是ErrorAttributes的子类，因此你写的类只需要继承 DefaultErrorAttributes即可。
 */
@Component
public class MyErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        //通过super.getErrorAttributes获取Spring Boot 默认提供的错误信息，然后在此基础上添加Error信息或者移除Error信息。
        Map<String, Object> errorAttributes=super.getErrorAttributes(webRequest,options);
        //Spring Boot 返回的Error信息一共有5条，分别是timestamp、status、error、message、path。
        //这时你就可以添加或者移除信息
        errorAttributes.put("msg","MyErrorAttributes");
        //errorAttributes.remove("error");
        return errorAttributes;
    }
}
