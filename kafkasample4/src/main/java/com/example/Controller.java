package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class Controller {
    
    @Autowired
    private KafkaTemplate<Object, Object> template;

    @PostMapping("/send/{what}")
    public void sendFoo(@PathVariable String what) {
        this.template.send("topic4", what);        
    }
    
}
