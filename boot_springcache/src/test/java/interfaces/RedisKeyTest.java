package interfaces;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisKeyTest {
    //因为redistempalte默认装配了泛型为<Object,Object>的redistemplate,
    //所以<String,String>也是可以注入的，因为string是object派生类 。
    //如果需要要<String,Object>的redistemplate则需要自己装配
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void test(){
        //给指定键设置过期时间
        redisTemplate.expire("lzj",12, TimeUnit.SECONDS);
        //删除指定键
        redisTemplate.delete("lzj");
        //查找 指定的 键
        redisTemplate.keys("*");
        //判断是否存在键值
        redisTemplate.hasKey("lzj");
        //获取过期时间
        redisTemplate.getExpire("lzj");
        //获取指定格式的过期时间
        redisTemplate.getExpire("lzj",TimeUnit.SECONDS);
        //获取当前传入的key的值序列化为byte[]类型
        redisTemplate.dump("lzj");
        //修改指定键的名字  如果该键不存在则报错
        redisTemplate.rename("lzj","lyx");
        //旧值存在时，将旧值改为新值
        redisTemplate.renameIfAbsent("lzj","lyx");
        //获取指定键的类型
        redisTemplate.type("lzj");
        //将指定的键移动到指定的库中
        redisTemplate.move("lzj",2);
        //随机取一个key
        redisTemplate.randomKey();
        //将key持久化保存 就是把过期或者设置了过期时间的key变为永不过期
        redisTemplate.persist("lzj");
    }

}
