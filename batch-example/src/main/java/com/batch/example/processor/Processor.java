package com.batch.example.processor;

import com.batch.example.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class Processor implements ItemProcessor<Employee, Employee> {

    private static final Map<String, String> DEPARTMENT_NAMES = new HashMap<>();

    public Processor() {
        DEPARTMENT_NAMES.put("001", "Technology");
        DEPARTMENT_NAMES.put("002", "Operations");
        DEPARTMENT_NAMES.put("003", "Accounts");
    }

    @Override
    public Employee process(Employee employee) throws Exception {
        String department = DEPARTMENT_NAMES.get(employee.getDepartment());
        employee.setDepartment(department);
        log.info("Employee updated with department {}", employee);
        return employee;
    }

}
