package org.newyork.microservices.novabank.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "/api/v1/batch")
@Log4j2
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job importCheckingAccountTransactionsJob;

    public BatchController(
            final JobLauncher jobLauncher,
            @Qualifier("importCheckingAccountTransactionsJob") final Job importCheckingAccountTransactionsJob
    ) {
        this.jobLauncher = jobLauncher;
        this.importCheckingAccountTransactionsJob = importCheckingAccountTransactionsJob;
    }

    @PostMapping(path = "/checking-accounts-transactions")
    public void startJob() {
        CompletableFuture.runAsync(() -> {
            final JobParameters jobParameters = new JobParametersBuilder()
                    .addString("time", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                    .toJobParameters();
            try {
                jobLauncher.run(importCheckingAccountTransactionsJob, jobParameters);
            } catch (JobExecutionAlreadyRunningException | JobParametersInvalidException |
                     JobInstanceAlreadyCompleteException | JobRestartException e) {
                throw new RuntimeException(e);
            }
        });
        log.info("Import checking accounts transactions job started");
    }

}
