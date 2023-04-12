package com.xufei.fastjson2;

import com.alibaba.fastjson2.*;


import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Fastjson_学习笔记
 * GitHub：alibaba/fastjson2     https://gitcode.net/mirrors/alibaba/fastjson2?utm_source=csdn_github_accelerator
 * Gitee：wenshao：fastjson2      https://gitee.com/wenshao/fastjson2
 * Gitee：astjson2文档                  https://gitee.com/wenshao/fastjson2/tree/main/docs
 * FASTJSON2提供的Annotations      https://gitee.com/wenshao/fastjson2/blob/main/docs/annotations_cn.md#fastjson2%E6%8F%90%E4%BE%9B%E7%9A%84annotations
 * FASTJSON 2 Autotype机制介绍      https://gitee.com/wenshao/fastjson2/blob/main/docs/autotype_cn.md
 * 通过Features配置序列化和反序列化的行为（格式化、别名等。。。）         https://gitee.com/wenshao/fastjson2/blob/main/docs/features_cn.md
 * FASTJSON v2 JSONSchema的支持        https://gitee.com/wenshao/fastjson2/blob/main/docs/json_schema_cn.md
 * FASTJSON2 JSONPath支持介绍       https://gitee.com/wenshao/fastjson2/blob/main/docs/jsonpath_cn.md
 * 使用Mixin注入Annatation定制序列化和反序列化         https://gitee.com/wenshao/fastjson2/blob/main/docs/mixin_cn.md
 * 列化时对字段名做处理的扩展接口 NameFilter           https://gitee.com/wenshao/fastjson2/blob/main/docs/name_filter_cn.md
 * 在 Spring 中集成 Fastjson2                   https://gitee.com/wenshao/fastjson2/blob/main/docs/spring_support_cn.md
 * JSONB格式文档 https://github.com/alibaba/fastjson2/wiki/jsonb_format_cn
 * JSON和JSONObject的区别:
 * JSONObject是继承JSON的，而toJSONString，parse，parseObject 这些方法只在JSON这个类里有，JSONObject并没有重写，所以他们其实没有区别
 */
public class FastJson2Test {

    /**
     * 通过Features配置序列化的行为（格式化、别名等）
     * 关于Feature 的其他配置参照官网:https://gitee.com/wenshao/fastjson2/blob/main/docs/features_cn.md
     */
    public static void test() {
         Product product = new Product(1001, "许飞");
        JSON.toJSONString(product, JSONWriter.Feature.PrettyFormat); //让Json字符串格式化输出
        JSON.toJSONString(product, JSONWriter.Feature.WriteClassName);//序列化的时候带上JavaBean的类型信息
        JSONObject.toJSONString(product,
                JSONWriter.Feature.PrettyFormat,    // 格式化输出
                JSONWriter.Feature.UseSingleQuotes, // 使用单引号
                JSONWriter.Feature.WriteNulls);     // 序列话时包含为 null 的字段

        /**
         * JSONWriter.Feature介绍:
         *FieldBased	基于字段反序列化，如果不配置，会默认基于public的field和getter方法序列化。配置后，会基于非static的field（包括private）做反序列化。
         * IgnoreNoneSerializable	序列化忽略非Serializable类型的字段
         * BeanToArray	将对象序列为[101,"XX"]这样的数组格式，这样的格式会更小
         * WriteNulls	序列化输出空值字段
         * BrowserCompatible	在大范围超过JavaScript支持的整数，输出为字符串格式
         * NullAsDefaultValue	将空置输出为缺省值，Number类型的null都输出为0，String类型的null输出为""，数组和Collection类型的输出为[]
         * WriteBooleanAsNumber	将true输出为1，false输出为0
         * WriteNonStringValueAsString	将非String类型的值输出为String，不包括对象和数据类型
         * WriteClassName	序列化时输出类型信息
         * NotWriteRootClassName	打开WriteClassName的同时，不输出根对象的类型信息
         * NotWriteHashMapArrayListClassName	打开WriteClassName的同时，不输出类型为HashMap/ArrayList类型对象的类型信息，反序列结合UseNativeObject使用，能节省序列化结果的大小
         * NotWriteDefaultValue	当字段的值为缺省值时，不输出，这个能节省序列化后结果的大小
         * WriteEnumsUsingName	序列化enum使用name
         * WriteEnumUsingToString	序列化enum使用toString方法
         * IgnoreErrorGetter	忽略setter方法的错误
         * PrettyFormat	格式化输出
         * ReferenceDetection	打开引用检测，这个缺省是关闭的，和fastjson 1.x不一致
         * WriteNameAsSymbol	将字段名按照symbol输出，这个仅在JSONB下起作用
         * WriteBigDecimalAsPlain	序列化BigDecimal使用toPlainString，避免科学计数法
         * UseSingleQuotes	使用单引号
         * MapSortField	对Map中的KeyValue按照Key做排序后再输出。在有些验签的场景需要使用这个Feature
         * WriteNullListAsEmpty	将List类型字段的空值序列化输出为空数组"[]"
         * WriteNullStringAsEmpty	将String类型字段的空值序列化输出为空字符串""
         * WriteNullNumberAsZero	将Number类型字段的空值序列化输出为0
         * WriteNullBooleanAsFalse	将Boolean类型字段的空值序列化输出为false
         * NotWriteEmptyArray	数组类型字段当length为0时不输出
         * WriteNonStringKeyAsString	将Map中的非String类型的Key当做String类型输出
         * ErrorOnNoneSerializable	序列化非Serializable对象时报错
         * WritePairAsJavaBean	将 Apache Common 包中的Pair对象当做JavaBean序列化
         * LargeObject	这个是一个保护措施，是为了防止序列化有循环引用对象消耗过大资源的保护措施。
         */

    }

