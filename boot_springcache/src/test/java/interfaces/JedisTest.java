package interfaces;

import com.xufei.demo.utils.JedisPoolUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.io.UnsupportedEncodingException;
import java.util.Set;

public class JedisTest {
    private static final Jedis jedis = JedisPoolUtils.getJedisPoolInstance().getResource();

    @Test
    public void keyTest() throws UnsupportedEncodingException {
        System.out.println(jedis.flushDB());// 清空数据
        System.out.println(jedis.echo("hello"));

        // 判断key否存在
        System.out.println(jedis.exists("foo"));

        jedis.set("key", "values");
        jedis.set("key2", "values");
        System.out.println(jedis.exists("key"));// 判断是否存在

        // 如果数据库没有任何key，返回nil，否则返回数据库中一个随机的key。
        String randomKey = jedis.randomKey();
        System.out.println("randomKey: " + randomKey);

        // 设置60秒后该key过期
        jedis.expire("key", 60);

        // key有效毫秒数
        System.out.println(jedis.pttl("key"));

        // 移除key的过期时间
        jedis.persist("key");

        // 获取key的类型, "string", "list", "set". "none" none表示key不存在
        System.out.println("type: " + jedis.type("key"));

        // 导出key的值
        byte[] bytes = jedis.dump("key");
        System.out.println(new String(bytes));

        // 将key重命名
        jedis.renamenx("key", "keytest");
        System.out.println("key是否存在: " + jedis.exists("key"));// 判断是否存在
        System.out.println("keytest是否存在: " + jedis.exists("keytest"));// 判断是否存在

        // 查询匹配的key
        // KEYS       * 匹配数据库中所有 key 。
        // KEYS       h?llo 匹配 hello ， hallo 和 hxllo 等。
        // KEYS       h*llo 匹配 hllo 和 heeeeello 等。
        // KEYS       h[ae]llo 匹配 hello 和 hallo ，但不匹配 hillo 。
        // 特殊符号用 \ 隔开。
        Set<String> set = jedis.keys("k*");
        System.out.println(set);

        // 删除key
        jedis.del("key");
        System.out.println(jedis.exists("key"));
    }

}
