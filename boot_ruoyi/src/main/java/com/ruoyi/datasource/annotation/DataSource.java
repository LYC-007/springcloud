package com.ruoyi.datasource.annotation;

import com.ruoyi.datasource.enums.DataSourceName;


import java.lang.annotation.*;

/**
 * 自定义多数据源切换注解
 * @DataSource 该注解会指明使用那个数据源
 *
 * 优先级：先方法，后类，如果方法覆盖了类上的数据源类型，以方法的为准，否则以类上的为准
 *
 * @author ruoyi
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {
    /**
     * 切换数据源名称
     */
    public DataSourceName value() default DataSourceName.MASTER;
}
