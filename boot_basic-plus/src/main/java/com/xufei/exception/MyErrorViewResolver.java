package com.xufei.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 自定义Error视图
 *
 *
 * 在处理异常时，开发者可以根据实际情况返回不同的页面，这种异常处理方式一般用来处理应用级别的异常。
 * 但是，有一些容器级别的错误就处理不了，例如Filter中抛出异常，使用@ControllerAdvice定义的全局异常处理机制就无法处理。因此，Spring Boot中对于异常的处理还有另外的方式。
 * Spring Boot中默认的处理行为，如果DispatcherServlet执行发生异常，内部如果没有处理掉（比如没有被自定义的全局异常处理器处理掉），而交给tomcat处理时，最终会转发到/error请求。
 * Spring Boot在启动tomcat容器时，注册了一个状态码为0，路径为/error的错误页面，而状态码为0时可以匹配所有的状态码，因此最终的错误处理又回到了Spring Boot，更准确的说回到了DispatcherServlet，而处理/error的控制器为BasicErrorController。
 * 该控制器根据请求的Accept请求头响应两种格式，即html和ajax数据。
 * Spring Boot在返回错误信息时不一定返回HTML页面,而是根据实际情况返回HTML页面或者一段JSON（若开发者发起 Ajax 请求，则错误信息是一段JSON)。对于开发者而言，这一段HTML或者JSON都能够自由定制。
 *
 *
 * Spring Boot 中的错误默认是由BasicErrorController类来处理的，该类中的核心方法主要有两个:
 *      errorHtml(HttpServletRequest request, HttpServletResponse response)  //errorHtml方法用来返回错误HTML页面
 *      error(HttpServletRequest request)  //error用来返回错误JSON
 *      具体返回的是HTML还是JSON，则要看请求头的Accept参数
 * 在 errorHtml方法中，通过调用resolveErrorView方法来获取一个错误视图的ModelAndView。而resolveErrorView方法的调用最终会来到DefaultErrorViewResolver类中。
 * DefaultErrorViewResolver类是Spring Boot中默认的错误信息视图解析器,从该视图解析器源码可以看出，Spring Boot默认是在error目录下查找4xx、5xx的文件作为错误视图，当找不到时会回到 errorHtml方法中，然后使用error 作为默认的错误页面视图名;
 *
 *  Spring Boot在返回错误信息时不一定返回HTML页面,而是根据实际情况返回HTML页面或者一段JSON（若开发者发起 Ajax 请求，则错误信息是一段JSON)。对于开发者而言，这一段HTML或者JSON都能够自由定制。
 *
 *
 * Error视图是展示给用户的页面，在 BasicErrorController 的 errorHtml方法中调用resolveErrorView方法获取一个 ModelAndView实例。
 * resolveErrorView方法是由ErrorViewResolver提供的，通过 ErrorMvcAutoConfiguration类的源码可以看到 Spring Boot默认采用的ErrorViewResolver是DefaultErrorViewResolver。
 * ErrorMvcAutoConfiguration部分源码如下:
 *      @Bean
 *      @ConditionalOnBean(DispatcherServlet.class)
 *      @ConditionalOnMissingBean(ErrorViewResolver.class)
 *      DefaultErrorViewResolver conventionErrorViewResolver() {
 * 	        return new DefaultErrorViewResolver(this.applicationContext, this.resources);
 *      }
 * 从这一段源码可以看到，如果用户没有定义 ErrorViewResolver，那么默认使用的ErrorViewResolver是 DefaultErrorViewResolver，正是在DefaultErrorViewResolver中配置了默认去error目录下寻找4xx.html、5xx.html
 * 因此，开发者想要自定义 Error 视图，只需要提供自己的ErrorViewResolver即可
 *
 *
 *
 *
 *
 */
@Component
public class MyErrorViewResolver implements ErrorViewResolver {
    private static MyErrorViewResolver errorViewResolver;

    @Autowired
    private ErrorAttributes errorAttributes;

    public MyErrorViewResolver(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    public MyErrorViewResolver() {
        if (errorViewResolver == null) {
            errorViewResolver = new MyErrorViewResolver(errorAttributes);
        }
    }

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        ModelAndView modelAndView=new ModelAndView();
        //最后一个 Map参数就是Spring Boot提供的默认的5条Error信息(可以按照前面自定义Error数据的步骤对这5条消息进行修改)。
        //所以:理论上，开发者也可以通过实现 ErrorViewResolver接口来实现 Error 数据的自定义，但是如果只是单纯地想自定义Error 数据，还是建议继承DefaultErrorAttributes。
        if (HttpStatus.BAD_REQUEST == status) {
            modelAndView.setViewName("error/error_400");
            modelAndView.addObject("errorAttribute",model.get("msg"));
            modelAndView.addObject("msg","MyErrorViewResolver");
        } else if(HttpStatus.INTERNAL_SERVER_ERROR == status) {
            modelAndView.setViewName("error/error_500");
            modelAndView.addObject("errorAttribute",model.get("msg"));
            modelAndView.addObject("msg","MyErrorViewResolver");
        }else {
            return null;
        }
        return  modelAndView;
    }
}