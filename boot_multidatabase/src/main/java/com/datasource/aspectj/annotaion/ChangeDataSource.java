package com.datasource.aspectj.annotaion;

import com.datasource.aspectj.enums.DataSourceName;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ChangeDataSource {
    DataSourceName value() default DataSourceName.MASTER;
}
