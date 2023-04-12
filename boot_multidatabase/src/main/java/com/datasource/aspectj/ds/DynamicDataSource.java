package com.datasource.aspectj.ds;

import com.datasource.aspectj.enums.DataSourceName;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * AbstractRoutingDataSource的getConnection() 方法根据查找 lookup key 键对不同目标数据源的调用，通常是通过(但不一定)某些线程绑定的事物上下文来实现。
 * AbstractRoutingDataSource的多数据源动态切换的核心逻辑是：在程序运行时，把数据源数据源通过 AbstractRoutingDataSource 动态织入到程序中，灵活的进行数据源切换。
 * 基于AbstractRoutingDataSource的多数据源动态切换，可以实现读写分离，这么做缺点也很明显，无法动态的增加数据源。
 *
 * 实现逻辑：
 *
 * 定义DynamicDataSource类继承抽象类AbstractRoutingDataSource，并实现了determineCurrentLookupKey()方法。
 * 把配置的多个数据源会放在AbstractRoutingDataSource的 targetDataSources和defaultTargetDataSource中，
 *      然后通过afterPropertiesSet()方法将数据源分别进行复制到resolvedDataSources和resolvedDefaultDataSource中。
 * 调用AbstractRoutingDataSource的getConnection()的方法的时候，先调用determineTargetDataSource()方法返回DataSource在进行getConnection()。
 *
 */
@Component
@DependsOn({"slaveDataSource","masterDataSource"})
public class DynamicDataSource extends AbstractRoutingDataSource {
    public DynamicDataSource(@Qualifier("masterDataSource")DataSource masterDataSource,
                             @Qualifier("slaveDataSource")DataSource slaveDataSource) {
        HashMap<Object,Object> hashMap=new HashMap<>();
        hashMap.put(DataSourceName.MASTER,masterDataSource);
        hashMap.put(DataSourceName.SLAVE,slaveDataSource);
        super.setDefaultTargetDataSource(masterDataSource);//设置默认的数据源
        super.setTargetDataSources(hashMap);//设置总的数据源
        super.afterPropertiesSet();
    }
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
