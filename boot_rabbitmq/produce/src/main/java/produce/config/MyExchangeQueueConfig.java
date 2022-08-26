package produce.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author: XuFei
 * @Date: 2022/8/8 20:01
 */
@Configuration
public class MyExchangeQueueConfig {
    @Bean("directExchange")
    public DirectExchange directExchange(){

        return new DirectExchange("direct-java-exchange",true,false);
    }

    @Bean("directQueue")
    public Queue  directQueue(){


        return new Queue("direct-java-queue",true,false,false);
    }
    @Bean
    public Binding directBinding(@Qualifier("directQueue") Queue queue,
                                 @Qualifier("directExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("hello.direct");
    }

}
