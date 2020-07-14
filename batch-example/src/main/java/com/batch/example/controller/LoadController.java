package com.batch.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.batch.example.config.SpringBatchConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "${application.controller.load}")
public class LoadController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier(value = SpringBatchConfig.FROM_FILE_TO_DATABASE_QUALIFIER)
    private Job fromFileToDatabaseJob;

    @Autowired
    @Qualifier(value = SpringBatchConfig.FROM_DATABASE_TO_FILE_QUALIFIER)
    private Job fromDatabaseToFileJob;

    @GetMapping
    @RequestMapping(value = "${application.controller.fromFile}")
    public ResponseEntity loadFromFile() {
        try {
            JobExecution jobExecution = this.jobLauncher.run(this.fromFileToDatabaseJob, this.jobParameters());

            log.info("Batch is running...");
            while (jobExecution.isRunning()) {
                log.info(".....");
            }

            return ResponseEntity.ok().body(jobExecution.getStatus());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    @RequestMapping(value = "${application.controller.fromDatabase}")
    public ResponseEntity loadFromDatabase() {
        try {
            JobExecution jobExecution = this.jobLauncher.run(this.fromDatabaseToFileJob, this.jobParameters());

            log.info("Batch is running...");
            while (jobExecution.isRunning()) {
                log.info(".....");
            }

            return ResponseEntity.ok().body(jobExecution.getStatus());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private JobParameters jobParameters() {
        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("timestamp", new JobParameter(System.currentTimeMillis()));
        return new JobParameters(maps);
    }

}
