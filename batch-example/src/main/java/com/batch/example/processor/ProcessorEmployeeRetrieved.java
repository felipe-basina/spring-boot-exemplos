package com.batch.example.processor;

import java.time.LocalDateTime;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.batch.example.model.Employee;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Qualifier(value = ProcessorEmployeeRetrieved.QUALIFIER)
public class ProcessorEmployeeRetrieved implements ItemProcessor<Employee, Employee> {

	public static final String QUALIFIER = "ProcessorEmployeeRetrieved";
	
    @Override
    public Employee process(Employee employee) throws Exception {
    	employee.setTimestamp(LocalDateTime.now().toString());
        log.info("Employee retrieved {}", employee);
        return employee;
    }

}
