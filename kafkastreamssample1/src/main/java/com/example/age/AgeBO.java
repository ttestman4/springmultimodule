package com.example.age;

import java.util.concurrent.atomic.AtomicInteger;


import org.springframework.stereotype.Component;

import com.common.Employee;

@Component
public class AgeBO {
    private static AtomicInteger seed1 = new AtomicInteger(0);

    public static Employee ageBusinessLogic(Employee empIn) {

        short age = (short) (seed1.incrementAndGet() % 110);

        // if( age == 5) {
        //     throw new RuntimeException("Age processing failed");
        // }

        return empIn.withAge(age);
    }

}
