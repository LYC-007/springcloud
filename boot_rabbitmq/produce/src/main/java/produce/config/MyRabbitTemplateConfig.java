package produce.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;


//@Configuration
public class MyRabbitTemplateConfig {
    private RabbitTemplate rabbitTemplate;
    //@Primary//用@Primary标记liSiEmployee()bean。 Spring将优先于zhangSanEmployee()注入liSiEmployee()bean。
    //其作用与功能，当有多个相同类型的bean时，使用@Primary来赋予bean更高的优先级。
    //@Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setMessageConverter(messageConverter());
        initRabbitTemplate();
        return rabbitTemplate;
    }
    public MessageConverter messageConverter() {//发送的对象类型的消息，也可以是一个json，但是需要自己配置;
        return new Jackson2JsonMessageConverter();
    }
    /**
     * 定制RabbitTemplate
     * 1、服务收到消息就会回调
     * 1、spring.rabbitmq.publisher-confirms: true
     * 2、设置确认回调
     * 2、消息正确抵达队列就会进行回调
     * 1、spring.rabbitmq.publisher-returns: true
     * spring.rabbitmq.template.mandatory: true
     * 2、设置确认回调ReturnCallback
     * <p>
     * 3、消费端确认(保证每个消息都被正确消费，此时才可以broker删除这个消息)
     */
    public void initRabbitTemplate() {
        /**
         * 1、只要消息抵达Broker就ack=true,Broker里面可以有很多交换机
         * correlationData：当前消息的唯一关联数据(这个是消息的唯一id)
         * ack：消息是否成功收到
         * cause：失败的原因
         */
        //设置确认回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("confirm...correlationData[" + correlationData + "]==>ack:[" + ack + "]==>cause:[" + cause + "]");
        });
        /**
         * 只要消息没有从交换机投递给指定的队列，就触发这个失败回调
         * message：投递失败的消息详细信息
         * replyCode：回复的状态码
         * replyText：回复的文本内容
         * exchange：当时这个消息发给哪个交换机
         * routingKey：当时这个消息用哪个路邮键
         */
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("Fail Message[" + message + "]==>replyCode[" + replyCode + "]" +
                    "==>replyText[" + replyText + "]==>exchange[" + exchange + "]==>routingKey[" + routingKey + "]");
        });
    }
}
