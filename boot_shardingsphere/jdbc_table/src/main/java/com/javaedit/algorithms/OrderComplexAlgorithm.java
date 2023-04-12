package com.javaedit.algorithms;


import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

import java.util.*;

/**
 * 通过自定义类的方式来实现分片算法，也是比较常用的一种做法，优先是比较灵活，可以做一些自定义的特殊分片逻辑;
 * 根据需求实现不同的类：
 * STANDARD ==> StandardShardingAlgorithm
 * COMPLEX ==>   ComplexKeysShardingAlgorithm
 * HINT         ==>   HintShardingAlgorithm
 * 比如要实现一个自定义的复合分片的算法;
 * 比如要实现一个多分片键的就实现ComplexKeysShardingAlgorithm;
 *
 */
public class OrderComplexAlgorithm implements ComplexKeysShardingAlgorithm<Long> {
    private Properties props;
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Long> shardingValue) {
        if (!shardingValue.getColumnNameAndRangeValuesMap().isEmpty()) {
            //如果是范围查询并且没有路由键信息，全表查询
            return availableTargetNames;
        }
        //只有一个可用的表信息
        int size = availableTargetNames.size();
        if (size == 1) {
            return availableTargetNames;
        }

        //获取逻辑表
        String logicTableName = shardingValue.getLogicTableName();
        Set<String> finalTargetNames = new HashSet<>();
        //获取分片信息
        Map<String, Collection<Long>> columnNameAndShardingValuesMap = shardingValue.getColumnNameAndShardingValuesMap();
        Set<Map.Entry<String, Collection<Long>>> entries = columnNameAndShardingValuesMap.entrySet();
        Long modValue = 0L;
        for (Map.Entry<String, Collection<Long>> entry : entries) {
            Collection<Long> values = entry.getValue();
            Long count = values.stream().findFirst().get();
            modValue = (count % size) + modValue;
        }
        modValue = modValue % size;
        finalTargetNames.add(logicTableName + "_" + modValue);
        return finalTargetNames;
    }

    @Override
    public Properties getProps() {
        return this.props;
    }

    @Override
    public void init(Properties properties) {
        this.props=properties;
    }
}
