package com.lkadoc;

import com.lk.api.annotation.LKADocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 登录地址:http://127.0.0.1:8080/lkadoc.html
 * https://blog.csdn.net/liukaitydn/article/details/107715778?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-12-107715778-blog-128382915.pc_relevant_multi_platform_whitelistv3&spm=1001.2101.3001.4242.7&utm_relevant_index=15
 */
@LKADocument(basePackages = "com.lkadoc", password = "1234", classNum = "1000", sconAll = true, projectName = "演示项目", description = "用于Lkadoc教学项目", version = "1.0")
/**
 * @LKADocument(basePackages="com.lkad.api",projectName = "演示项目",description = "用于Lkadoc教学项目",version = "1.0",sconAll = true,serverNames = "物业项目^192.168.0.52:8081,租房项目^192.168.0.77:8001")
 *   #@LKADocument是加在SpringBoot启动类上的注解，必须加上，否则Lkadoc接口文档不可使用，用来描述项目信息，该注解下的属性可以代替application配置文件里面的lkad相关配置，非常方便，常用属性：
 *   basePackages:扫描接口的包路径，多个用","号隔开，指定父包路径可以扫描所有父包下的子包路径【必须】#例如:basePackages="com.lkad.api,com.lkad.admin"
 *   projectName:项目名称【可选】#例如:projectName="Lkadoc测试项目"
 *   description:项目描述【可选】#例如:description="该项目用来教学Lkadoc的使用"
 *   serverNames:要聚合的项目地址，"^"前面是项目名称（可省略），后面是项目的地址（也可以用域名），多个用","号隔开，用来聚合其它项目的接口信息，可以在UI界面切换【可选】#例如:serverNames="物业项目^192.168.0.52:8081,租房系统^192.168.0.77:8001"
 *   version:项目的版本号,配合@LKAMethod注解的version属性可以快速定位新接口【可选】#例如:version="1.0"
 *   enabled:接口文档启动开关,true是开启，false是禁用,默认为开启【可选】#例如:enabled=true
 *   sconAll:是否开启对未加注解描述的参数进行自动识别，默认为false【可选】#例如:sconAll=false
 *   password:设置查看接口文档所需的密码，默认不需要密码【可选】#例如:password="123456"
 *   classNum:设置扫描类的最大数量，默认是5000个类，防止内存溢出【可选】#例如:classNum="10000"
 *
 *
 *  注意：@LKADocument注解相关属性也可以在application.properties文件中通过lkad.basePackages="x.x.x"的方式配置，但@LKADocument这个注解不能省略。
 *  如果application.properties文件中配置了lkad属性，那么@LKADocument注解中的属性会全部失效，意思就是说两种方式只能二选一
 *
 */
@SpringBootApplication
public class LkadocDemoApplication {
    private static final Logger logger = LoggerFactory.getLogger(LkadocDemoApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext run = SpringApplication.run(LkadocDemoApplication.class, args);
        ConfigurableEnvironment environment = run.getEnvironment();
        logger.info("\n[----------------------------------------------------------]\n\t" +
                        "Lkadoc登录文档界面:\thttp://{}:{}/lkadoc.html" +
                        "\n[----------------------------------------------------------",
                InetAddress.getLocalHost().getHostAddress(),
                environment.getProperty("server.port"));
    }
}
