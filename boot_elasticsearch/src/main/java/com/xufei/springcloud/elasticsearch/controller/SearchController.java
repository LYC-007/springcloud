package com.xufei.springcloud.elasticsearch.controller;

import com.xufei.springcloud.elasticsearch.config.RestClientConfig;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private RestHighLevelClient esClient;
    /**
     * 我认为一个完整的查询包含下面几个步骤
     * @throws IOException
     */
    @GetMapping("/1")
    public void search01() throws IOException {
        /**
        * 创建SearchRequest对象（并且设置要查询索引名称 ）
        */
        SearchRequest request = new SearchRequest();
        request.indices("user");//“user是索引名”
        /**
         * 创建SearchSourceBuilder对象(通过此字段设置查询的规则;)
         */
        SearchSourceBuilder builder = new SearchSourceBuilder();

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("ageGroup").field("字段值");
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

                /**
                 * 1.过滤字段: 设置查询你想要的字段和不想要的字段;
                 */
        builder.fetchSource("name","sex")
                /**
                   2.设置查询条件(查询条件有多种选择，你可以选择下面的任何一项，必须要有一个)
                     A.组合条件查询(合并多个查询条件)：BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                         boolQueryBuilder.must(QueryBuilders.matchQuery("sex", "男")); //相当于“and”
                         boolQueryBuilder.mustNot(QueryBuilders.matchQuery("sex", "男"));//相当于“排除”
                         boolQueryBuilder.should(QueryBuilders.matchQuery("age", 30));//相当于“or”
                     B.单个条件查询：TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("字段名", "字段值");
                     C.全量查询：MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
                     D.范围查询：RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("字段名");
                         rangeQuery.gte(30);
                         rangeQuery.lt(50);
                     E.模糊查询：FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("字段名", "输入值").fuzziness(Fuzziness.TWO);
                        Fuzziness.TWOFuzzine---->表示和输入值差2个字符(需要设置偏差的范围);
                     G.过滤查询：boolQueryBuilder.filter(QueryBuilders.rangeQuery("age").gt(25).lt(55));
                     F.非“Text”字段建议使用：TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("age", 30);
                        builder.query(termQueryBuilder);
                     H.短语匹配：MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery("字段名", "字段值");
                        builder.query(matchPhraseQueryBuilder);//短语匹配，查询的“字段值”不可拆分查询;
                 */
                .query(boolQueryBuilder)
                /**
                 * 3.过滤:builder.postFilter(QueryBuilders.rangeQuery("age").gt(25).lt(35));
                 */
                .postFilter(QueryBuilders.rangeQuery("age").gt(25).lt(35))
                /**
                 * 4.分页
                 *      size(int size) // 每页显示数据条数
                 *      from(int from) // (当前页码-1)*每页显示数据条数
                 */
                .size(1).from(4)
                /**
                 * 6.聚合查询(查询最大值，最小值，平均值，分组查询等)：aggregation(AggregationBuilder aggregation),分为下面两种:
                 *      1.查询最大值，最小值，平均值：AggregationBuilder aggregationBuilder = AggregationBuilders.max("maxAge").field("字段名");
                 *          为查询的结果取一个名字“maxAge”,设置那个字段值的最大值;
                 *      2.分组查询：AggregationBuilder aggregationBuilder = AggregationBuilders.terms("ageGroup").field("字段值");
                 *          取一个名字“ageGroup”,设置根据那个字段值的分组；
                 */
                .aggregation(aggregationBuilder)//
                /**
                 * 7.排序：sort(String name, SortOrder order);
                 */
                .sort("name")
                /**
                 * 8.高亮显示，举例:
                 *      SearchSourceBuilder builder = new SearchSourceBuilder();
                 *      HighlightBuilder highlightBuilder = new HighlightBuilder();
                 *      highlightBuilder.preTags("<font color='red'>");
                 *      highlightBuilder.postTags("</font>");
                 *      highlightBuilder.field("字段名");
                 *      builder.highlighter(highlightBuilder);
                 */
                .highlighter(highlightBuilder);

        /**
         * 将SearchSourceBuilder对象设置到SearchRequest对象里面
         */
        request.source(builder);
        /**
         * 客户端调用“search”方法
         */
        SearchResponse response = esClient.search(request, RestClientConfig.COMMON_OPTIONS);
        SearchHits hits = response.getHits();//获取查询的结果
        System.out.println(hits.getTotalHits());//获取查询的总记录数
        System.out.println(response.getTook());//获取查询花费的时间
    }
}
