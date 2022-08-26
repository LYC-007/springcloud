package interfaces;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisStringTest {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void test1(){
        //存入数据  key存在就覆盖，不存在新增
        redisTemplate.opsForValue().set("name","lzj");
        //获取数据
        System.out.println(redisTemplate.opsForValue().get("name"));
        //获取多个数据
        System.out.println(redisTemplate.opsForValue().multiGet(Arrays.asList("lzj","lyx","3333")));
        //存入数据并且设置过期时间  key存在就覆盖，不存在新增
        redisTemplate.opsForValue().set("num","123",10, TimeUnit.SECONDS);
        //给指定键值 4 偏移量的位置开始替换内容
        redisTemplate.opsForValue().set("name","lzj",2);
        //设置键的字符串值并返回其旧值
        System.out.println(redisTemplate.opsForValue().getAndSet("name","lyx"));
        //给指定键 的值追加字符串   给对应的key追加value，key不存在直接新增
        redisTemplate.opsForValue().append("test","Hello");
        //存入数据 如果不存在则存入数据返回true 否则不覆盖数据返回false
        System.out.println(redisTemplate.opsForValue().setIfAbsent("lzj","1234"));
        //存入数据并设置过期时间 如果不存在则存入数据返回true 否则不覆盖数据返回false
        System.out.println(redisTemplate.opsForValue().setIfAbsent("lzj","1234",200,TimeUnit.SECONDS));
        //存入数据 如果存在键则覆盖数据 返回true 不存在则不作任何操作 返回false
        System.out.println(redisTemplate.opsForValue().setIfPresent("lyx","1234"));
        //存入数据并设置过期时间 如果存在键则覆盖数据 返回true 不存在则不作任何操作 返回false
        System.out.println(redisTemplate.opsForValue().setIfPresent("lyx","1234",200,TimeUnit.SECONDS));
    }

    @Test
    public void test2(){
        Map<String,String> map = new HashMap<>();
        map.put("1","123");
        map.put("2","123");
        //多个键值的插入
        redisTemplate.opsForValue().multiSet(map);
        //多个键值的插入 如果不存在则存入数据返回true 否则不覆盖数据返回false
        redisTemplate.opsForValue().multiSetIfAbsent(map);
    }

    @Test
    public void test3(){
        //返回键的值的长度
        System.out.println(redisTemplate.opsForValue().size("lzj"));
        System.out.println(redisTemplate.opsForValue().multiGet(Arrays.asList("lzj","lyx","3333")));
        //给指定键 加1如果值不是数字则抛出异常 不存在指定键创建一个初始为0的加1 增加成功则返回增加后的值
        System.out.println(redisTemplate.opsForValue().increment("lzj"));
        //给指定键 加指定整数如果值不是数字则抛出异常 不存在指定键创建一个初始为0的加指定整数 增加成功则返回增加后的值
        System.out.println(redisTemplate.opsForValue().increment("1",1));
        //给指定键 加指定浮点数如果值不是数字则抛出异常 不存在指定键创建一个初始为0的加指定浮点数 增加成功则返回增加后的值
        System.out.println(redisTemplate.opsForValue().increment("1",1.2));
        //给指定键 减1如果值不是数字则抛出异常 不存在指定键创建一个初始为0的减1 减少成功则返回增加后的值
        System.out.println(redisTemplate.opsForValue().decrement("1"));
        //给指定键 减指定整数如果值不是数字则抛出异常 不存在指定键创建一个初始为0的减指定整数 减少成功则返回增加后的值
        System.out.println(redisTemplate.opsForValue().decrement("1",3));
    }

}
