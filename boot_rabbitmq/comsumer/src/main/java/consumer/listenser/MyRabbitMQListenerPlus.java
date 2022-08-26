package consumer.listenser;

import com.rabbitmq.client.Channel;
import consumer.domain.Student;
import consumer.domain.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;

import java.io.IOException;

/**
 * 监听方式: @RabbitListener+@RabbitHandler
 * 该监听方式适合用来监听同一个队列发送不同类型的消息
 */





//@RabbitListener(queues ="CONFIRM_QUEUE_NAME_PLUS")
//@Component
@Slf4j
public class MyRabbitMQListenerPlus { //通过监听器就收消息
    /**
     * 生产者的主启动类标注:@EnableRabbit注解开启功能;
     * 消费者接受消息的方法的参数可以写以下类型(只有消息完全处理完，方法运行结束，才可以接收下一个消息)：
     *      1).Message message :原生消息详细信息;
     *      2).T<发送消息的类型> :直接写当时发送消息的对象类型，Spring会自动给我们转化和封装;
     *      3).Channel channel :当前传输的通道;
     * 消费者:如果使用@RabbitListener监听消息，主启动类必须要有@EnableRabbit(@RabbitListener ：可以标在类和方法上)
     * 消费者:如果使用@RabbitHandler+@RabbitListener监听消息，主启动类也必须要有@EnableRabbit(@RabbitHandler标在方法上)
     *
     */
    //@RabbitHandler
    public void getDirectMessage(Channel channel, UserDetail userDetail, Message message) throws IOException {
        log.info("【mq接收到的消息为：】,{}",new String(message.getBody()));
    }
    //@RabbitHandler
    public void getDirectMessage(Channel channel, Student student, Message message) throws IOException {
        log.info("【mq接收到的消息为：】,{}",new String(message.getBody()));
    }
}


