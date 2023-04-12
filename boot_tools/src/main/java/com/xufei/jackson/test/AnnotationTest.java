package com.xufei.jackson.test;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
/*
@JsonIgnoreProperties({ "id" }) 是一个类级别的注解，它标记了Jackson将忽略的一个属性或一组属性。
@JsonIgnore 注解用于在字段级别标记要忽略的属性。
@JsonInclude(Include.NON_NULL)  标注在类上或属性字段上，用来排除 empty/null/default 的属性。如果属性的值为"empty/null/default"做序列化的时候将被忽略
@JsonProperty 注解来表示JSON中的属性名。我们处理非标准的getter和setter时，让我们使用 @JsonProperty 来序列化/反序列化属性name:
@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")注解指定序列化 日期/时间 值时的格式。
 */

public class AnnotationTest {

    public static void main(String[] args) throws JsonProcessingException {
        HashSet<Integer> integers = new HashSet<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(integers));
    }
    /**
     * 如何禁用所有 Jackson 注解。 我们可以通过禁用 MapperFeature.USE_ANNOTATIONS 来做到这一点
     */
    public static void whenDisablingAllAnnotations_thenAllDisabled()
            throws IOException {
        MyBean bean = new MyBean(1, "许飞");
        JsonMapper mapper = JsonMapper.builder().disable(MapperFeature.USE_ANNOTATIONS).build();
        /**
         禁用前:{"id":1,"name":"许飞"}
         禁用后: {"id":1}
         */
        String result = mapper.writeValueAsString(bean);
        System.out.println(result);
    }

    /**
     * @JsonRootName(value = "user")    类注解
     * {"UserWithRoot":{"id":1,"name":"John"}} 变为 {"user":{"id":1,"name":"John"}}
     */
    public static void JsonRootNameAnnotation() throws JsonProcessingException {
        UserWithRoot user = new UserWithRoot(1, "John");
        String result = new ObjectMapper().enable(SerializationFeature.WRAP_ROOT_VALUE).writeValueAsString(user);
        System.out.println(result);
        User john = new User(1, new UserWithRoot(1, "John"));
        String string = new ObjectMapper().enable(SerializationFeature.WRAP_ROOT_VALUE).writeValueAsString(john);
        System.out.println(string);
    }

    /**
     * JsonCreator 作用反序列化一些与我们需要获取的目标实体不完全匹配的 JSON
     * 比如我们反序列化JSON : {"id":1,"theName":"My bean"} 但是，我们的目标实体中没有 theName 字段，只有一个 name 字段。
     * 现在我们不想更改实体本身，我们只需要通过使用 @JsonCreator 注解构造函数并同时使用 @JsonProperty 注解来对解组过程进行更多控制
     */
    public static void JsonCreatorAnnotation() throws JsonProcessingException, ParseException {
        String json = "{\"id\":1,\"theName\":\"My bean\"}";
        BeanWithCreator bean = new ObjectMapper()
                .readerFor(BeanWithCreator.class)
                .readValue(json);
        assertEquals("My bean", bean.name);
    }

    /**
     * @JacksonInject 表示属性将从注入而不是从 JSON 数据中获取其值。
     */
    public static void JacksonInjectAnnotation() throws IOException {

        String json = "{\"id\":\"222222\"}";//这里没有传入 name
        InjectableValues inject = new InjectableValues.Std().addValue(String.class, "我的值是被注入的不是从Json中获取的值！");
        BeanWithInject bean = new ObjectMapper().reader(inject)
                .forType(BeanWithInject.class)
                .readValue(json);
        assertEquals("My bean", bean.name);
        assertEquals(222222, bean.id);
    }

    /**
     * @JsonAnySetter 允许我们灵活地使用 Map 作为标准属性。 在反序列化时，来自 JSON 的属性将简单地添加到Map中。
     * {"name":"My bean","attr2":"val2","attr1":"val1"} Json中没有匹配到对象的属性将被放到 Map
     */
    public static void JsonAnySetterAnnotation() throws IOException {
        String json = "{\"name\":\"My bean\",\"attr2\":\"val2\",\"attr1\":\"val1\"}";
        ExtendableBean bean = new ObjectMapper().readerFor(ExtendableBean.class).readValue(json);
        //bean : ExtendableBean(name=My bean, properties={attr2=val2, attr1=val1})
    }

    /**
     * @JsonSetter是*@JsonProperty*的替代方法，它将该方法标记为setter方法。 当我们需要读取一些JSON数据，但目标实体类并不完全匹配该数据时，这是非常有用的，因此我们需要调优流程以使其适合。
     */
    public static void JsonSetterAnnotation() throws IOException {
        String json = "{\"id\":1,\"name\":\"My bean\"}";
        MyBean bean = new ObjectMapper()
                .readerFor(MyBean.class)
                .readValue(json);
    }

    /**
     * @JsonAlias 在反序列化期间为属性定义一个或多个可选名称。
     */
    public static void JsonAliasAnnotation() throws IOException {
        String json = "{\"fName\": \"John\", \"lastName\": \"Green\"}";
        AliasBean aliasBean = new ObjectMapper().readerFor(AliasBean.class).readValue(json);
        System.out.println(aliasBean);
    }

    /**
     * @JsonIgnoreType 标记要忽略的注解类型的所有属性。
     */
    public static void JsonIgnoreTypeAnnotation() throws JsonProcessingException, ParseException {
        Use.Name name = new Use.Name("John", "Doe");
        Use use = new Use(1, name);
        String result = new ObjectMapper().writeValueAsString(use);
        System.out.println(result);
    }

    /**
     * @JsonAutoDetect 可以覆盖哪些属性可见哪些不可见的默认语义
     */
    public static void JsonAutoDetectAnnotation() throws JsonProcessingException {
        PrivateBean bean = new PrivateBean(1, "My bean");
        String result = new ObjectMapper().writeValueAsString(bean);
        System.out.println(result);
    }

    /**
     * @JsonUnwrapped 定义了在序列化/反序列化时应该 解包/展平 的值。
     */
    public static void JsonUnwrappedAnnotation() throws JsonProcessingException {
        UnwrappedUser.Name name = new UnwrappedUser.Name("John", "Doe");
        UnwrappedUser user = new UnwrappedUser(1, name);
        String result = new ObjectMapper().writeValueAsString(user);
        // result = {"id":1,"firstName":"John","lastName":"Doe"}
        String str = "{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\"}";
        UnwrappedUser readValue = new ObjectMapper().readerFor(UnwrappedUser.class).readValue(str);
        //readValue = UnwrappedUser(id=1, name=UnwrappedUser.Name(firstName=John, lastName=Doe))
    }

    /**
     * @JsonManagedReference 和 @JsonBackReference 注解可以处理 父/子 关系并绕过循环。
     * 注意两个注解标注的位置
     */
    public static void JsonBackReferenceAnnotation() throws JsonProcessingException {
        //如果没有这两个注解将会报故障
        UserWithRef user = new UserWithRef(1, "John");
        ItemWithRef item = new ItemWithRef(2, "book", user);
        user.addItem(item);
        String result = new ObjectMapper().writeValueAsString(item); //{"id":2,"itemName":"book","owner":{"id":1,"name":"John"}}
        String asString = new ObjectMapper().writeValueAsString(user);//{"id":1,"name":"John"}
    }

    /**
     * @JsonIdentityInfo 表示对象标识应该在序列化/反序列化值时使用，例如，当处理无限递归类型的问题时。
     */
    public static void JsonIdentityInfoAnnotation()
            throws JsonProcessingException {
        UserWithIdentity user = new UserWithIdentity(1, "John");
        ItemWithIdentity item = new ItemWithIdentity(2, "book", user);
        ItemWithIdentity item1 = new ItemWithIdentity(5, "boocsk", user);
        user.addItem(item);
        String string = new ObjectMapper().writeValueAsString(user);//{"id":1,"name":"John","userItemsList":[{"id":2,"itemName":"book","userWithIdentity":1}]}
        String result = new ObjectMapper().writeValueAsString(item);//{"id":2,"itemName":"book","userWithIdentity":{"id":1,"name":"John","userItemsList":[2]}}
    }

    /**
     * @JsonFilter 指定了在序列化期间要使用的过滤器。
     */
    public static void JsonFilterAnnotation() throws JsonProcessingException {
        BeanWithFilter bean = new BeanWithFilter(1, "My bean");
        FilterProvider filters = new SimpleFilterProvider().addFilter("myFilter", SimpleBeanPropertyFilter.filterOutAllExcept("name"));//过滤调所有除了 "name",也就是只有name才能被序列化
        String result = new ObjectMapper()
                .writer(filters)
                .writeValueAsString(bean);//{"name":"My bean"}
        System.out.println(result);
    }

    /**
     * @JsonRawValue 注解可以指示Jackson按原样序列化属性。
     */
    public static void jsonRawValueAnnotation()
            throws JsonProcessingException {
        RawBean bean = new RawBean("My bean", "{\"attr\":false}");
        String result = new ObjectMapper().writeValueAsString(bean);
        //有@JsonRawValue 注解{"name":"My bean","attrs":{"attr":false}}
        //没有 @ JsonRawValue {"name":"My bean","attrs":"{\"attr\":false}"}
    }

}

