package com.xufei.jackson.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

/**
 * 实现描述：Json工具（Jackson）
 */
@Slf4j
public class JsonUtils {

    /**
     * 默认日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 默认时间格式
     */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    /**
     * 默认日期时间格式
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = DEFAULT_DATE_FORMAT + " " + DEFAULT_TIME_FORMAT;
    private static final ObjectMapper defaultJsonMapper = new ObjectMapper();
    private static final ObjectMapper prettyJsonMapper = new ObjectMapper();
    private static final JavaTimeModule javaTimeModule = new JavaTimeModule();
    static {
        // java8日期处理. 修复 LocalDateTime 无法反序列化的bug
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)))
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        // json
        defaultJsonMapper
                /*
                在反序列化时忽略在 json 中存在但 Java 对象不存在的属性
                 */
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                /*
                关闭空对象不让序列化(默认true,如果对象为空将报异常)
                 */
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                /*
                当json字符串里带注释符时，默认情况下解析器不能解析。Feature.ALLOW_COMMENTS特性决定解析器是否允许解析使用Java/C++ 样式的注释（包括'/'+'*' 和'//' 变量）
                默认是false，不能解析带注释的JSON串;如果为false,JSON里的注释符会被过滤掉
                String json  = "{"
                            +"\"age\"" + ":" + 10 +
                            "/*"+"}";
                         或者{
                            "age": 10
                            //,
                            //"name": "曹操"
                        }
                 */
                .configure(JsonParser.Feature.ALLOW_COMMENTS, true)
                /*
                在序列化时忽略值为 null 的属性,如果属性的值为 null 是将不会被序列化
                 */
                .setSerializationInclusion(Include.NON_NULL)
                /*
                Json反序列化可以解析单引号包住的属性名称和字符串值,parser解析器默认情况下不能识别单引号包住的属性和属性值，默认下该属性也是关闭的。
                 String json = "{'age':12, 'name':'曹操'}";
                 */
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                /*
                默认情况下，标准的json串里属性名字都需要用双引号引起来。
                比如：{age:12, name:"曹操"}非标准的json串，解析器默认不能解析，需要设置Feature.ALLOW_UNQUOTED_FIELD_NAMES属性来处理这种没有双引号的json串
                 */
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
                /*
                在Data类型的数据被序列化时自定义时间日期格式,Jackson对时间(Date)序列化的转换格式默认是时间戳,可以取消时间的默认时间戳转化格式；
                默认时间戳转化格式取消后在序列化时日期格式默认为  yyyy-MM-dd'T'HH:mm:ss.SSSZ
                 */
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                /*
                 指定时区
                 */
                .setTimeZone(TimeZone.getTimeZone("GMT+8"))
                .registerModule(javaTimeModule);

        prettyJsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setSerializationInclusion(Include.NON_NULL)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .setTimeZone(TimeZone.getTimeZone("GMT+8"))
                .registerModule(javaTimeModule);

    }

    /**
     * 反序列化为对象
     *
     * @param json
     * @param targetClass
     * @param <T>
     * @return
     * @throws IOException
     */

    public static <T> T fromJson(String json, Class<T> targetClass) {
        if (StringUtils.isBlank(json) || targetClass == null) {
            return null;
        }

        try {
            return defaultJsonMapper.readValue(json, targetClass);
        } catch (Exception e) {
            log.warn(String.format("fromJson error %s, %s", json, targetClass.toString()), e);

        }
        return null;
    }

    /**
     * 反序列化为集合对象
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T fromJson(String json, TypeReference<T> type) {
        if (StringUtils.isBlank(json) || type == null) {
            return null;
        }

        try {
            return defaultJsonMapper.readValue(json, type);
        } catch (Exception e) {
            log.warn(String.format("fromJson error %s, %s", json, type.getType()), e);
        }
        return null;
    }

    /**
     * 反序列化为List
     *
     * @param json
     * @param targetClass
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> fromJson2List(String json, Class<T> targetClass) {
        if (StringUtils.isBlank(json) || targetClass == null) {
            return null;
        }

        try {
            return defaultJsonMapper.readValue(json, defaultJsonMapper.getTypeFactory()
                    .constructCollectionType(List.class, targetClass));
        } catch (Exception e) {
            log.warn(String.format("fromJson2List error %s, %s", json, targetClass.toString()), e);
        }
        return null;
    }

    /**
     * JsonNode -> Object
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T fromJsonNode(JsonNode json, Class<T> clazz) {
        try {
            if (json == null || json.isMissingNode() || json.isNull()) {
                return null;
            }
            return defaultJsonMapper.treeToValue(json, clazz);
        } catch (IOException e) {
            log.warn("json deserialization failure {}", json, e);
            return null;
        }
    }

    /**
     * 序列化
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }

        try {
            return defaultJsonMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn(String.format("toJson error object: %s ", obj), e);
        }
        return null;
    }

    /**
     * 序列化，输出格式化的JSON
     *
     * @param obj
     * @return
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    public static String toJsonPretty(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return prettyJsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.warn(String.format("toJsonPretty error object: %s ", obj), e);
        }
        return null;
    }

    /**
     * 转化为JsonNode
     *
     * @param Json
     * @return
     */
    public static JsonNode toJsonNode(String Json) {
        if (StringUtils.isBlank(Json)) {
            return null;
        }
        try {
            JsonNode arrNode = new ObjectMapper().readTree(Json);
            return arrNode;
        } catch (Exception e) {
            log.warn(String.format("toJsonNode error Json: %s ", Json), e);
        }
        return null;
    }

    /**
     * @param json
     * @return
     */
    public static boolean isJsonFormat(String json) {
        return isJsonObjectFormat(json) || isJsonArrayFormat(json);
    }

    /**
     * 是否jsonObject格式
     *
     * @param json
     * @return
     */
    public static boolean isJsonObjectFormat(String json) {
        String trimed = StringUtils.trim(json);

        return StringUtils.startsWith(trimed, "{") && StringUtils.endsWith(trimed, "}");
    }

    /**
     * 是否是jsonArray格式
     *
     * @param json
     * @return
     */
    public static boolean isJsonArrayFormat(String json) {
        String trimed = StringUtils.trim(json);

        return StringUtils.startsWith(trimed, "[") && StringUtils.endsWith(trimed, "]");
    }
}