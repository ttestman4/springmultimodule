package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import com.common.Foo1;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class Controller {
    
    @Autowired
    private KafkaTemplate<Object, Object> template;

    @PostMapping("/send/foos/{what}")
    public void sendFoo(@PathVariable String what) {
        this.template.executeInTransaction(KafkaTemplate -> {
            StringUtils.commaDelimitedListToSet(what).stream()
                .map(s -> new Foo1(s))
                .forEach(foo -> KafkaTemplate.send("topic2", foo));
            return null;
        });
    }
    
}
