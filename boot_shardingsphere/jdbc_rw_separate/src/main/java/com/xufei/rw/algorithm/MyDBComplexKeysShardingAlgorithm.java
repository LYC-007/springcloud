package com.xufei.rw.algorithm;

import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

import java.util.*;
public class MyDBComplexKeysShardingAlgorithm implements ComplexKeysShardingAlgorithm<Integer> {

    private Properties properties;

    @Override
    public Properties getProps() {

        return this.properties;
    }

    @Override
    public void init(Properties properties) {

        this.properties = properties;
    }
    /**
     * 自定义分片算法的名字
     */
    @Override
    public String getType() {
        return "MONTH";
    }

    /**
     * @param databaseNames  表的名字集合
     * @param complexKeysShardingValue  配置文件中配置的进行分表的字段集合
     * @return  返回的数据实际存入的表名
     */
    @Override
    public Collection<String> doSharding(Collection<String> databaseNames, ComplexKeysShardingValue<Integer> complexKeysShardingValue) {
        // 得到每个分片健对应的值
        Collection<Integer> orderIdValues = this.getShardingValue(complexKeysShardingValue, "order_id");
        Collection<Integer> userIdValues = this.getShardingValue(complexKeysShardingValue, "user_id");

        List<String> shardingSuffix = new ArrayList<>();
        // 对两个分片健同时取模的方式分库
        for (Integer userId : userIdValues) {
            for (Integer orderId : orderIdValues) {
                String suffix = userId % 2 + "_" + orderId % 2;
                for (String databaseName : databaseNames) {
                    if (databaseName.endsWith(suffix)) {
                        shardingSuffix.add(databaseName);
                    }
                }
            }
        }
        return shardingSuffix;
    }

    private Collection<Integer> getShardingValue(ComplexKeysShardingValue<Integer> shardingValues, final String key) {
        Collection<Integer> valueSet = new ArrayList<>();
        Map<String, Collection<Integer>> columnNameAndShardingValuesMap = shardingValues.getColumnNameAndShardingValuesMap();
        if (columnNameAndShardingValuesMap.containsKey(key)) {
            valueSet.addAll(columnNameAndShardingValuesMap.get(key));
        }
        return valueSet;
    }


}


