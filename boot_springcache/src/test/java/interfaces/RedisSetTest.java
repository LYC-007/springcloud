package interfaces;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.util.Arrays;

@SpringBootTest
public class RedisSetTest {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void test1(){
        //向键为set1的添加元素1（若没有该键，创建一个新的，并加入元素）
        redisTemplate.opsForSet().add("set1","1");
        //查询指定键的包含所有元素
        System.out.println(redisTemplate.opsForSet().members("set1"));
        //查询指定键的包含元素个数
        System.out.println(redisTemplate.opsForSet().size("set1"));
        //查询指定键是否有该元素
        System.out.println(redisTemplate.opsForSet().isMember("set1","1"));
        //指定键随机推出一个元素 并返回
        System.out.println(redisTemplate.opsForSet().pop("set1"));
        //移除指定键里面的指定元素
        redisTemplate.opsForSet().remove("set1","1","2");
        //将指定键的指定元素移动到指定键中
        redisTemplate.opsForSet().move("set1","1","set3");

        //获取两个集合的差集
        redisTemplate.opsForSet().difference("set1","set2").forEach(System.out::println);
        //获取两个集合的差集，并存入一个集合中
        redisTemplate.opsForSet().differenceAndStore("set1","set2","set3");
        //求指定键与另外一个集合的差集
        redisTemplate.opsForSet().difference("set1",Arrays.asList("1","2","3")).forEach(System.out::println);
        //求指定键与另外一个集合的差集，并存入一个集合中
        redisTemplate.opsForSet().differenceAndStore("set1", Arrays.asList("1","2","3"),"set3");

        //获取两个集合的交集
        redisTemplate.opsForSet().intersect("set1","set2").forEach(System.out::println);
        //获取两个集合的交集，并存入一个集合中
        redisTemplate.opsForSet().intersectAndStore("set1","set2","set3");
        //求指定键与另外一个集合的交集
        redisTemplate.opsForSet().intersect("set1",Arrays.asList("1","2","3"));
        //求指定键与另外一个集合的交集，并存入一个集合中
        redisTemplate.opsForSet().intersectAndStore("set1",Arrays.asList("1","2","3"),"set3");

        //获取两个集合的并集
        redisTemplate.opsForSet().union("set1","set2").forEach(System.out::println);
        //获取两个集合的并集，并存入一个集合中
        redisTemplate.opsForSet().unionAndStore("set1","set2","set3");
        //求指定键与另外一个集合的并集
        redisTemplate.opsForSet().union("set1",Arrays.asList("1","2","3")).forEach(System.out::println);
        //求指定键与另外一个集合的并集，并存入一个集合中
        redisTemplate.opsForSet().unionAndStore("set1",Arrays.asList("1","2","3"),"set3");


        //随机获取集合中的一个元素
        redisTemplate.opsForSet().randomMember("set1");
        //随机返回集合中指定数量的元素。随机的元素可能重复
        redisTemplate.opsForSet().randomMembers("set1",2);
        //随机返回集合中指定数量的元素。随机的元素不会重复
        redisTemplate.opsForSet().distinctRandomMembers("set1",2);
        //获取集合的游标。通过游标可以遍历整个集合
        Cursor<String> curosr = redisTemplate.opsForSet().scan("set1", ScanOptions.NONE);
        while(curosr.hasNext()){
            String item = curosr.next();
            System.out.println(item);
        }
    }
}
