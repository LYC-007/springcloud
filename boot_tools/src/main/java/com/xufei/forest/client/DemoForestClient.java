package com.xufei.forest.client;

import com.dtflys.forest.annotation.*;
import com.xufei.http.domain.User;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * 基本使用
 */

@Component
public interface DemoForestClient {
    /**
     * 测试访问百度 :这里使用的是Get请求，你也可以简写为：@Get
     */
    @GetRequest(url = "http://www.baidu.com")
    String accessBaiDu();

    /**
     * 也可以在请求里面指定类型
     */
    @Request(url = "http://www.baidu.com", type = "get")
    String accessBaiDuRequestType();

    @Post(url = "http://www.baidu.com")
    String accessBaiDuPost();

    @Put(url = "http://www.baidu.com")
    String accessBaiDuPut();

    @DeleteRequest(url = "http://www.baidu.com")
    String accessBaiDuDelete();

    /**
     * post请求
     *
     * @param domainName 参数传递domainName（变量绑定是@DataVariable，视情况而定可以不出现在url上）
     * @param port       参数传递 port（变量绑定是@DataVariable）
     * @return
     */
    @PostRequest(url = "http://${domainName}:${port}")
    String testDataVariable(@DataVariable("domainName") String domainName, @DataVariable("port") Integer port);
    /**
     * Query的时候需要注意的点：
     *
     * (1) 需要单个单个定义 参数名=参数值 的时候，@Query注解的value值一定要有，比如 @Query("name") String name
     * (2) 需要绑定对象的时候，@Query注解的value值一定要空着，比如 @Query User user 或 @Query Map map
     */
    /**
     * post请求
     *
     * @param domainName (@Query修饰的参数一定会出现在url中)
     * @param port
     */
    @PostRequest(url = "http://domainName:port")
    String testQuery(@Query("domainName") String domainName, @Query("port") Integer port);

    /**
     * post请求
     * 使用 @Query 注解，可以修饰 Map 类型的参数
     * 很自然的，Map 的 Key 将作为 URL 的参数名， Value 将作为 URL 的参数值
     * 这时候 @Query 注解不定义名称
     *
     * @param map
     */
    @PostRequest(url = "http://domainName:port")
    String testQuery(@Query Map<String, Object> map);

    /**
     * @param user 这是你的入参对象
     * @Query 注解也可以修饰自定义类型的对象参数
     * 依据对象类的 Getter 和 Setter 的规则取出属性
     * 其属性名为 URL 参数名，属性值为 URL 参数值
     * 这时候 @Query 注解不定义名称
     */
    @PostRequest(url = "http://domainName:port")
    String testQuery(@Query User user);




}
