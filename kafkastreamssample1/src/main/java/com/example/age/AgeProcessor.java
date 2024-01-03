package com.example.age;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.common.Employee;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class AgeProcessor {

    private AtomicInteger seed1 = new AtomicInteger(0);
    private final AgeBOFlux ageBOFlux;

    @Bean
    public KStream<?, ?> ageProcess(StreamsBuilder builder) {
        KStream<String, Employee> stream = builder.stream("EmpAgeIn",
                Consumed.with(Serdes.String(), new JsonSerde<>(Employee.class)));

        // stream.mapValues(AgeBO::ageBusinessLogic)
        // .to("EmpAddressIn", Produced.with(Serdes.String(), new
        // JsonSerde<>(Employee.class)));

        // stream.mapValues(emp -> ageBOFlux.ageBusinessLogic(emp))
        // .to("EmpAddressIn", Produced.with(Serdes.String(), new
        // JsonSerde<>(Employee.class)));

        stream.mapValues(ageBOFlux::ageBusinessLogic)
                .to("EmpAddressIn", Produced.with(Serdes.String(), new JsonSerde<>(Employee.class)));
        return stream;
    }

}