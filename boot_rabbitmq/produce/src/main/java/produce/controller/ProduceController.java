package produce.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import produce.domain.UserDetail;

@RestController
@RequestMapping("/produce")
@Slf4j
public class ProduceController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message, @PathVariable String ttlTime) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration(ttlTime);
        Message reallyMessage = new Message(message.getBytes(), messageProperties);
        CorrelationData correlationData = new CorrelationData(); //为什么要设置这个类，配置类要回调，不设置，配置类什么也得不到;
        correlationData.setId("1");  //设置消息的id
        correlationData.setReturnedMessage(reallyMessage);
        log.info("正常发送了一个消息：{}", message);
        rabbitTemplate.convertAndSend("confirm.exchange", "key1", reallyMessage, correlationData);
    }

    /**
     * 线程安全有序的一个哈希表，适用于高并发的情况 (并发链路式队列 ConcurrentSkipListMap)
     * 1.轻松的将序号与消息进行关联
     * 2.轻松批量删除条目 只要给到序列号
     * 3.支持并发访问
     */
    @GetMapping("/send")//http://localhost:8006/produce/send
    public void sendMsg001() {
        UserDetail userDetail = new UserDetail();
        userDetail.setAge(14);
        userDetail.setDesc("我是一名小学生。");
        userDetail.setName("Jack");
        CorrelationData correlationData = new CorrelationData();
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration("1000");
        correlationData.setReturnedMessage(new Message(userDetail.toString().getBytes(), messageProperties));
        rabbitTemplate.convertAndSend("direct-jav-exchange", "hello.direct01", userDetail,correlationData);
    }
}
