package com.xufei.springcloud.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**Token的过期时间我们在生成token的时候写死了,如果你想要自动刷新token的过期时间，请结合SpringSession把token放到Redis里面,每次获取冲Redis里面获取
 *
 * 这里有两种方式实现,实际中只需要引入其中之一就行
 *     方式一:引入jwt  官方地址：https://github.com/auth0/java-jwt
 *     方式二:JJWT版JWT(Java版)的github地址:https://github.com/jwtk/jjwt
 */
@SpringBootApplication
public class LoginApplication8006 {//前端登录后从后端token字符串，每次访问后端时前端都的带上这个字符串
    public static void main(String[] args) {
        SpringApplication.run(LoginApplication8006.class);
    }
}
/**JWT官网： https://jwt.io/
 * 1.什么是JWT
 * JSON Web Token（JWT）是一个非常轻巧的规范。这个规范允许我们使用JWT在用户和服务器之间传递安全可靠的信息。
 *
 * 2.JWT组成
 * 一个JWT实际上就是一个字符串，它由三部分组成，头部、载荷与签证(每部份用“.”进行分割)
 * 头部、载荷的信息进行BASE64编码组成JWT的第一和第二部分;(所以我们不用进行验签直接获取JWT的头部和载荷信息然后通过BASE64解码就可以获取里面的内容---所以在载荷里面不要放敏感的信息)
 * 验签:这个部分需要base64加密后的header和base64加密后的payload(载荷)使用“.”连接组成的字符串，然后通过header中声明的加密方式进行加盐secret组合加密，然后就构成了jwt的第三部分。
 * (header的只是一个信息告诉别人用了什么算法，实际上这个可以不写的,java里工具他会自动去生成header里的信息)
 */