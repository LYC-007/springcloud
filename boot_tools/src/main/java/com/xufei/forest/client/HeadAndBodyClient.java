package com.xufei.forest.client;

import com.dtflys.forest.annotation.*;
import com.xufei.forest.entity.HeaderInfo;
import com.xufei.forest.entity.Item;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *  向请求体和请求头设置参数
 *      1.关于请求头的设置（以下例子中依次递增的是优化方案）
 *      2.请求中的附加参数（head和body中）
 */
@Component
public interface HeadAndBodyClient {
    /*
     *  @Header使用注意事项
     * (1) 需要单个单个定义请求头的时候，@Header注解的value值一定要有，比如 @Header("Content-Type") String contentType
     *
     * (2) 需要绑定对象的时候，@Header注解的value值一定要空着，比如 @Header MyHeaders headers 或 @Header Map headerMap
     */

    /**
     * 默认get请求，在head中也可以使用@Query设置值同理@DataVariable
     *
     * @param encoding
     * @return
     */
    @Request(
            url = "http://www.baidu.com",
            headers = {
                    "Accept-Charset: ${encoding}",
                    "Content-Type: text/plain"
            }
    )
    String testheaders(@Query("encoding") String encoding);

    /**
     * post请求
     * 使用 @Header 注解将参数绑定到请求头上
     *
     * @param accept
     * @param accessToken
     * @Header 注解的 value 指为请求头的名称，参数值为请求头的值
     * @Header("Accept") String accept将字符串类型参数绑定到请求头 Accept 上
     * @Header("accessToken") String accessToken将字符串类型参数绑定到请求头 accessToken 上
     */
    @Post("http://www.baidu.com")
    void testHead(@Header("Accept") String accept, @Header("accessToken") String accessToken);

    /**
     * 使用 @Header 注解可以修饰 Map 类型的参数
     * Map 的 Key 指为请求头的名称，Value 为请求头的值
     * 通过此方式，可以将 Map 中所有的键值对批量地绑定到请求头中
     *
     * @param headerMap
     */
    @Post("http://www.baidu.com")
    void testHead(@Header Map<String, Object> headerMap);


    /**
     * 使用 @Header 注解可以修饰自定义类型的对象参数
     * 依据对象类的 Getter 和 Setter 的规则取出属性
     * 其属性名为 URL 请求头的名称，属性值为请求头的值
     * 以此方式，将一个对象中的所有属性批量地绑定到请求头中
     */
    @Post("http://localhost:8080/hello/user?username=foo")
    void testHead(@Header HeaderInfo headersInfo);

    /**
     * delete请求
     *
     * @param url
     * @param param 这是数据参数，附加在请求上，get在head，post在body
     *              此方法@DataParam已过时
     * @return
     */
    @DeleteRequest(url = "${url}")
    String testVariableAndParam(@DataVariable("url") String url, @DataParam("param") String param);

    /**
     * @param username
     * @param password
     * @return username=xxx&password=xxx （这是默认格式，如果要转为json格式，在请求头中设置即可Content-Type: application/json）
     * @Body注解修饰的参数一定会绑定到请求体中 默认body格式为 application/x-www-form-urlencoded，即以表单形式序列化数据
     */
    @Post(
            url = "http://www.baidu.com",
            headers = {"Accept:text/plain"}
    )
    String testBody(@Body("username") String username, @Body("password") String password);

    /**
     * 也可以使用@Body修饰，将整个类以表单方式传输
     *
     * @param item
     * @return
     */
    @Post(
            url = "http://www.baidu.com",
            headers = {"Accept:text/plain"}
    )
    String testBody(@Body Item item);

    /**
     * 被 @JSONBody注解修饰的参数会根据其类型被自定解析为JSON字符串, (也可以拆分开，修饰单个参数，如 @JSONBody(string) String string)
     * 修饰map等亦可以，当修饰 list的时候，结果类型可为["A", "B", "C"]
     * 使用 @JSONBody注解时可以省略 contentType = "application/json"属性设置
     *
     * @param item
     * @return {"username": "xx", "password": "xxx"}
     */
    @Post("http://www.baidu.com")
    String testJSONBody(@JSONBody Item item);

}
