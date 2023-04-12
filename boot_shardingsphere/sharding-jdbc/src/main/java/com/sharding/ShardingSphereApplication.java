package com.sharding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * read_wirte_separate.yml 读写分离配置
 * db_application.yml 分库配置
 * dbAndtable_application.yml 分库分表配置
 * table_application.yml 分表配置
 * docker run -p 3307:3306 --network zzyy_network --name mysql-master  -v /zzyyuse/mysql/master/log:/var/log/mysql  -v /zzyyuse/mysql/master/data:/var/lib/mysql  -v /zzyyuse/mysql/master/conf:/etc/mysql  -e MYSQL_ROOT_PASSWORD=1234   -d mysql:5.7
 * docker run -p 3317:3306 --network zzyy_network --name mysql-slave1  -v /zzyyuse/mysql/slave1/log:/var/log/mysql  -v /zzyyuse/mysql/slave1/data:/var/lib/mysql  -v /zzyyuse/mysql/slave1/conf:/etc/mysql  -e MYSQL_ROOT_PASSWORD=1234  -d mysql:5.7
 *
 * docker run -p 3327:3306 --network zzyy_network --name mysql-slave2  -v /zzyyuse/mysql/slave2/log:/var/log/mysql  -v /zzyyuse/mysql/slave2/data:/var/lib/mysql  -v /zzyyuse/mysql/slave2/conf:/etc/mysql  -e MYSQL_ROOT_PASSWORD=1234  -d mysql:5.7
 * change master to master_host='mysql-master',master_user='master',master_password='123456',master_log_file='mysql-bin.000003',master_log_pos=889,master_port=3306;
 */

/**
 * spring boot 整合 sharding-jdbc 实现 mysql数据库读写分离功能的前提在于已经配置好mysql集群的主从复制
 *
 * 数据分片：
 *      1.分库分表：将数据分片进行垂直拆分和水平拆分
 *      2.读写分离：根据SQL语义的分析，将读操作和写操作分别路由至主库与从库
 *      3.分片策略：基于分片键和不同的分片算法实现分片
 *      4.分布式主键：内置的分布式主键生成器，例如UUID、SNOWFLAKE
 *
 * 本项目主要是读写分类示范
 *
 */
@SpringBootApplication
public class ShardingSphereApplication {
    private static final Logger log = LoggerFactory.getLogger(ShardingSphereApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext run = SpringApplication.run(ShardingSphereApplication.class, args);
        ConfigurableEnvironment environment = run.getEnvironment();
        log.info("\n[----------------------------------------------------------]\n\t" +
                        "测试窗口:\thttp://{}:{}/test" +
                        "\n[----------------------------------------------------------",
                InetAddress.getLocalHost().getHostAddress(),
                environment.getProperty("server.port"));

    }

}
