package com.xufei.rw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 目前ShardingSphere只支持单主库，所以如果要保证业务的高可用，那么目前ShardingSphere不是很好的选择
 *
 * spring boot 整合 sharding-jdbc 实现 mysql数据库读写分离功能的前提在于已经配置好mysql集群的主从复制
 *
 * https://www.cnblogs.com/steakliu/p/16514796.html
 * 更多配置查看官网:
 * https://shardingsphere.apache.org/document/5.1.2/cn/user-manual/shardingsphere-jdbc/yaml-config/rules/sharding/
 *
 */
@SpringBootApplication
public class SpringbootShardingJdbcRWApplication {
    private static final Logger log= LoggerFactory.getLogger(SpringbootShardingJdbcRWApplication.class);
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext run = SpringApplication.run(SpringbootShardingJdbcRWApplication.class);
        ConfigurableEnvironment environment = run.getEnvironment();
        log.info("\n[----------------------------------------------------------]\n\t" +
                        "测试窗口:\thttp://{}:{}/1" +
                        "\n[----------------------------------------------------------",
                InetAddress.getLocalHost().getHostAddress(),
                environment.getProperty("server.port"));

    }
}
