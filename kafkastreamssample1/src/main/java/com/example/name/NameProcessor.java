package com.example.name;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.common.Employee;

@Configuration
public class NameProcessor {

    @Bean
    public KStream<?, ?> nameProcess(StreamsBuilder builder) {
        KStream<String, Employee> stream = builder.stream("EmpNameIn",
                Consumed.with(Serdes.String(), new JsonSerde<>(Employee.class)));

        stream.mapValues( NameBO::nameBusinessLogic)
                .to("EmpAgeIn", Produced.with(Serdes.String(), new JsonSerde<>(Employee.class)));

        return stream;
    }

}