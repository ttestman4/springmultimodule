package app0;

import java.util.Map;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaKraftBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@EmbeddedKafka(topics = "spring-kafka-app0-demo1", bootstrapServersProperty = "spring.kafka.bootstrap-servers")
class SpringKafkaApp0Tests {
    
    @Autowired
    EmbeddedKafkaKraftBroker embeddedKafkaKraftBroker;

    @Test
    void test() {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("group", "false", embeddedKafkaKraftBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        DefaultKafkaConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
        Consumer<String,String> consumer = cf.createConsumer();
        embeddedKafkaKraftBroker.consumeFromEmbeddedTopics(consumer, "spring-kafka-app0-demo1");

        ConsumerRecords<String, String> cr = KafkaTestUtils.getRecords(consumer);

        assertThat(cr.isEmpty()).isFalse();
    }
}
