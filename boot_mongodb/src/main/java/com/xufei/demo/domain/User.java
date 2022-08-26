package com.xufei.demo.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("User") //在连接的数据库下创建一张"User"表，用来保存User相关的数据
@Data
// name和age将作为复合索引
// 数字参数指定索引的方向，1为正序，-1为倒序
@CompoundIndexes({
        @CompoundIndex(name = "name_age_idx", def = "{'name': 1, 'age': -1}")
})
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String createDate;
    private Integer age;
}

/**
 * @Document:把一个java类声明为mongodb的文档，可以通过collection参数指定这个类对应的文档，标注在实体类上，类似于hibernate的entity注解。
 *
 * @Id:文档的唯一标识，在mongodb中为ObjectId，它是唯一的，不可重复，自带索引，通过时间戳+机器标识+进程ID+自增计数器（确保同一秒内产生的Id不会冲突）构成。
 *
 * @Transient:映射忽略的字段，该字段不会保存到mongodb，只作为普通的javaBean属性。
 *
 * @Field:映射 mongodb中的字段名，可以不加，不加的话默认以参数名为列名。
 *
 * @Indexed:声明该字段需要索引，建索引可以大大的提高查询效率。
 *
 * @CompoundIndex:复合索引的声明，建复合索引可以有效地提高多字段的查询效率。
 *
 * @GeoSpatialIndexed:声明该字段为地理信息的索引。
 *
 * @DBRef:关联另一个document对象。类似于mysql的表关联，但并不一样，mongo不会做级联的操作。
 *
 * @Query:在MongoDB存储库方法上提供查询
 *
 */