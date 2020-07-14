package com.batch.example.processor;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.batch.example.config.SpringBatchConfig;
import com.batch.example.model.Employee;
import com.batch.example.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Qualifier(value = SpringBatchConfig.ITEM_WRITER_TO_DATABASE)
public class DBWriter implements ItemWriter<Employee> {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void write(List<? extends Employee> list) throws Exception {
        this.employeeRepository.saveAll(list);
        log.info("Employees saved successfully {}", list);
    }

}
