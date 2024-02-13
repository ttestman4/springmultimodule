package com.example.rest;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.Employee;

@RestController
public class Controller {
    private static AtomicInteger seed = new AtomicInteger(0);

    @PostMapping("/employees/name")
    public Employee postMethodName(@RequestBody Employee empIn) {

        int seedValue = seed.incrementAndGet();
        return empIn.withFirstName("FirstName" + seedValue)
                .withLastName("LastName" + seedValue);
    }
}
