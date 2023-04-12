package com.xufei.rw.loadbalance;

import lombok.Getter;
import org.apache.shardingsphere.readwritesplitting.spi.ReadQueryLoadBalanceAlgorithm;
import org.apache.shardingsphere.transaction.TransactionHolder;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public final class RoundRobinReplicaLoadBalanceAlgorithm implements ReadQueryLoadBalanceAlgorithm {

    private final AtomicInteger count = new AtomicInteger(0);

    @Getter
    private Properties props;

    @Override
    public void init(final Properties props) {
        this.props = props;
    }

    @Override
    public String getDataSource(final String name, final String writeDataSourceName, final List<String> readDataSourceNames) {
        if (TransactionHolder.isTransaction()) {
            return writeDataSourceName;
        }
        return readDataSourceNames.get(Math.abs(count.getAndIncrement()) % readDataSourceNames.size());
    }

    @Override
    public String getType() {
        return "ROUND_ROBIN";
    }

    @Override
    public boolean isDefault() {
        return true;
    }
}
