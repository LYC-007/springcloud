package com.xufei.rw.loadbalance;

import lombok.Getter;
import org.apache.shardingsphere.readwritesplitting.spi.ReadQueryLoadBalanceAlgorithm;

import java.util.List;
import java.util.Properties;

/**
 * 功能说明： 自定义负载均衡算法
 * 为了演示，我们将所有请求全部都负载到slave2。
 *实现步骤:
 * 1.自定义负载均衡算法需要实现ReadQueryLoadBalanceAlgorithm接口，里面核心的两个方法是getDataSource和getType
 * 2.在自己项目的META-INF/services目录下编写负载均衡SPI接口，里面内容为我们自定义的负载均衡算法的类文件的位置。
 * 3.就可以在配置文件中配置自定义的负载均衡算法了
 *            data-sources:
 *             read_write_db:
 *               type: Static
 *               props:
 *                 write-data-source-name: db1
 *                 read-data-source-names: db2,db3,db4
 *               load-balancer-name: CUSTOM
 */
public class CustomReplicaLoadBalanceAlgorithm implements ReadQueryLoadBalanceAlgorithm {

    @Getter
    private Properties props;

    /**
     * getDataSource是算法的逻辑实现部分，
     * 其目的是选出一个目标数据库，此方法会传入readDataSourceNames，它是读库的集合，我们此处直接返回db2，那么会一直读db2
     */
    @Override
    public String getDataSource(final String name, final String writeDataSourceName, final List<String> readDataSourceNames) {
        return "slave2";
    }
    /**
     * getType是返回负载均衡算法的名称(自定义一个名字)。 需要将该名称配置到配置文件中
     *
     *      data-sources:
     *       read_write_db:
     *         type: Static
     *         props:
     *           write-data-source-name: db1
     *           read-data-source-names: db2,db3,db4
     *         load-balancer-name: CUSTOM
     */

    @Override
    public String getType() {
        return "CUSTOM";
    }

    @Override
    public void init(Properties props) {
        this.props = props;
    }

    @Override
    public boolean isDefault() {
        return false;
    }
}
