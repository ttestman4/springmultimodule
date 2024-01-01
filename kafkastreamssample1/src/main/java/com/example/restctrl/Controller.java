package com.example.restctrl;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.common.Employee;
import com.example.age.AgeBO;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@RestController
public class Controller {

    private final KafkaTemplate<String, Employee> kafkaTemplate;
    private static Logger privateLOGGER = LoggerFactory.getLogger(Controller.class);

    private AtomicInteger seed = new AtomicInteger(0);

    @PostMapping("/generate/employee/{what}")
    public void sendEmployee(@PathVariable int what) {
        for (int i = 0; i < what; i++) {
            Employee emp = Employee.builder().EmpId("id" + seed.incrementAndGet()).build();
            kafkaTemplate.send("EmpNameIn", emp.EmpId(), emp);
        }
        privateLOGGER.debug("Employee {} generated", what);
    }

    @PostMapping("/employees/age")
    public Employee postMethodName(@RequestBody Employee empIn) {
        
        return AgeBO.ageBusinessLogic(empIn);
    }
    

}
