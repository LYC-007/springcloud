package com.room1.config;

import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@MapperScan("com.room1.mapper")  //Mapper接口就不用标@mapper注解了
public class MybatisPlusConfig {
    @Bean
    public PageHelper pageHelper() {//分页插件
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        /**
         * 是否启动分页合理化。如果启用，当 pagenum < 1 时，会自动查询第一页的数据，当 pagenum > pges 时，自动查询最后一页数据；
         * 不启用的，以上两种情况都会返回空数据，如果启用则 pageHelper可以自动拦截请求参数中的 pageNum，pageSize参数，否则需要使用 PageHelper.startPage(pageNum,pageSize) 方法调用。
         */
        p.setProperty("helperDialect","mysql");
        p.setProperty("params","count=countSql");
        pageHelper.setProperties(p);
        return pageHelper;
    }


}
