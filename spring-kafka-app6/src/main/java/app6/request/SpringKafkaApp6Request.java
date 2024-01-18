package app6.request;

import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@SpringBootApplication
public class SpringKafkaApp6Request {

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaApp6Request.class, args);
    }

    @RestController
    static class Producer {

        @Autowired
        @Lazy
        ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;

        // curl -X POST -H "Content-Type: text/plain" --data "hello request-reply in Spring for Apache Kafka" http://localhost:8086/publish/demo

        @PostMapping(path = "/publish/demo")
        public String publishDemo(@RequestBody String data) throws Exception {
            ProducerRecord<String, String> record = new ProducerRecord<String, String>("kRequests", data);
            RequestReplyFuture<String, String, String> replyFuture = replyingKafkaTemplate.sendAndReceive(record);
            SendResult<String, String> sendResult = replyFuture.getSendFuture().get(10, TimeUnit.SECONDS);
            System.out.println("Send OK: " + sendResult.getRecordMetadata());

            ConsumerRecord<String, String> consumerRecord = replyFuture.get(120, TimeUnit.SECONDS);
            System.out.println("Return value: " + consumerRecord.value());
            return consumerRecord.value();
        }

        @Bean
        public ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate(
                ProducerFactory<String, String> pf,
                ConcurrentMessageListenerContainer<String, String> repListenerContainer) {
            return new ReplyingKafkaTemplate<>(pf, repListenerContainer);
        }

        @Bean
        public ConcurrentMessageListenerContainer<String, String> repliesContainer(
            ConcurrentKafkaListenerContainerFactory<String, String> containerFactory
        ) {
            ConcurrentMessageListenerContainer<String, String> repListenerContainer =
            containerFactory.createContainer("kReplies");
            repListenerContainer.getContainerProperties().setGroupId("repliesGroup");        
            repListenerContainer.setAutoStartup(false);
            return repListenerContainer;
        }

    }

    static class Admin {
        @Bean
        public NewTopics springKafkaApp5Demo1Topic() {
            return new NewTopics(
                    TopicBuilder.name("kRequests")
                            .partitions(1)
                            .replicas(2)
                            .build(),
                    TopicBuilder.name("kReplies")
                            .partitions(1)
                            .replicas(2)
                            .build());
        }
    }
}
