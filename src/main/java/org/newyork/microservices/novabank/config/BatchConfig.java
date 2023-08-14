package org.newyork.microservices.novabank.config;

import org.newyork.microservices.novabank.batch.listeners.CheckingAccountTransactionsCompletionListener;
import org.newyork.microservices.novabank.batch.processors.CheckingAccountTransactionItemProcessor;
import org.newyork.microservices.novabank.batch.writers.CheckingAccountTransactionItemWriter;
import org.newyork.microservices.novabank.dto.TransactionDTO;
import org.newyork.microservices.novabank.entities.CheckingAccountTransactionEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private static final String TRANSACTION_ITEM_READER = "transactionItemReader";

    @Value("${batch-processing.transactions.inputs.file}")
    private String transactionsInputFile;

    @Bean
    public ConversionService transactionsConversionService() {
        final DefaultConversionService conversionService = new DefaultConversionService();
        DefaultConversionService.addDefaultConverters(conversionService);
        conversionService.addConverter(new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(final String text) {
                return LocalDateTime.parse(
                        text, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS]X")
                );
            }
        });
        return conversionService;
    }

    @Bean
    public FlatFileItemReader<TransactionDTO> transactionItemReader() {
        return new FlatFileItemReaderBuilder<TransactionDTO>()
                .name(TRANSACTION_ITEM_READER)
                .resource(new ClassPathResource(transactionsInputFile))
                .delimited()
                .names("operation", "operationDate", "amount", "accountNumber")
                .linesToSkip(1)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setConversionService(transactionsConversionService());
                    setTargetType(TransactionDTO.class);
                }})
                .build();
    }

    @Bean
    public Step importCheckingAccountTransactionsStep(
            final JobRepository jobRepository,
            final PlatformTransactionManager transactionManager,
            final CheckingAccountTransactionItemProcessor checkingAccountTransactionItemProcessor,
            final CheckingAccountTransactionItemWriter checkingAccountTransactionItemWriter
            ) {
        return new StepBuilder("importCheckingAccountTransactionStep", jobRepository)
                .<TransactionDTO, CheckingAccountTransactionEntity>chunk(1000, transactionManager)
                .reader(transactionItemReader())
                .processor(checkingAccountTransactionItemProcessor)
                .writer(checkingAccountTransactionItemWriter)
                .build();
    }

    @Bean
    public Job importCheckingAccountTransactionsJob(
            final JobRepository jobRepository,
            @Qualifier("importCheckingAccountTransactionsStep") final Step importCheckingAccountTransactionsStep,
            final CheckingAccountTransactionsCompletionListener checkingAccountTransactionsCompletionListener
    ) {
       return new JobBuilder("importCheckingAccountTransactionJob", jobRepository)
               .incrementer(new RunIdIncrementer())
               .listener(checkingAccountTransactionsCompletionListener)
               .flow(importCheckingAccountTransactionsStep)
               .end()
               .build();
    }


}