    /**
     * 通过Features配置反序列化的行为（格式化、别名等）
     * 关于Feature 的其他配置参照官网:https://gitee.com/wenshao/fastjson2/blob/main/docs/features_cn.md
     */
    public static void test1() {
        String jsonString = "{ 'age':10,'groupTag':'A','name':' 张三','score':null}";
        JSONObject.parseObject(jsonString, Hero.class);//jsonString 转 JavaBean
        JSONObject jsonObject = JSONObject.parseObject(jsonString);//jsonString 转 JSONObject
        jsonObject.to(Hero.class); //JSONObject.parseObject(jsonString)
        /**
         * JSONObject 获取 JavaBean
         */
        String jsonStr = "{" +
                "'张三': { 'age':10,'groupTag':'A','name':'张三','score':23 }," +
                "'李四': { 'age':11,'groupTag':'B','name':'李四','score':28 }" +
                "}";
        JSONObject json = JSONObject.parseObject(jsonStr);
        json.get("张三");// {"age":10,"groupTag":"A","name":"张三","score":23}
        json.getObject("李四", Hero.class);// Hero(name=李四, age=11, score=28, groupTag=B)
        /**
         * JSONReader.Feature介绍:
         * FieldBased	基于字段反序列化，如果不配置，会默认基于public的field和getter方法序列化。配置后，会基于非static的field（包括private）做反序列化。在fieldbase配置下会更安全
         * IgnoreNoneSerializable	反序列化忽略非Serializable类型的字段
         * SupportArrayToBean	支持数据映射的方式
         * InitStringFieldAsEmpty	初始化String字段为空字符串""
         * SupportAutoType	支持自动类型，要读取带"@type"类型信息的JSON数据，需要显示打开SupportAutoType
         * SupportSmartMatch	默认下是camel case精确匹配，打开这个后，能够智能识别camel/upper/pascal/snake/Kebab五中case
         * UseNativeObject	默认是使用JSONObject和JSONArray，配置后会使用LinkedHashMap和ArrayList
         * SupportClassForName	支持类型为Class的字段，使用Class.forName。为了安全这个是默认关闭的
         * IgnoreSetNullValue	忽略输入为null的字段
         * UseDefaultConstructorAsPossible	尽可能使用缺省构造函数，在fieldBase打开这个选项没打开的时候，会可能用Unsafe.allocateInstance来实现
         * UseBigDecimalForFloats	默认配置会使用BigDecimal来parse小数，打开后会使用Float
         * UseBigDecimalForDoubles	默认配置会使用BigDecimal来parse小数，打开后会使用Double
         * ErrorOnEnumNotMatch	默认Enum的name不匹配时会忽略，打开后不匹配会抛异常
         * TrimString	对读取到的字符串值做trim处理
         * ErrorOnNotSupportAutoType	遇到AutoType报错（缺省是忽略）
         * DuplicateKeyValueAsArray	重复Key的Value不是替换而是组合成数组
         * AllowUnQuotedFieldNames	支持不带双引号的字段名
         * NonStringKeyAsString	非String类型的Key当做String处理
         * Base64StringAsByteArray	将byte[]序列化为Base64格式的字符串
         *
         */
    }


