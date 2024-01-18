package app2;

import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.AbstractConsumerSeekAware;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.github.javafaker.Book;
import com.github.javafaker.Faker;

@SpringBootApplication
public class SpringKafkaApp2 {
    public static record Foo(String title, String author, String genere, String publisher) {};


    private static final Logger logger = LoggerFactory.getLogger(SpringKafkaApp2.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaApp2.class);
    }

    @Bean
    public NewTopic springKafkaApp2DemoTopic() {
        return TopicBuilder.name("spring-kafka-app2-demo")
            .partitions(3)
            .replicas(3)
            .build();
    }

    @Bean
    public ApplicationRunner runner(KafkaTemplate<String, Foo> kafkaTemplate) {
        Faker faker = Faker.instance();
        return args -> {
            for (int i = 0; i < 100; i++) {
                final Book book = faker.book();
                Foo foo = new Foo(book.title(), book.author(), book.genre(), book.publisher());
                kafkaTemplate.send("spring-kafka-app2-demo", foo.title(), foo);
                // Thread.sleep(100);
            }
        };
    }

    @KafkaListener(id = "sk-app2-demo-group", topics = "spring-kafka-app2-demo")
    public void listen(Foo in) {
        logger.info("Listener-1: Data Received: {}",  in);
    }
    
    @Component
    static class Listener extends AbstractConsumerSeekAware {

        @KafkaListener(id = "sk-app2-demo2-group", topics = "spring-kafka-app2-demo")
        public void listen2(Foo in, @Header(KafkaHeaders.RECEIVED_PARTITION)int partition,
        @Header(KafkaHeaders.OFFSET)int offset) {
            logger.info("Listerner-2: Data received: {} from partition {} and offset {}.", in, partition, offset);
        }

        @Override
        public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
            // logger.info("Assignments: {}", assignments);
            super.onPartitionsAssigned(assignments, callback);
            waitUntil(1000);
            callback.seekRelative("spring-kafka-app2-demo", 0, 20, false);
        }
        
        private void waitUntil(long t) {
            try {
                Thread.sleep(t);
            } 
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
