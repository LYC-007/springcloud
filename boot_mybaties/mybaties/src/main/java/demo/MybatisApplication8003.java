package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 MybatisAutoConfiguration默认给我们配置了什么：
 SqlSessionFactory: 自动配置好了
 SqlSession：自动配置了 SqlSessionTemplate 组合了SqlSession
 @Import(AutoConfiguredMapperScannerRegistrar.class）；只要我们写了@Mapper就会被自动扫描,不需要我们配置mapper接口的位置
 导入了Spring-boot-starter-jdbc:2.4.0，一般我们用Druid数据源
 */
// 官网地址:https://mybatis.net.cn/getting-started.html

/**步骤:
 * 0.导入相关starter
 * 1.编写mapper接口并标注@Mapper注解
 * 2.编写sql映射文件(*mapper.xml) 并绑定到mapper接口
 * 3.再application.yml中指定sql映射文件的位置，并指定全局配置文件的信息(建议不写全局配置文件，直接再application.yml中配置就可以)
 */
@SpringBootApplication
public class MybatisApplication8003 {
    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication8003.class);

    }
}
