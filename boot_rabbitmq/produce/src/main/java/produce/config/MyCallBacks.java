package produce.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class MyCallBacks implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct   //MyCallBack这个对象创建完成后执行这个方法;
    public void init() {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setConfirmCallback(this); //注册交换机的回调接口
        rabbitTemplate.setReturnCallback(this);//注册交换机回退方法的接口
    }

    /**
     * 情形一：交换机收到消息：
     * 参数1；保存回调消息的ID及相关信心，如果你在发消息的时候没有设置消息的相关信息，那么你在这里什么也获取不到
     * 参数2：表示交换机收到消息 -->true
     * 参数3：null
     * <p>
     * <p>
     * 情形二：交换机没有收到消息：
     * 参数1；保存回调消息的ID及相关信心，如果你在发消息的时候没有设置消息的相关信息，那么你在这里什么也获取不到
     * 参数2：表示交换机未收到消息 -->false
     * 参数3：保存未收到的原因
     */
    @Override//交换机不管是否收到消息，生产者都要回调交换机的一个方法-->用来确认交换机收到消息，如果没收到消息将记录消息或者重新发送
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (ack) {
            log.info("交换机已经收到 id 为:{}的消息", id);
        } else {
            String message = new String(correlationData.getReturnedMessage().getBody());
            log.info("交换机还未收到 id 为:{}消息,由于原因:{},消息的内容是：{}", id, cause, message);
        }
    }

    /**
     * 只要消息没有投递给指定的队列，就触发这个失败回调
     * message：投递失败的消息详细信息
     * replyCode：回复的状态码
     * replyText：回复的文本内容
     * exchange：当时这个消息发给哪个交换机
     * routingKey：当时这个消息用哪个路邮键
     */
    @Override
//当交换机无法路由消息的时候的回调方法（交换机收到了消息但是没有可路由的队列时的回调接口），无法路由时交换机会回退该消息，如果不想回退可以设置备份交换机
    public void returnedMessage(Message message, int replyCode, String replyText, String
            exchange, String routingKey) {
        log.error(" 消 息 {}, 被交换机 {} 退回，退回原因 :{}, 路 由 key:{}", new String(message.getBody()), exchange, replyText, routingKey);
    }
}



