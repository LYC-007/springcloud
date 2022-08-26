package com.xufei.springcloud.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
/**
 * 第一次创建Session时，服务端会在HTTP协议中向客户端 Cookie 中记录一个Session ID，以后每次请求把这个会话ID发送到服务器，这样服务端就知道客户端是谁了。
 *
 * 那么如果客户端的浏览器禁用了 Cookie 怎么办？
 * 一般这种情况下，会使用一种叫做URL重写的技术来进行session会话跟踪，即每次HTTP交互，URL后面都会被附加上一个诸如 sessionId=xxxxx 这样的参数，服务端据此来识别客户端是谁
 *
 * Session在分布式集群环境存在的问题:
 * 1.同一个服务(这个服务被部署在多台服务器，用户在其中一台登录，如果用户再次访问部署相同服务的另一台服务器那么Session还是不能共享)，Session不能同步的问题
 * 2.不同服务(不同子域)Session不能共享问题(每个Session都有一个作用域-->Domain,也就是域名，不同域名的Session也不能共享)
 *
 * 解决方案:
 * 1.session复制（服务器之间相互复制）Tomcat原生支持只需要改tomcat配置文件
 * 缺点:占服务器的带宽,内存,浪费资源(大型分布式集群情况下，由于所有的服务器都要全量保存数据，浪费内存，资源)
 * 任意一台web-server保存的数据都是所有web- server的session总和，受到内存限制无法水平扩展
 *
 * 2.客户端存储session，服务端不用存session
 * 每次http请求，携带用户在cookie中的完整信息，浪费网络带宽
 * session数据放在cookie中，cookie有长度限制4K，不能保存大量信息
 * session数据放在cookie中，存在泄漏、篡改、窃取等安全隐患
 *
 * 3.hash一致性(浏览器从哪个IP访问了服务端,就一直用那个服务端处理该浏览器的请求)，nginx提供了根据IP绑定服务端的负载均衡算法,只改nginx配置文件即可，可以支持服务器的水平扩展
 * session还是存在web-server中的，所以web-server重启可能导致部分session丢失，影响业务，如部分用户需要重新登录
 * 如果web-server水平扩展，rehash后session重新分布，也会有一部分用户路由不到正确的session
 *
 * 4.使用中间件Redis统一存储session
 * 缺点:增加了一次网络调用，并且需要修改应用代码；如将所有的getSession方法替换为从Redis查数据的方式。redis获取数据比内存慢很多
 *
 * 5.使用基于token的认证方式
 */
@EnableRedisHttpSession  //SpringSession的使用手册(注意版本):https://docs.spring.io/spring-session/docs/2.5.6/reference/html5/guides/boot-redis.html
@SpringBootApplication
public class SessionApplication8004 {
    public static void main(String[] args) {
        SpringApplication.run(SessionApplication8004.class);
    }
}
/**
 *基本原理：@EnableRedisHttpSession注解导入了RedisHttpSessionConfiguration配置类，该类注入了一个在Redis进行Session增删改查的类 RedisIndexedSessionRepository
 * 该类的父类 SpringHttpSessionConfiguration 导入一个过滤器SessionRepositoryFilter该过滤器继承Servlet的Filter,用于封装原来的HttpServletRequest，HttpServletResponse
 * 所以我们获取Session时调用的时封装后的请求重写的getSession()方法，
 *
 *
 *
 */