package com.xufei.springcloud.config;


import com.xufei.springcloud.interceptor.LoginHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.util.UrlPathHelper;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {


    @Override //要想使用 @MatrixVariable这个注解 有效，，配置就得配置以下
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        // 不移除";"后面的内容。矩阵变量功能就可以生效
        urlPathHelper.setRemoveSemicolonContent(false);  //RemoveSemicolonContent 默认移除分号内容；
        configurer.setUrlPathHelper(urlPathHelper);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/views/templates/upload.html");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**")//设置拦截器的过滤规则
                .excludePathPatterns("/adminLogin", "/userLogin");//用于设置不需要拦截的过滤规则
    }
    /**
     * 我们想自定义静态资源映射目录的话，只需重写 addResourceHandlers() 方法即可
     * <p>
     * addResoureHandler：对外暴露的访问路径
     * addResourceLocations：内部文件放置的路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/my/**").addResourceLocations("classpath:/my/");
    }

    /**
     * configureContentNegotiation（内容协商）
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // 自定义策略
        configurer.favorParameter(true)// 是否开启请求参数来决定mediaType，默认false
                .ignoreAcceptHeader(true)// 不检查Accept请求头
                .parameterName("mediaType") //指定一个名字用来接收内容类型（不设置默认是：format） http://localhost:8080/mvcConfig/contentNegotiation?mediaType=json
                .defaultContentType(MediaType.APPLICATION_JSON)// 设置默认的MediaType
                .mediaType("html", MediaType.TEXT_HTML)// 请求以.html结尾的会被当成MediaType.TEXT_HTML
                .mediaType("json", MediaType.APPLICATION_JSON)// 请求以.json结尾的会被当成MediaType.APPLICATION_JSON
                .mediaType("xml", MediaType.APPLICATION_ATOM_XML);// 请求以.xml结尾的会被当成MediaType.APPLICATION_ATOM_XML
    }

}
