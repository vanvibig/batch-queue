package com.kv.batchqueue.batch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class JobLauncherService {

    private final JobLauncher jobLauncher;
    private final Job mergeCsvIdJob;

    public JobLauncherService(JobLauncher jobLauncher, Job mergeCsvIdJob) {
        this.jobLauncher = jobLauncher;
        this.mergeCsvIdJob = mergeCsvIdJob;
    }

    public void startJob() {
        var date = LocalDateTime.now();
        var parameters = new JobParametersBuilder().addString("launchDate", date.format(DateTimeFormatter.ISO_DATE_TIME)).toJobParameters();

        try {
            var jobExecution = jobLauncher.run(mergeCsvIdJob, parameters);
            log.debug("Batch job ends with status as " + jobExecution.getStatus());
        } catch (Exception e) {
            log.error("Fail when run job", e);
        }
    }
}
