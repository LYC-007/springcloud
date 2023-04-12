package com.xufei.jackson.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.util.List;

public final class JacksonJsonUtils {
    private static final JsonMapper OBJECT_MAPPER = JsonMapper.builder()
            /*
            反序列Json字符串中包含制控制字符Feature.ALLOW_UNQUOTED_CONTROL_CHARS该特性决定parser是否允许JSON字符串包含非引号控制字符（值小于32的ASCII字符，包含制表符\t、换行符\n和回车\r）。
            String json = "{'age':12, 'name':'曹操\n'}";
             */
            .enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS)
            /*
            当反序列化的JSON串里带有反斜杠("\")时，默认objectMapper反序列化会失败，抛出异常Unrecognized character escape。
            可以通过Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER来设置当反斜杠存在时，能被objectMapper反序列化。
             */
            .enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)
            /*
            Json字符串里数字类型值为正无穷、负无穷或NaN时，默认反序列化会失败,需要开启JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS
             */
            .enable(JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS)
            /*
            默认情况下objectMapper解析器是不能解析以"0"为开头的数字，需要开启JsonReadFeature.ALLOW_LEADING_ZEROS_FOR_NUMBERS才能使用。
            String json = "{\"age\":0012, \"name\":\"曹操\"}";
             */
            .enable(JsonReadFeature.ALLOW_LEADING_ZEROS_FOR_NUMBERS)
            /*
               返回JSON格式的数据忽略某个字段（对应的实体中没有对应字段）
             */
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            /*
             在序列化时忽略值为 null 的属性,如果属性的值为 null 是将不会被序列化
             */
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .build();


    public static String toJSONString(Object data) {

        try {
            return OBJECT_MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }

    }

    public static <T> T fromJson(String str, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(str, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T fromJson(byte[] data, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(data, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public static <T> List<T> parseList(String str, Class<T> clazz) {
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructCollectionLikeType(List.class, clazz);
        try {
            return OBJECT_MAPPER.readValue(str, javaType);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}