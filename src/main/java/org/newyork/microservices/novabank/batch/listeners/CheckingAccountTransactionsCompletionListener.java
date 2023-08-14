package org.newyork.microservices.novabank.batch.listeners;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Log4j2
public class CheckingAccountTransactionsCompletionListener implements JobExecutionListener {

    @Override
    public void beforeJob(final JobExecution jobExecution) {
        log.info("Starting checking account transactions job at: {}", LocalDateTime.now());
    }

    @Override
    public void afterJob(final JobExecution jobExecution) {
        log.info(
                "Checking account transactions job done at: {} with status: {}",
                LocalDateTime.now(),
                jobExecution.getStatus()
        );
    }
}
