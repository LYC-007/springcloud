package consumer;

import consumer.domain.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: XuFei
 * @Date: 2022/8/8 20:52
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProduceMsgTest {//收发送消息测试
    @Autowired
    RabbitTemplate rabbitTemplate; //RabbitTemplate：消息发送处理组件
    @Test
    public  void senMsg(){//发送消息测试
        String msg="你好，RabbitMQ。。。";
        rabbitTemplate.convertAndSend("hello-java-exchange","hello.java", msg);
        log.info("发送消息成功{}",msg);
    }

    @Test
    public  void senUserAsMsg(){//以流的方式发送对象消息---使用自带的序列化机制(该对象必须实现Serializable接口)
        UserDetail userDetail = new UserDetail();
        userDetail.setAge(14);
        userDetail.setDesc("我是一名小学生。");
        userDetail.setName("Jack");
        rabbitTemplate.convertAndSend("hello-java-exchange","hello.java",userDetail);
        log.info("发送消息成功{}",userDetail.toString());
    }
    @Test
    public  void senUserAsMsgJson(){//以Json的方式发送对象消息
        UserDetail userDetail = new UserDetail();
        userDetail.setAge(14);
        userDetail.setDesc("我是一名小学生。");
        userDetail.setName("Jack");
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend("hello-java-exchange","hello.java",userDetail);
        log.info("发送消息成功{}",userDetail.toString());
    }
}
