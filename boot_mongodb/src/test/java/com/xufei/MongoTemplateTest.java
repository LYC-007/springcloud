package com.xufei;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.xufei.constant.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 保存到MongoDB中的文件有两种方式:
 *      1.MongoTemplate
 *      2.实现接口:MongoRepository<T,String>
 */
@SpringBootTest
public class MongoTemplateTest{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void contextLoads() {
        User user = new User();
        user.setAge(230);
        user.setName("Jak");
        user.setEmail("45423432220@qq.com");
        User user1 = mongoTemplate.insert(user);
        System.out.println(user1);
    }

    @Test
    void findAll() {
        List<User> userList = mongoTemplate.findAll(User.class);

        System.out.println(userList);
    }

    @Test
    void findUserById() {
        User user = mongoTemplate.findById("61375819ddefc0499d848b8d", User.class);
        System.out.println(user);
    }

    @Test
    void findUser() { //条件查询
        Query query = new Query(Criteria.where("name").is("test").and("age").is(20));
        List<User> users = mongoTemplate.find(query, User.class);
        System.out.println(users);
    }

    @Test
    void findLikeUser() {//模糊查询
        String name = "est";
        String regex = String.format("%s%s%s", "^.*", name, ".*$");
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));
        List<User> users = mongoTemplate.find(query, User.class);
    }

    //分页查询
    @Test
    public void findUsersPage() {
        String name = "est";
        int pageNo = 1;
        int pageSize = 10;

        Query query = new Query();
        String regex = String.format("%s%s%s", "^.*", name, ".*$");
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        query.addCriteria(Criteria.where("name").regex(pattern));
        int totalCount = (int) mongoTemplate.count(query, User.class);
        List<User> userList = mongoTemplate.find(query.skip((pageNo - 1) * pageSize).limit(pageSize), User.class);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("list", userList);
        pageMap.put("totalCount", totalCount);
        System.out.println(pageMap);
    }

    //修改
    @Test
    public void updateUser() {
        /**第一步：获取数据
         * 第二步：设置数据
         * 第三步：创建一个Update，对象
         *
         */
        User user = mongoTemplate.findById("61375819ddefc0499d848b8d", User.class);
        user.setName("test_1");
        user.setAge(25);
        user.setEmail("493220990@qq.com");
        Query query = new Query(Criteria.where("_id").is(user.getId()));
        Update update = new Update();
        update.set("name", user.getName());
        update.set("age", user.getAge());
        update.set("email", user.getEmail());
        UpdateResult result = mongoTemplate.upsert(query, update, User.class);
        long count = result.getModifiedCount();
        System.out.println(count);
    }

    //删除操作
    @Test
    public void delete() {
        Query query =
                new Query(Criteria.where("_id").is("5ffbfa2ac290f356edf9b5aa"));
        DeleteResult result = mongoTemplate.remove(query, User.class);
        long count = result.getDeletedCount();
        System.out.println(count);
    }
}
