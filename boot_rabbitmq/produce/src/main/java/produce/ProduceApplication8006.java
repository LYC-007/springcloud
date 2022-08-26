package produce;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class ProduceApplication8006 {
    public static void main(String[] args) {
        SpringApplication.run(ProduceApplication8006.class,args);
    }
}
