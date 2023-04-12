package com.javaedit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 分片算法  https://blog.csdn.net/qq_34279995/article/details/122421989?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522167626407316800182785141%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=167626407316800182785141&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduend~default-1-122421989-null-null.142^v73^insert_down2,201^v4^add_ask,239^v1^control&utm_term=sharding-algorithms&spm=1018.2226.3001.4187
 */

@MapperScan("com.javaedit.mapper")
@SpringBootApplication
public class TableShardingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TableShardingApplication.class, args);
    }

}
