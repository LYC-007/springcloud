package com.xufei.forest.client;

import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.DataVariable;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Query;
import org.springframework.stereotype.Component;

/**
 * @BaseRequest 为配置接口层级请求信息的注解，
 * 其属性会成为该接口下所有请求的默认属性，
 * 但可以被方法上定义的属性所覆盖
 *
 * @BaseRequest注解中的所有字符串属性都可以通过模板表达式引用全局变量或方法中的参数。
 * 若全局变量中已定义 baseUrl
 * 便会将全局变量中的值绑定到 @BaseRequest 的属性中
 */
@BaseRequest(
        baseURL = "${baseUrl}",     // 默认域名
        headers = {
                "Accept:text/plain"                // 默认请求头
        },
        sslProtocol = "sslType"                    // 默认单向SSL协议(TLS)
)
@Component
public interface BaseRequestClient {
    /**
     * 在 @BaseRequest 中的属性亦可以引用方法中的绑定变量名的参数
     * @param baseUrl 在BaseRequest注解中被引用
     * @return
     */
    @Get("/hello/user")
    String testOne(@DataVariable("baseUrl") String baseUrl);

    /**
     * 方法的URL不必再写域名部分
     * @param sslType 在BaseRequest注解中被引用
     * @return
     */
    @Get("/hello/user")
    String testTwo(@Query("sslType") String sslType);

    /**
     * 若方法的URL是完整包含http://开头的，那么会以方法的URL中域名为准，不会被接口层级中的baseUrl属性覆盖
     * @param username
     * @return
     */
    @Get("http://www.xxx.com/hello/user")
    String testThree(@Query("username") String username);

    /**
     * 覆盖请求头中信息
     * @param username
     * @return
     */
    @Get(
            url = "/hello/user",
            headers = {
                    "Accept:application/json"      // 覆盖接口层级配置的请求头信息
            }
    )
    String testFour(@Query("username") String username);
}
