package com.xufei.springcloud.controller;

import com.xufei.springcloud.domain.Person;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
public class RequestController {
    /**
     * @RequestPart这个注解用在multipart/form-data表单提交请求的方法上。支持的请求方法的方式MultipartFile，属于Spring的MultipartResolver类。
     * @RequestParam支持’application/json’，也同样支持multipart/form-data请求
     * 区别
     * 1.当请求方法的请求参数类型不是String 或 MultipartFile / Part时，而是复杂的请求域时，
     * RequestParam 依赖Converter or PropertyEditor进行数据解析，RequestPart参考 ‘Content-Type’ header，依赖HttpMessageConverters 进行数据解析
     *
     * 2.当请求为multipart/form-data时，@RequestParam只能接收String类型的name-value值，@RequestPart可以接收复杂的请求域（像json、xml）;
     * RequestParam 依赖Converter or PropertyEditor进行数据解析， @RequestPart参考'Content-Type' header，依赖HttpMessageConverters进行数据解析
     */
    @PostMapping("/13")//@RequestPart可以将jsonData的json数据转换为Person对象
    public String jsonDataAndUploadFile(@RequestPart("uploadFile") MultipartFile uploadFile,
                                        @RequestPart("jsonData") Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(uploadFile.getOriginalFilename()).append(";;;");
        return person.toString() + ":::" + sb.toString();
    }
    @PostMapping("/12")//@RequestParam对于jsonData的json数据只能用String字符串来接收
    public String jsonDataAndUploadFile(@RequestPart("uploadFile") MultipartFile uploadFile,
                                        @RequestParam("jsonData") String jsonData) {
        return jsonData + ":::" + uploadFile.getOriginalFilename() + ";;;";
    }


    //@PathVariable  --路径变量
    @GetMapping("/1/{id}/owner/{username}")
    public Map<String, String> getCar1(@PathVariable("id") Integer id,          //获取某一个的值
                                      @PathVariable("username") String name,
                                      @PathVariable Map<String, String> pv) {  //获取多个并把它放到Map集合里面;
        return pv;
    }

    @GetMapping("/2/{id}/owner/{username}")
    public Map<String,Object> getCar2(@PathVariable("id") Integer id,
                                     @PathVariable("username") String name,
                                     @PathVariable Map<String,String> pv,
                                     @RequestHeader("user-Agent") String userAgent,
                                     @RequestHeader Map<String,String> header
    ){
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        map.put("name",name);
        map.put("pv",pv);
        map.put("user-Agent",userAgent);
        map.put("headers",header);
        return map;
    }
    //@RequestParam (指问号后的参数，url?a=1&b=2)  /car/{3}/owner/{username}?age=18&inters=basketball&inters=game
    @GetMapping("/3/{id}/owner/{username}")
    public Map<String,Object> getCar3(@PathVariable("id") Integer id,
                                     @PathVariable("username") String name,
                                     @PathVariable Map<String,String> pv,
                                     @RequestHeader("user-Agent") String userAgent,
                                     @RequestHeader Map<String,String> header,
                                     @RequestParam("age") Integer age,
                                     @RequestParam("inters") List<String> inters,
                                     @RequestParam Map<String,String> params
    ){
        Map<String,Object> map=new HashMap<>();
        //map.put("id",id);
        //map.put("name",name);
        //map.put("pv",pv);
        //map.put("user-Agent",userAgent);
        //map.put("headers",header);
        map.put("age",age);
        map.put("inters",inters);
        map.put("params",params);
        return map;
    }


    //例1  /cars/sell;low=34;brand=byd,audi,yd       --注意一个key对应多个值的写法
    @GetMapping("/4/{path}")
    public Map<String,Object> carsSell(@MatrixVariable("low") Integer low,
                        @MatrixVariable("brand") List<String> brand,
                        @PathVariable("path") String path){
        Map<String,Object> map = new HashMap<>();
        map.put("low",low);
        map.put("brand",brand);
        map.put("path",path);
        return map;
    }
    //   例2：/boss/1;age=20/2;age=10       --注意这两个age是怎么区分的  1的age 和 2的age
    @GetMapping("/5/{bossId}/{empId}")
    public Map<String,Object> boss(
            @MatrixVariable(value = "age",pathVar = "bossId") Integer bossAge,
            @MatrixVariable(value = "age",pathVar = "empId") Integer empAge){
        Map<String,Object> map = new HashMap<>();
        map.put("bossAge",bossAge);
        map.put("empAge",empAge);
        return map;
    }


    @GetMapping("/6/{id}/owner/{name}")//         /car/{3}/owner/{name}?age=18&inters=basketball&inters=game
    public Map<String,Object> getCar4(@PathVariable("id") Integer id,
                                     @PathVariable("name") String name,
                                     @PathVariable Map<String,String> pv,
                                     @RequestHeader("user-Agent") String userAgent,
                                     @RequestHeader Map<String,String> header,
                                     @RequestParam("age") Integer age,
                                     @RequestParam("inters") List<String> inters,
                                     @RequestParam Map<String,String> params,
                                     @CookieValue("_ga")  String _ga,
                                     @CookieValue("_ga") Cookie cookie
    ){
        Map<String,Object> map=new HashMap<>();
        //map.put("id",id);
        //map.put("name",name);
        //map.put("pv",pv);
        //map.put("user-Agent",userAgent);
        //map.put("headers",header);
        map.put("age",age);
        map.put("inters",inters);
        map.put("params",params);
        map.put("_ga",_ga);
        System.out.println(cookie.getName()+"===>"+cookie.getValue());
        return map;
    }
    @PostMapping("/save")
    public Map<String,Object> postMethod(@RequestBody String content){      //获取所有请求体的内容
        Map<String,Object> map = new HashMap<>();
        map.put("content",content);
        return map;
        /**
         * 用于读取 Request 请求（可能是 POST,PUT,DELETE,GET 请求）的 body 部分并且Content-Type 为application/json 格式的数据，接收到数据之后会自动将数据绑定到 Java 对象上去。
         * 系统会使用HttpMessageConverter 或者自定义的 HttpMessageConverter 将请求的 body 中的 json 字符串转换为 java 对象。
         * 一个请求方法只可以有一个 @RequestBody如果你的方法必须要用两个 @RequestBody 来接受数据的话，大概率是你的数据库设计或者系统设计出问题了！
         */
    }
    /**
     * 1、@RequestBody 作用在方法上，表示该方法的返回的结果直接写入 HTTP 响应正文（ResponseBody）中，一般在异步获取数据时使用；
     *              通常是在使用 @RequestMapping 后，返回值通常解析为跳转路径，加上 @RequestBody后返回结果不会被解析为跳转路径，而是直接写入HTTP 响应正文中。
     *              作用：
     *                  该注解用于将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后，写入到Response对象的body数据区。
     *              使用时机：
     *                  返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
     * 2、@RequestBody是作用在形参列表上，用于将前台发送过来固定格式的数据【xml 格式或者 json等】封装为对应的 JavaBean 对象，封装时使用到的一个对象是系统默认配置的 HttpMessageConverter进行解析，然后封装到形参上。
     */

}
