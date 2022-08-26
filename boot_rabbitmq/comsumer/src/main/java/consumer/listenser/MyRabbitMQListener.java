package consumer.listenser;

import com.rabbitmq.client.Channel;
import consumer.domain.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 监听消息的方式: @RabbitListener(queues ="队列名字")
 */

@Component
@Slf4j
public class MyRabbitMQListener { //通过监听器就收消息
    public static final String CONFIRM_QUEUE_NAME = "direct-java-queue";

    /**
     * 生产者的主启动类标注:@EnableRabbit注解开启功能;
     * 消费者接受消息的方法的参数可以写以下类型(只有消息完全处理完，方法运行结束，才可以接收下一个消息)：
     * 1).Message message :原生消息详细信息;
     * 2).T<发送消息的类型> :直接写当时发送消息的对象类型，Spring会自动给我们转化和封装;
     * 3).Channel channel :当前传输的通道;
     * 消费者:如果使用@RabbitListener监听消息，主启动类必须要有@EnableRabbit(@RabbitListener ：可以标在类和方法上)
     * 消费者:如果使用@RabbitHandler+@RabbitListener监听消息，主启动类也必须要有@EnableRabbit(@RabbitHandler标在方法上)
     */
    @RabbitListener(queues = CONFIRM_QUEUE_NAME)
    public void getDirectMessage(Channel channel, UserDetail userDetail, Message message) throws IOException {
        /**
         * 下面这种处理方式是:处理消息时发生异常，消息将重新入列，消息第二次消费时又出现异常消息将会被丢弃(如果配置了死信队列消息则进入死信队列)
         */
        log.info("【mq接收到的消息为：】,{}", userDetail.toString());
        try {
            // 模拟执行任务
            //Thread.sleep(1000);
            // 模拟异常
            //int i = 10 / 0;
            /**
             * 参数一:每一个消息都会有一个“DeliveryTag”，它是在“通道-Channel”内自增的
             * 参数二:确认收到消息，false只确认当前consumer一个消息收到，true批量确认所有consumer获得的消息
             */
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            if (message.getMessageProperties().getRedelivered()) { //判断消息是否  是重新入列的消息
                log.info("消息已重复处理失败,拒绝再次接收");
                // 拒绝消息，requeue=false 表示不再重新入队，如果配置了死信队列则进入死信队列

                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                log.info("消息即将再次返回队列处理");
                // 参数三：requeue为是否重新回到队列，true重新入队
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
            //e.printStackTrace();
        }
    }

}


