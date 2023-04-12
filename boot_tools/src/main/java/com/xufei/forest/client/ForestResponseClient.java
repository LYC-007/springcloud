package com.xufei.forest.client;

import com.dtflys.forest.annotation.*;
import com.dtflys.forest.http.ForestResponse;
import com.xufei.forest.entity.Item;
import com.xufei.http.domain.User;
import org.springframework.stereotype.Component;

/**
 * 接收响应封装数据
 */
@Component
public interface ForestResponseClient {
    /**
     * dataType属性指定了该请求响应返回的数据类型，目前可选的数据类型有三种: text, json, xml
     * Forest请求会自动将响应的返回数据反序列化成您要的数据类型。想要接受指定类型的数据需要完成两步操作：
     * 第一步：定义dataType属性
     * 第二步：指定反序列化的目标类型
     * 从1.4.0版本开始，dataType 属性默认为 auto（自动判断数据类型）， 也就是说 dataType 属性可以完全省略不填，Forest会自行判断返回的数据类型是哪种格式。
     */

    /**
     * dataType为text或不填时，请求响应的数据将以文本字符串的形式返回回来
     * @return
     */
    @Request(
            url = "http://localhost:8080/text/data",
            dataType = "text"
    )
    String testQueryStringData();

    /**
     * dataType属性指明了返回的数据类型为JSON
     * 从1.4.0版本开始，dataType 属性默认为 auto（自动判断数据类型），
     * 也就是说 dataType 属性可以完全省略不填，Forest会自行判断返回的数据类型是哪种格式。
     * @param id
     * @return
     */
    @Get(
            url = "http://localhost:8080/user?id=${0}",
            dataType = "json"   // 注意这里从1.4开始可以不写，自动判断
    )
    User testQueryUserData(Integer id);


    /**
     * ForestResponse 可以获取响应内容，也可以得到响应头等信息
     * ForestResponse 可以作为请求方法的返回类型
     * ForestResponse 为带泛型的类，其泛型参数中填的类作为其响应反序列化的目标类型
     * @param item
     * @return
     */
    @Post("http://localhost:8080/user")
    ForestResponse<String> testResponseString(@JSONBody Item item);

    /**
     * 响应封装在对象中
     *  日志打印默认为true
     * @param item
     * @return
     */
    @LogEnabled(value = true)
    @Post("http://localhost:8080/user")
    ForestResponse<Item> testResponseItem(@JSONBody Item item);
    /**
     * 	// 以 ForestResponse类型变量接受响应数据
     * 	ForestResponse<String> response = client.postUser(user);
     *
     * 	// 用 isError方法去判断请求是否失败
     * 	if (response.isError()) {
     * 	    ... ...
     *        }
     *
     * 	// 用isSuccess方法去判断请求是否成功
     * 	if (response.isSuccess()) {
     * 	    ... ...
     *    }
     *
     * 	// 以字符串方式读取请求响应内容
     * 	String text = response.readAsString();
     *
     * 	// getContent方法可以获取请求响应内容文本
     * 	// 和 readAsString方法不同的地方在于，getContent方法不会读取二进制形式数据内容，
     * 	// 而 readAsString方法会将二进制数据转换成字符串读取
     * 	String content = response.getContent();
     *
     * 	// 获取反序列化成对象类型的请求响应内容
     * 	// 因为返回类型为 ForetReponse<String>, 其泛型参数为String
     * 	// 所以这里也用 String类型获取结果
     * 	String result = response.getResult();
     *
     * 	// 以字节数组的形式获取请求响应内容
     * 	byte[] byteArray = response.getByteArray();
     *
     * 	// 以输入流的形式获取请求响应内容
     * 	InputStream in = response.getInputStream();
     *
     *     // 根据响应头名称获取单个请求响应头
     * 	ForestHeader header = response.getHeader("Content-Type");
     * 	// 响应头名称
     * 	String headerName = header.getName();
     * 	// 响应头值
     * 	String headerValue = header.getValue();
     *
     * 	// 根据响应头名称获取请求响应头列表
     * 	List<ForestHeader> heaers = response.getHeaders("Content-Type");
     *
     * 	// 根据响应头名称获取请求响应头值
     * 	String val = response.getHeaderValue("Content-Type");
     *
     * 	// 根据响应头名称获取请求响应头值列表
     * 	List<String> vals = response.getHeaderValues("Content-Type");
     */
}
