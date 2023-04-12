package com.xufei.rw.algorithm;


import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.Properties;


public class MyStandardShardingAlgorithm implements StandardShardingAlgorithm<Integer> {
    private Properties properties = new Properties();

    @Override
    public Properties getProps() {
        return properties;
    }

    @Override
    public String doSharding(Collection availableTargetNames, PreciseShardingValue shardingValue) {
        return null;
    }


    @Override
    public Collection<String> doSharding(Collection availableTargetNames, RangeShardingValue shardingValue) {
        return null;
    }

    /**
     * 自定义分片算法的名字
     */
    @Override
    public String getType() {
        return "MONTH";
    }

    @Override
    public void init(Properties properties) {
        this.properties = properties;
    }
}