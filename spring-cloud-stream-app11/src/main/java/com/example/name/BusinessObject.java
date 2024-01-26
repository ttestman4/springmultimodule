package com.example.name;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.common.Employee;


public class BusinessObject {
    private static Logger privateLOGGER = LoggerFactory.getLogger(BusinessObject.class);

    private static AtomicInteger seed = new AtomicInteger(0);

    // TBD Use @Value
    private static boolean enableException = true;


    public static Employee nameBusinessLogic(Employee empIn) {
        int seedValue = seed.incrementAndGet();
        if (enableException && "id5".equals(empIn.EmpId())) {
            privateLOGGER.info("Employee exception generated: {}", seedValue);
            throw new RuntimeException("Name Processing Exception");
        }

        return empIn.withFirstName("FirstName" + seedValue)
                .withLastName("LastName" + seedValue);
    }
}
