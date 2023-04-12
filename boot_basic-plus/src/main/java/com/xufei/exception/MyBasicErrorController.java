package com.xufei.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;
/**
 * MyErrorAttributes和MyErrorViewResolver都是对BasicErrorController类中的某个环节进行修补。
 *
 * 查看Error自动化配置类ErrorMvcAutoConfiguration，可以发现 BasicErrorController本身只是一个默认的配置，相关源码如下:
 *    public class ErrorMvcAutoConfiguration {
 *          ......
 *          @Bean
 *          @ConditionalOnMissingBean(value = ErrorController.class, search = SearchStrategy.CURRENT)
 * 	        public BasicErrorController basicErrorController(ErrorAttributes errorAttributes,ObjectProvider<ErrorViewResolver> errorViewResolvers) {
 * 		        return new BasicErrorController(errorAttributes, this.serverProperties.getError(),errorViewResolvers.orderedStream().collect(Collectors.toList()));
 *          }
 * 	        ...
 *    }
 *
 *  从这段源码中可以看到，若开发者没有提供自己的 ErrorController，则 Spring Boot 提供BasicErrorController作为默认的ErrorController。
 *  因此，如果开发者需要更加灵活地对Error视图和数据进行处理，那么只需要提供自己的ErrorController即可。
 *  提供自己的 ErrorController有两种方式:一种是实现 ErrorController接口，另一种是直接继承BasicErrorController。
 *  由于ErrorController接口只提供一个待实现的方法，而BasicErrorController已经实现了很多功能，因此这里选择第二种方式，即通过继承BasicErrorController来实现自己的ErrorController。
 *
 */
@Controller
public class MyBasicErrorController extends BasicErrorController {
    @Autowired
    public MyBasicErrorController(ErrorAttributes errorAttributes,
                                  ServerProperties serverProperties,
                                  List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, serverProperties.getError(), errorViewResolvers);
    }
    /**
     * BasicErrorController中的实现，重写errorHtml和error方法，对 Error的视图和数据进行充分的自定义。
     */
    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = this.getStatus(request);
        Map<String, Object> model = Collections.unmodifiableMap(this.getErrorAttributes(request, this.getErrorAttributeOptions(request, MediaType.TEXT_HTML)));
        response.setStatus(status.value());
        ModelAndView modelAndView = this.resolveErrorView(request, response, status, model);
        if(modelAndView != null){
            return modelAndView;
        }
        //查看父类的源码可以发现:如果错误没有找到对应的响应页面，那么就响应系统默认提供的“error”页面
        ModelAndView mv = new ModelAndView("error/default_error");//这里我们改写了规则将默认页面变为我们自定义的错误页面
        mv.addObject("url", request.getRequestURL());
        mv.addObject("message",model.get("message"));
        mv.addObject("status",status);
        return mv;

    }
    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        return super.error(request);
    }
}