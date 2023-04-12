package com.xufei.forest.client;

import com.dtflys.forest.annotation.DataVariable;
import com.dtflys.forest.annotation.Query;
import com.dtflys.forest.annotation.Request;
import com.dtflys.forest.callback.OnError;
import com.dtflys.forest.callback.OnSuccess;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * 回调函数测试
 */
@Component
public interface CallbackClient {
    /**
     * 在异步请求中只能通过OnSuccess<T>回调函数接或Future<T>返回值接受数据。
     * 而在同步请求中，OnSuccess<T>回调函数和任何类型的返回值都能接受到请求响应的数据。
     * OnError回调函数可以用于异常处理，一般在同步请求中使用try-catch也能达到同样的效果。
     */

    /**
     * 请求使用成功和失败回调
     *
     * 如这两个回调函数的类名所示的含义一样，OnSuccess<T>在请求成功调用响应时会被调用，而OnError在失败或出现错误的时候被调用。
     * 其中OnSuccess<T>的泛型参数T定义为请求响应返回结果的数据类型。
     * @param username
     * @param onSuccess
     * @param onError
     * @return
     */
    @Request(
            url = "http://localhost:8080/hello/user",
            headers = {"Accept:text/plain"},
            data = "username=${username}"
    )
    String sendAndCallback(@DataVariable("username") String username, OnSuccess<String> onSuccess, OnError onError);
    /**
     * 在Forest使用异步请求，可以通过设置@Request注解的async属性为true实现，不设置或设置为false即为同步请求。
     */

    /**
     * 异步请求，成功回调(在异步请求中只能通过OnSuccess<T>回调函数接或Future<T>返回值接受数据，所以返回值为void)
     * @param username
     * @param onSuccess
     */
    @Request(
            url = "http://localhost:8080/hello/queryUserByName/username",
            async = true,
            headers = {"Accept:text/plain"}
    )
    void testAsyncGet(@Query("username") String username, OnSuccess<String> onSuccess);

    /**
     * 异步请求，Future封装接收
     * @param username
     * @return
     */
    @Request(
            url = "http://localhost:8080/hello/queryUserByName/username",
            async = true,
            headers = {"Accept:text/plain"}
    )
    Future<String> testAsyncGet(@Query("username") String username);

}
