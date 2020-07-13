package com.batch.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "${application.controller.load}")
public class LoadController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @GetMapping
    public ResponseEntity load() {
        try {
            JobExecution jobExecution = this.jobLauncher.run(this.job, this.jobParameters());

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