    public static void test2() {
        ArrayList<Hero> heroArrayList = new ArrayList<>();
        heroArrayList.add(new Hero("张三", 10, 23, "A"));
        heroArrayList.add(new Hero("李四", 104, 69, "B"));
        /**
         * jsonString 转 JSONArray
         */
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(heroArrayList));
        jsonArray.get(0);// {"age":10,"groupTag":"A","name":"张三","score":23}
        jsonArray.getObject(0, Hero.class);// Hero(name=张三, age=10, score=23, groupTag=A)

        /**
         * JSONArray 转 List
         */
        List<Hero> heroList = JSONArray.parseArray(JSON.toJSONString(heroArrayList)).toJavaList(Hero.class);
        // [Hero(name=张三, age=10, score=23, groupTag=A), Hero(name=李四, age=11, score=28, groupTag=B)]
        /**
         * jsonString 转 List
         */
        List<Hero> parseArrayList = JSON.parseArray(JSON.toJSONString(heroArrayList), Hero.class);

        /**
         * jsonString 转 Map
         */
        String jsonString = "{" +
                "'张三': { 'age':10,'groupTag':'A','name':'张三','score':23 }," +
                "'李四': { 'age':11,'groupTag':'B','name':'李四','score':28 }" +
                "}";
        HashMap<String, Hero> map = JSONObject.parseObject(jsonString, new TypeReference<HashMap<String, Hero>>() {
        });

        /**
         * Map转JavaBean
         */
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("age", "102");
        stringStringHashMap.put("groupTag", "A");
        stringStringHashMap.put("name", "root");
        stringStringHashMap.put("score", "28");
        Hero bean = JSONObject.parseObject(JSONObject.toJSONString(stringStringHashMap), Hero.class);

    }

    /**
     * 读取JSON对象
     */
    public static void test000() {
        String jsonString = JSON.toJSONString(new Product(1001, "jack"));
        JSONObject jsonObject = JSON.parseObject(jsonString);
        jsonObject.getInteger("id"); //1001
        String[] strings = {"root", "dog", "animal"};
        List<String> stringList = Arrays.asList(strings);
        System.out.println(JSON.toJSONString(stringList));
        String str1 = "[\"id\", 123]";
        JSONArray jsonArray = JSON.parseArray(str1);
        System.out.println(jsonArray.getString(0));
        System.out.println(jsonArray.getIntValue(1));
    }

    /**
     * jsonString 转 JSONArray
     */
    public static void test001() {
        //将JavaBean对象生成JSON格式的字符串
        Product product = new Product(1001, "jack");
        JSON.toJSONString(product);//  {"id" : 1001,"name" : "DataWorks"}
        JSON.toJSONString(product, JSONWriter.Feature.BeanToArray); //  [1001, "DataWorks"]
        byte[] utf8JSONBytes = JSON.toJSONBytes(product);  //将JavaBean对象生成UTF8编码的byte[]


        //将JavaBean对象生成JSONB格式的byte[]
        byte[] jsonbBytes = JSONB.toBytes(product);
        byte[] toBytes = JSONB.toBytes(product, JSONWriter.Feature.BeanToArray);
        // 将JSONB数据读取成JavaBean
        JSONB.parseObject(jsonbBytes, Product.class);
        JSONB.parseObject(jsonbBytes, Product.class, JSONReader.Feature.SupportArrayToBean);

        //将UTF8编码的byte[]读取成JavaBean
        byte[] utf8Bytes = "{\"id\":123,\"name\":\"jack\"}".getBytes(StandardCharsets.UTF_8);
        JSON.parseObject(utf8Bytes, Product.class);

    }




}
