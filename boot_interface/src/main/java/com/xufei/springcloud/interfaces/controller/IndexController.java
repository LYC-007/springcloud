package com.xufei.springcloud.interfaces.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 幂等解决方案:
 * A、token 机制
 * 1、服务端提供了发送 token 的接口。我们在分析业务的时候，哪些业务是存在幂等问题的，就必须在执行业务前，先去获取 token，服务器会把 token 保存到 redis 中。
 * 2、然后调用业务接口请求时，把 token 携带过去，一般放在请求头部。
 * 3、服务器判断 token 是否存在 redis 中，存在表示第一次请求，然后删除 token,继续执行业务。
 * 4、如果判断 token 不存在 redis 中，就表示是重复操作，直接返回重复标记给 client，这样就保证了业务代码，不被重复执行。
 * 危险性：
 * 1、先删除 token 还是后删除 token；
 * (1) 先删除可能导致，业务确实没有执行，重试还带上之前 token，由于防重设计导致，请求还是不能执行。
 * (2) 后删除可能导致，业务处理成功，但是服务闪断，出现超时，没有删除 token，别人继续重试，导致业务被执行两边
 * (3) 我们最好设计为先删除 token，如果业务调用失败，就重新获取 token 再次请求。
 * 2、Token 获取、比较和删除必须是原子性
 * (1) redis.get(token) 、token.equals、redis.del(token)如果这两个操作不是原子，可能导致，高并发下，都 get 到同样的数据，判断都成功，继续业务并发执行
 * (2) 可以在 redis 使用 lua 脚本完成这个操作
 * if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end
 *
 *
 * B.各种锁机制
 * 1、数据库悲观锁
 * select * from xxxx where id = 1 for update;
 * 悲观锁使用时一般伴随事务一起使用，数据锁定时间可能会很长，需要根据实际情况选用。另外要注意的是，id 字段一定是主键或者唯一索引，不然可能造成锁表的结果，处理起来会非常麻烦。
 * 2、数据库乐观锁
 * 这种方法适合在更新的场景中，update t_goods set count = count -1 , version = version + 1 where good_id=2 and version = 1
 * 根据 version 版本，也就是在操作库存前先获取当前商品的 version 版本号，然后操作的时候带上此 version 号。
 * 我们梳理下，我们第一次操作库存时，得到 version 为 1，调用库存服务version 变成了 2；
 * 但返回给订单服务出现了问题，订单服务又一次发起调用库存服务，当订单服务传如的 version 还是 1，再执行上面的 sql 语句时，就不会执行；
 * 因为 version 已经变为 2 了，where 条件就不成立。这样就保证了不管调用几次，只会真正的处理一次。乐观锁主要使用于处理读多写少的问题
 * 3、业务层分布式锁
 * 如果多个机器可能在同一时间同时处理相同的数据，比如多台机器定时任务都拿到了相同数据处理，我们就可以加分布式锁，锁定此数据，处理完成后释放锁。获取到锁的必须先判断这个数据是否被处理过。
 *
 *
 * C、各种唯一约束
 * 1、数据库唯一约束
 * 插入数据，应该按照唯一索引进行插入，比如订单号，相同的订单就不可能有两条记录插入。我们在数据库层面防止重复。
 * 这个机制是利用了数据库的主键唯一约束的特性，解决了在 insert 场景时幂等问题。但主键的要求不是自增的主键，这样就需要业务生成全局唯一的主键。
 * 如果是分库分表场景下，路由规则要保证相同请求下，落地在同一个数据库和同一表中，要不然数据库主键约束就不起效果了，因为是不同的数据库和表主键不相关。
 * 2、redis set 防重
 * 很多数据需要处理，只能被处理一次，比如我们可以计算数据的 MD5 将其放入 redis 的 set，每次处理数据，先看这个 MD5 是否已经存在，存在就不处理。
 *
 *
 * D、防重表
 * 使用订单号 orderNo 做为去重表的唯一索引，把唯一索引插入去重表，再进行业务操作，且他们在同一个事务中。这个保证了重复请求时，因为去重表有唯一约束，导致请求失败，避免了幂等问题。
 * 这里要注意的是，去重表和业务表应该在同一库中，这样就保证了在同一个事务，即使业务操作失败了，也会把去重表的数据回滚。这个很好的保证了数据一致性。之前说的 redis 防重也算
 *
 *
 * E、全局请求唯一 id
 * 调用接口时，生成一个唯一 id，redis 将数据保存到集合中（去重），存在即处理过。
 * 可以使用 nginx 设置每一个请求的唯一 id；proxy_set_header X-Request-Id $request_id
 */
@RestController
public class IndexController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * RedisTemplate里面有一个方法execute可以用来执行lua脚本;
     * DefaultRedisScript(String script, @Nullable Class<T> resultType)
     * 参数说明:1.脚本代码;2.脚本执行后的放回结果;
     * public class DefaultRedisScript<T> implements RedisScript<T>, InitializingBean {.....}
     * <p>
     * <p>
     * execute(RedisScript<T> script, List<K> keys, Object... args)
     * 参数说明：1.见上;2.传入的key;3.传入要比较的值;
     */

    @GetMapping("/conform")
    public String conform(@RequestParam(value = "orderToken", required = false) String token) throws InterruptedException {
        //1、验证令牌是否合法【令牌的对比和删除必须保证原子性】  此段脚本说明：获取Redis中的key并比较对应的值，比较成功删除该值并返回“1”,获取失败或者比较失败返回“0”;
        DefaultRedisScript<Long> script =
                new DefaultRedisScript<>("if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end", Long.class);

        List<String> keys = Collections.singletonList("用户ID");//可以有多个key    List<String> keys = Arrays.asList(shortHashKey, longHashKey);

        //通过lure脚本原子验证令牌和删除令牌
        Long result = stringRedisTemplate.execute(script, keys, token);
        if (result == 0L) {//如果result为代表表单已经提交一次了;
            return "业务正在处理，请稍后...........";
        } else {
            //令牌验证成功
            TimeUnit.SECONDS.sleep(10);//模拟业务处理
            return "业务正常处理完成！！！！！！！！！";
        }
    }

    @GetMapping("/index")
    public ModelAndView index(ModelAndView modelAndView) {
        UUID uuid = UUID.randomUUID();
        String replace = uuid.toString().replace("-", "");

        //replace  存一份给Redis,发给前端一份
        stringRedisTemplate.opsForValue().set("用户ID", replace, 30, TimeUnit.SECONDS);

        modelAndView.setViewName("conform");
        modelAndView.addObject("orderToken", replace);
        return modelAndView;
    }

}
