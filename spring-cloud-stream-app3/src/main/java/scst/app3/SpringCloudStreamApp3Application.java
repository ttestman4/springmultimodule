package scst.app3;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.github.javafaker.Book;
import com.github.javafaker.Faker;

@SpringBootApplication
public class SpringCloudStreamApp3Application {

    public record WordCount(String word, long count, Date start, Date end) {
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamApp3Application.class, args);
    }

    static final int WINDOW_SIZE_SECONDS = 30;

    @Bean
    public Function<KStream<Object, String>, KStream<Object, WordCount>> countWords() {
        return input -> input
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
                .map((key, value) -> new KeyValue<>(value, value))
                .groupByKey()
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(WINDOW_SIZE_SECONDS)))
                .count()
                .toStream()
                .map((key, value) -> new KeyValue<>(null, new WordCount(key.key(), value,
                        new Date(key.window().start()), new Date(key.window().end()))));
    }

    @Bean
    public Supplier<String> provideWords() {
        return () -> {
            Faker faker = Faker.instance();
            final Book book = faker.book();
            return String.join(", ", book.title(), book.author(), book.genre(), book.publisher());
        };
    }

    @Bean
    public Consumer<String> logWordCount() {
        return System.out::println;
    }

}
