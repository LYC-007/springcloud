package com.xufei.springcloud.elasticsearch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.xufei.springcloud.elasticsearch.config.RestClientConfig;
import com.xufei.springcloud.elasticsearch.dao.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;

/**
 * 浏览器发不了Post请求，测试的时候有Post请求的请用Postman
 *
 */
//http://localhost:8018/index/create
@RequestMapping("/index")
@RestController
public class IndexController {

    @Autowired
    private RestHighLevelClient esClient;
    /**
     * 创建,删除，查询索引
     * @throws IOException
     */
    @GetMapping("/create")
    public void createIndexRequest() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("user");
        CreateIndexResponse createIndexResponse = null;
        try {
            createIndexResponse = esClient.indices().create(request, RestClientConfig.COMMON_OPTIONS);
            boolean acknowledged = createIndexResponse.isAcknowledged();
            System.out.println(acknowledged);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/delete")
    public void deleteIndexRequest() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("user");
        AcknowledgedResponse delete = esClient.indices().delete(request, RestClientConfig.COMMON_OPTIONS);
        boolean acknowledged = delete.isAcknowledged();
        System.out.println(acknowledged);
    }
    @GetMapping("/find")
    public void findIndexRequest() throws IOException {
        // 查询索引
        GetIndexRequest request = new GetIndexRequest("user");
        GetIndexResponse getIndexResponse =esClient.indices().get(request, RestClientConfig.COMMON_OPTIONS);
        // 响应状态
        System.out.println(getIndexResponse.getAliases());
        System.out.println(getIndexResponse.getMappings());
        System.out.println(getIndexResponse.getSettings());
    }

    /**
     *  插入测试
     *  创建完索引后再到可视化界面：Kibana创建索引的映射关系，在进行插入操作
     */
    @PostMapping("/insert")
    public void insertOne() throws IOException {
        // 插入数据
        IndexRequest request = new IndexRequest();
        request.index("user").id("1009");
        User user = new User();
        user.setAge(14);
        user.setName("Jack");
        user.setSex("男");
        // 向ES插入数据，必须将数据转换位JSON格式
        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(user);
        request.source(userJson, XContentType.JSON);
        IndexResponse response = esClient.index(request,RestClientConfig.COMMON_OPTIONS);
        System.out.println(response.getResult());
    }
    @PostMapping("/4")
    public void insertList() throws IOException {
        // 批量插入数据
        BulkRequest request = new BulkRequest();
        User user = new User();
        user.setAge(17);
        user.setName("Mary");
        user.setSex("男");
        // 向ES插入数据，必须将数据转换位JSON格式
        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(user);
        request.add(new IndexRequest().index("user").id("1010").source(userJson,XContentType.JSON));
        BulkResponse response = esClient.bulk(request, RestClientConfig.COMMON_OPTIONS);
        System.out.println(response.getTook());
        System.out.println(Arrays.toString(Arrays.stream(response.getItems()).toArray()));
    }

    /**
     * 删除测试
     */
    @PostMapping("/1")
    public void deleteOne() throws IOException {
        DeleteRequest request = new DeleteRequest();
        request.index("user").id("1009");
        DeleteResponse response = esClient.delete(request, RestClientConfig.COMMON_OPTIONS);
        System.out.println(response.toString());
    }
    @PostMapping("/2")
    public void deleteList() throws IOException {
        // 批量删除数据
        BulkRequest request = new BulkRequest();
        request.add(new DeleteRequest().index("user").id("1001"));
        request.add(new DeleteRequest().index("user").id("1002"));
        request.add(new DeleteRequest().index("user").id("1003"));
        BulkResponse response = esClient.bulk(request, RestClientConfig.COMMON_OPTIONS);
        System.out.println(response.getTook());
        System.out.println(Arrays.toString(response.getItems()));
    }
    /**
     *  更新测试
     */
    @GetMapping("/3")
    public void update() throws IOException {
        // 修改数据
        UpdateRequest request = new UpdateRequest();
        request.index("user").id("1009");
        request.doc(XContentType.JSON, "sex", "男","name","Jack01");
        UpdateResponse response = esClient.update(request, RestClientConfig.COMMON_OPTIONS);
        System.out.println(response.getResult());
    }
}
