package com.example.restctrl1;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.common.Utility;
import com.example.age.AgeEnhancer;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class Controller {
    
    private final KafkaTemplate<Object, Object> kafkaTemplate;
    private final ObjectMapper jacksonObjectMapper;
    private static Logger privateLOGGER = LoggerFactory.getLogger(Controller.class);

    @PostMapping("/send/employee/{what}")
    public void sendEmployee(@PathVariable String what) {
        // kafkaTemplate.send("EmpNameIn", Employee.builder().EmpId(what).build());        
        
        Employee enhanedEmployee = Employee.builder().EmpId(what).build();
        String temp1;
        try {
            temp1 = jacksonObjectMapper.writeValueAsString(enhanedEmployee);
        } catch (Exception e) {
            privateLOGGER.info("JSON serialization Exception: ", e);
            temp1 = "{}";
        }


        kafkaTemplate.send("EmpNameIn", temp1);
    }
    
}