@Data
@AllArgsConstructor
class RawBean {
    public String name;
    @JsonRawValue
    public String attrs;
}

@ToString
@JsonFilter("myFilter")
class BeanWithFilter {
    public int id;
    public String name;

    public BeanWithFilter(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

@ToString
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
class UserWithIdentity {
    public int id;
    public String name;
    public List<ItemWithIdentity> userItemsList = new ArrayList<>();

    public UserWithIdentity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addItem(ItemWithIdentity item) {
        userItemsList.add(item);
    }
}

@ToString
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
class ItemWithIdentity {
    public int id;
    public String itemName;
    public UserWithIdentity userWithIdentity;

    public ItemWithIdentity(int id, String itemName, UserWithIdentity owner) {
        this.id = id;
        this.itemName = itemName;
        this.userWithIdentity = owner;
    }
}


@AllArgsConstructor
class ItemWithRef {
    public int id;
    public String itemName;

    @JsonManagedReference
    public UserWithRef owner;
}

class UserWithRef {
    public int id;
    public String name;
    @JsonBackReference
    public List<ItemWithRef> userItems = new ArrayList<>();

    public UserWithRef(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addItem(ItemWithRef item) {
        userItems.add(item);
    }
}

@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
//注意这里的属性权限为 private 但是我没有提供Setter和Getter方法，有了这个注解我们还是可以序列化
class PrivateBean {
    private int id;
    private String name;
}

@Data
@AllArgsConstructor
class Use {
    public int id;
    public Name name;

    @JsonIgnoreType //标记要忽略的注解类型的所有属性，这里序列化的时候 Use中的 name将被忽略
    @AllArgsConstructor
    public static class Name {
        public String firstName;
        public String lastName;
    }
}

@ToString
@NoArgsConstructor
@AllArgsConstructor
class UnwrappedUser {
    public int id;

    @JsonUnwrapped//定义了在序列化/反序列化时应该 解包/展平 的值。
    public Name name;

    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Name {
        public String firstName;
        public String lastName;
    }
}

@Data
class AliasBean {
    @JsonAlias({"fName", "f_name"})
    private String firstName;
    private String lastName;
}

@ToString
class MyBean {
    public int id;
    private String name;

    public MyBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonSetter("name")
    public void setTheName(String name) {
        this.name = name;
    }
}

@ToString
class ExtendableBean {
    public String name;
    private final Map<String, String> properties = new HashMap<>();

    /**
     * 没有匹配上的反序列化属性，放到这里
     */
    @JsonAnySetter
    public void add(String key, String value) {
        properties.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, String> getOther() {
        return properties;
    }
}

class BeanWithInject {
    public int id;
    @JacksonInject
    public String name;
}

class BeanWithCreator {
    public int id;
    public String name;

    @JsonCreator //反序列化注解
    public BeanWithCreator(@JsonProperty("id") int id, @JsonProperty("theName") String name) {
        this.id = id;
        this.name = name;
    }
}

@JsonRootName(value = "user")
class UserWithRoot {
    public int id;
    public String name;

    public UserWithRoot(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class User {
    public int id;
    public UserWithRoot userWithRoot;

    public User(int id, UserWithRoot userWithRoot) {
        this.id = id;
        this.userWithRoot = userWithRoot;
    }
}
