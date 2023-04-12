package com.xufei.jackson.test;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class JacksonDatabindTest {
    /**
     * 将Json字符串转为Java对象
     */
    public void test5() throws Exception {
        String str = "{\"gender\":\"男\",\"name\":\"zhangsan\",\"age\":23}";//json字符串
        ObjectMapper mapper = new ObjectMapper();//Jackson核心对象
        Person person = mapper.readValue(str, Person.class);//使用readValue方法进行转换
    }

    /**
     * Java对象转Json
     */
    public void test1() throws IOException {
        Person p = new Person("张三", 23, "男", new Date()); //1.创建Java对象
        ObjectMapper mapper = new ObjectMapper();//2.创建Jackson对象 ObjectMapper
        String json = mapper.writeValueAsString(p);//3.转换为JSOn
        mapper.writeValue(new File("d:\\jaon.txt"), json);
        mapper.writeValue(new FileWriter("d:\\json.txt"), json);
    }

    /**
     * List转Json
     */
    public void test3() throws Exception {
        //复杂格式的转换：list
        Person p1 = new Person("张三", 23, "男", new Date()); //1.创建Java对象
        Person p2 = new Person("张三", 23, "男", new Date()); //1.创建Java对象
        List<Person> list = new ArrayList<>(2);
        list.add(p1);
        list.add(p2);
        //2.创建Jackson对象 ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(list);//3.转换为JSOn
        System.out.println(json);//[{"name":"张三","age":23,"gender":"男","birthday":"2021-03-19"},{"name":"张三","age":23,"gender":"男","birthday":"2021-03-19"}]

    }

    /**
     * Map转Json
     */
    public void test4() throws Exception {
        //复杂格式的转换Map
        //1.创建map对象
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zhangsan");
        map.put("age", 23);
        map.put("gender", "男");
        //2.创建Jackson对象 ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        //3.转换为JSOn
        String json = mapper.writeValueAsString(map);
        System.out.println(json);//{"gender":"男","name":"zhangsan","age":23}
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Person {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String name;

    @JsonIgnore//忽略该属性，不进行转换
    private int age;

    @JsonProperty("gender")
    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

}
