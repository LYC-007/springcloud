package com.xufei.jpa;

import com.xufei.jpa.service.AuditorAwareImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
/**
 * 首先了解JPA是什么？
 * JPA(Java Persistence API)是Sun官方提出的Java持久化规范。它为Java开发人员提供了一种对象/关联映射工具来管理Java应用中的关系数据。
 * 他的出现主要是为了简化现有的持久化开发工作和整合ORM技术，结束现在Hibernate，TopLink，JDO等ORM框架各自为营的局面。
 * 值得注意的是，JPA是在充分吸收了现有Hibernate，TopLink，JDO等ORM框架的基础上发展而来的，具有易于使用，伸缩性强等优点。
 * 从目前的开发社区的反应上看，JPA受到了极大的支持和赞扬，其中就包括了Spring与EJB3.0的开发团队。
 * 注意:JPA是一套规范，不是一套产品，那么像Hibernate,TopLink,JDO他们是一套产品，如果说这些产品实现了这个JPA规范，那么我们就可以叫他们为JPA的实现产品。
 *
 * Spring Data JPA 是 Spring 基于 ORM 框架、JPA 规范的基础上封装的一套JPA应用框架，可使开发者用极简的代码即可实现对数据的访问和操作。
 * 它提供了包括增删改查等在内的常用功能，且易于扩展！学习并使用 Spring Data JPA 可以极大提高开发效率！
 * spring data jpa让我们解脱了DAO层的操作，基本上所有CRUD都可以依赖于它来实现
 *
 */



/**
 * 对于传统关系型数据库，Spring Boot使用JPA（Java Persistence API）资源库来实现对数据库的操作;
 * 简单来说，JPA就是为POJO（Plain Ordinary Java Object）提供持久化的标准规范;
 * 即将Java普通对象通过对象关系映射（Object Relational Mapping，ORM）持久化到数据库中。
 * JpaRepository<User,Integer>泛型解释:
 *      1.将实体类型(User)和实体类的id类型作为类型参数(Integer)
 *      2.接口必须继承类型参数化为实体类型和实体类中的Id类型的Repository
 */
@SpringBootApplication
@EnableJpaAuditing //利用jpa可以给MySQL列属性自动赋值,例如一些创建时间，修改时间
public class SpringDatajpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDatajpaApplication.class, args);
    }
    /**
     * 测试中如果无法自动识别,可能是包路径的问题，采用手动声明bean的方式
     */
    @Bean
    public AuditorAwareImpl setUserAuditorAware(){
        return new AuditorAwareImpl();
    }
}