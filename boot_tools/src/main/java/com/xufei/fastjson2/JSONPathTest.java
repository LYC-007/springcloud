package com.xufei.fastjson2;

import com.alibaba.fastjson2.*;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 更多测试查看API:https://gitee.com/wenshao/fastjson2/blob/main/docs/jsonpath_cn.md
 * jsonpath是使用一种简单的方法提取给定的json文档的部分内容，我们做接口测试时，目前主要流行的数据结构是json，遇到复杂的json格式，使用jsonpath提取数据
 */

public class JSONPathTest {
    public static void main(String[] args) throws Exception {
        test1();
    }
    public static void test_entity() throws Exception {
        Entity entity = new Entity(123, new Object());
        System.out.println(JSONPath.contains(entity, "$.value"));//true
        System.out.println(JSONPath.contains(entity, "$.name"));//false
    }

    public void test0() {
        assertEquals("{\"color\":\"red\",\"price\":19.95,\"gears\":[23,50],\"extra\":{\"x\":0},\"escape\":\"Esc\\b\\f\\n\\r\\t*\",\"nullValue\":null}"
                , JSON.toJSONString(
                        JSONPath.extract(STR, "$.store.bicycle[?(@.color == 'red' )]"),
                        JSONWriter.Feature.WriteNulls
                )
        );
    }

    public static void test1() {
        assertEquals("{\"color\":\"red\",\"price\":19.95,\"gears\":[23,50],\"extra\":{\"x\":0},\"escape\":\"Esc\\b\\f\\n\\r\\t*\",\"nullValue\":null}"
                , JSON.toJSONString(
                        JSONPath.extract(STR, "$.store.bicycle[?(@.gears == [23, 50])]"),
                        JSONWriter.Feature.WriteNulls
                )
        );
        assertNull(JSONPath.extract(STR, "$.store.bicycle[?(@.gears == [23, 77])]"));

        assertEquals("{\"color\":\"red\",\"price\":19.95,\"gears\":[23,50],\"extra\":{\"x\":0},\"escape\":\"Esc\\b\\f\\n\\r\\t*\",\"nullValue\":null}"
                , JSON.toJSONString(
                        JSONPath.extract(STR, "$.store.bicycle[?(@.extra == {\"x\":0})]"),
                        JSONWriter.Feature.WriteNulls
                )
        );

        assertEquals("{\"color\":\"red\",\"price\":19.95,\"gears\":[23,50],\"extra\":{\"x\":0},\"escape\":\"Esc\\b\\f\\n\\r\\t*\",\"nullValue\":null}"
                , JSON.toJSONString(
                        JSONPath.extract(STR, "$.store.bicycle[?(@.escape == 'Esc\\b\\f\\n\\r\\t\\u002A')]"),
                        JSONWriter.Feature.WriteNulls
                )
        );
    }

    public final static String STR =
            "{ \"store\": {\n" +
                    "    \"book\": [ \n" +
                    "      { \"category\": \"reference\",\n" +
                    "        \"author\": \"Nigel Rees\",\n" +
                    "        \"title\": \"Sayings of the Century\",\n" +
                    "        \"price\": 8.95\n" +
                    "      },\n" +
                    "      { \"category\": \"fiction\",\n" +
                    "        \"author\": \"Evelyn Waugh\",\n" +
                    "        \"title\": \"Sword of Honour\",\n" +
                    "        \"price\": 12.99\n" +
                    "      },\n" +
                    "      { \"category\": \"fiction\",\n" +
                    "        \"author\": \"Herman Melville\",\n" +
                    "        \"title\": \"Moby Dick\",\n" +
                    "        \"isbn\": \"0-553-21311-3\",\n" +
                    "        \"price\": 8.99\n" +
                    "      },\n" +
                    "      { \"category\": \"fiction\",\n" +
                    "        \"author\": \"J. R. R. Tolkien\",\n" +
                    "        \"title\": \"The Lord of the Rings\",\n" +
                    "        \"isbn\": \"0-395-19395-8\",\n" +
                    "        \"price\": 22.99\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"bicycle\": {\n" +
                    "      \"color\": \"red\",\n" +
                    "      \"price\": 19.95\n," +
                    "      \"gears\": [23, 50]\n," +
                    "      \"extra\": {\"x\": 0}\n," +
                    "      \"escape\" : \"Esc\\b\\f\\n\\r\\t\\u002A\",\n" +
                    "      \"nullValue\": null\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";
    public static void test300() {
        //使用JSONPath部分读取数据
        String str = "{\"id\":123,\"name\":\"jack\"}";
        JSONReader parser = JSONReader.of(str);
        JSONPath path = JSONPath.of("$.id");
        Object result = path.extract(parser); // 123

        //使用JSONPath读取部分utf8Bytes的数据
        byte[] utf8Bytes = "{\"id\":123,\"name\":\"jack\"}".getBytes(StandardCharsets.UTF_8);
        JSONPath jsonPath = JSONPath.of("$.id"); // 缓存起来重复使用能提升性能
        JSONReader jsonReaderUtf8 = JSONReader.of(utf8Bytes);
        Object result1 = jsonPath.extract(jsonReaderUtf8);



        //使用JSONPath读取部分jsonbBytes的数据
        byte[] jsonbBytes = JSONB.toBytes(new Product(1001, "jack"));
        JSONPath idPath = JSONPath.of("$.id"); // 缓存起来重复使用能提升性能
        JSONReader ofJSONB = JSONReader.ofJSONB(jsonbBytes); // 注意，这是利用ofJSONB方法
        Object result2 = idPath.extract(ofJSONB);
    }
}
