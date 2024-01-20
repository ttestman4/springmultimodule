package com.example.restctrl;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.common.Employee;
import com.common.Fish;
import com.example.age.AgeBO;
import com.example.name.NameBO;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestController
public class Controller {

    private final KafkaTemplate<String, Employee> kafkaTemplate1;
    private final KafkaTemplate<String, Fish> kafkaTemplate2;

    private static Logger privateLOGGER = LoggerFactory.getLogger(Controller.class);

    private AtomicInteger seed1 = new AtomicInteger(0);
    private AtomicInteger seed2 = new AtomicInteger(0);

    @PostMapping("/generate/employee/{what}")
    public void sendEmployee(@PathVariable int what) {
        for (int i = 0; i < what; i++) {
            Employee emp = Employee.builder().EmpId("id" + seed1.incrementAndGet()).build();
            kafkaTemplate1.send("EmpNameIn", emp.EmpId(), emp);
        }
        privateLOGGER.debug("Employee {} generated", what);
    }

    @PostMapping("/generateOne/employee/{what}")
    public void sendOneEmployee(@PathVariable int what) {
        Employee emp = Employee.builder().EmpId("id" + what).build();
        kafkaTemplate1.send("EmpNameIn", emp.EmpId(), emp);
    }

    @PostMapping("/generate/fish/{what}")
    public void sendFish(@PathVariable int what) {
        for (int i = 0; i < what; i++) {
            Fish fish = Fish.builder().FishId("id" + seed2.incrementAndGet()).build();
            kafkaTemplate2.send("EmpNameIn", fish.FishId(), fish);
        }
        privateLOGGER.debug("Employee {} generated", what);
    }

    @PostMapping("/employees/name")
    public Employee postMethodName(@RequestBody Employee empIn) {

        return NameBO.nameBusinessLogic(empIn);
    }

    @PostMapping("/employees/age")
    public Employee postMethodAge(@RequestBody Employee empIn) {

        return AgeBO.ageBusinessLogic(empIn);
    }

}
