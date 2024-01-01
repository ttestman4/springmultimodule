package com.example.name;

import java.util.concurrent.atomic.AtomicInteger;

import com.common.Employee;

public class NameBO {
    private static AtomicInteger seed11 = new AtomicInteger(0);
    private static AtomicInteger seed12 = new AtomicInteger(0);

    public static Employee nameBusinessLogic(Employee empIn) {

        return empIn.withFirstName("FirstName" + seed11.incrementAndGet())
            .withLastName("LastName" + seed12.incrementAndGet());
    }
}
