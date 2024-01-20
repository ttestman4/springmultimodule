package scst.app5;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootApplication
public class SpringCloudStreamApp5Application {

    private AtomicInteger counter = new AtomicInteger(0);
    private final String istStr = "+05:30";
    private final String istNameStr = "IST";
    private final String estStr = "-06:00";
    private final String estNameStr = "EST";

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamApp5Application.class);
    }

    @Bean
    public Supplier<Message<String>> currentTime() {
        return () -> {
            String zone = istStr;
            String zoneName = istNameStr;
            if (counter.incrementAndGet() % 10 == 0) {
                zone = estStr;
                zoneName = estNameStr;
            }
            Long l = System.currentTimeMillis();
            return MessageBuilder.withPayload(Instant.ofEpochMilli(l).atZone(ZoneOffset.of(zone)).toString())
                    .setHeader(KafkaHeaders.KEY, zoneName)
                    .build();
        };
    }

    @Bean
    public Consumer<Message<String>> print() {
        return ist -> System.out.println("Current IST Time: " + ist.getPayload()
                + " received from partition " 
                + ist.getHeaders().get(KafkaHeaders.RECEIVED_PARTITION) + ":"
                + ist.getHeaders().get(KafkaHeaders.RECEIVED_KEY) + ":"
                + ist.getHeaders().get(KafkaHeaders.KEY));
    }

}