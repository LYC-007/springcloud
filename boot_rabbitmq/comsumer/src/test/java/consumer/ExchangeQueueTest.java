package consumer;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExchangeQueueTest {//创建交换机，队列和绑定关系测试;
    @Autowired
    AmqpAdmin amqpAdmin;//导入相关的starter就会导入“AmqpAdmin：管理组件”该组件可以用来创建交换机，队列，绑定关系，消费队列等等;
    @Test
    public void createExchange() {//创建一个直接类型的交换机
        Exchange directExchange = new DirectExchange("hello-java-exchange",true,false);
        amqpAdmin.declareExchange(directExchange);
        log.info("Exchange[{}]创建成功：","java-exchange");
    }
    @Test
    public void testCreateQueue() {//创建队列
        /**
         * 队列名字，是否持久化，是否排他(表示有一条连接连上了其他就不能连了)，是否自动删除
         */
        Queue queue = new Queue("hello-java-queue",true,false,false);
        amqpAdmin.declareQueue(queue);
        log.info("Queue[{}]创建成功：","java-queue");
    }

    /**
     *  String destination  目的地
     *  DestinationType destinationType  目的地的类型(交换机可以和交换机绑定，也可以和队列绑定)
     *  String exchange  交换机
     *  String routingKey  绑定关系
     *  Map<String, Object> arguments
     */
    @Test
    public void createBinding() {//创建绑定关系
        Binding binding = new Binding(
                "hello-java-queue",//目的地
                Binding.DestinationType.QUEUE,//目的地类型，有两种选择可以是队列也可以是交换机
                "hello-java-exchange",
                "hello.java",//路由键
                null//其他参数
        );
        amqpAdmin.declareBinding(binding);
        log.info("Binding[{}]创建成功：","hello-java-binding");

    }
}
