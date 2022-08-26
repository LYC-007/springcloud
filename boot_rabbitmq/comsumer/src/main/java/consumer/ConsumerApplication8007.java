package consumer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: XuFei
 * @Date: 2022/8/8 19:40
 */

@EnableRabbit
@SpringBootApplication
public class ConsumerApplication8007 {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication8007.class,args);
    }
}

