package com.xufei.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.github.pagehelper.PageHelper;
import com.xufei.sqlInjector.MySqlInjector;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@MapperScan("com.xufei.demo.mapper")  //Mapper接口就不用标@mapper注解了
public class MybatisPlusConfig {

    //逻辑删除的插件：（3.1开始 高版本不用配置插件了）


    /**
     * 自动填充功能还需要在字段上添加以下注解
     *      @TableField(fill = FieldFill.INSERT) //插入数据时进行填充
     *      private String password;
     * @return
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                Object password = getFieldValByName("password", metaObject);
                if (null == password) {//字段为空，可以进行填充
                    setFieldValByName("password", "123456", metaObject);
                }
            }
            @Override
            public void updateFill(MetaObject metaObject) {
            }
        };
    }

    @Bean
    public PageHelper pageHelper() {//分页插件
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }


    /**
     * 仅支持 updateById(id) 与 update(entity, wrapper) 方法
     * 乐观锁字段支持的数据类型只有:int,Integer,long,Long,Date,Timestamp,LocalDateTime
     * <p>
     * 在实体类的字段上加上@Version注解
     *
     * @return
     * @Version private Integer version;
     */

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {//乐观锁插件
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    /**
     * 如何实现SQL注入器？
     * 1.写一个方法类（就是你要定义的方法要有一个对应类，mp封装的每一个方法都要定义一个对应的类），在类中写SQL模板，需继承AbstractMethod类
     * 2.写一个SQL注入类，将你定义的方法注入MP
     * 3.写一个用于被继承的Mapper接口（比如MyBaseMapper）,该接口需继承BaseMapper接口
     * 4.写一个配置类，把我们写的注入类装进Spring容器
     * 5.写你的业务Mapper层，继承自定义的MyBaseMapper
     * 6.注入自定义的SQL注入器
     * <p>
     * <p>
     * Sql注入器的作用就是，将自定义的方法放到
     *
     * @return
     */
    @Bean
    public MySqlInjector mySqlInjector() {
        return new MySqlInjector();
    }
}
