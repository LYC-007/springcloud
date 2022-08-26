package com.xufei.springcloud.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 倒排索引机制：
 *        Elasticsearch是以文档（Json）的形式保存数据，每个文档会有一个主键“id”；它还会把每个字段的值通过分拆器（不同的分拆器对应不同的拆分规则）拆分成几份，每一份对应一个或多个文档的“id”，由此形成一张倒排索引表;
 *        当查询数据的时候，它也会把查询的语句拆分成几份，通过倒排索引表找到每份对应的文档“id”，找到相关性得分高的文档返回；
 */
@SpringBootApplication
public class ElasticSearchApplication8018 {
    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchApplication8018.class);
    }
}
