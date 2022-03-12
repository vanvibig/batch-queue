package com.kv.batchqueue.controller;

import com.kv.batchqueue.batch.service.JobLauncherService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchController {

    private final JobLauncherService jobLauncherService;

    public BatchController(JobLauncherService jobLauncherService) {
        this.jobLauncherService = jobLauncherService;
    }

    @GetMapping("start-job")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void startJmsListener() {
        jobLauncherService.startJob();
    }
}
