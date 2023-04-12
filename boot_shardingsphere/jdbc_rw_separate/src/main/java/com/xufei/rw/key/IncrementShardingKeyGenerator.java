package com.xufei.rw.key;

import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.sharding.spi.KeyGenerateAlgorithm;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public final class IncrementShardingKeyGenerator implements KeyGenerateAlgorithm {
    /**
     * 自定义的生成方案类型
     */
    @Override
    public String getType() {
        return "INCREMENT";
    }

    private final AtomicInteger count = new AtomicInteger();

    @Getter
    @Setter
    private Properties properties = new Properties();
    /**
     * 核心方法-生成主键ID
     */
    @Override
    public Comparable<?> generateKey() {
        return count.incrementAndGet();
    }

    @Override
    public Properties getProps() {
        return this.properties;
    }

    @Override
    public void init(Properties properties) {
        this.properties = properties;
    }
}
