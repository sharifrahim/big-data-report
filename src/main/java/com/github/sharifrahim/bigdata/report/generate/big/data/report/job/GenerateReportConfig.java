package com.github.sharifrahim.bigdata.report.generate.big.data.report.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.dto.ReportDailyTransactionDto;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.tasklet.GenerateDailyTransactionSummaryReportTasklet;

import lombok.extern.slf4j.Slf4j;

/**
 * GenerateReportConfig sets up the Spring Batch jobs and steps for generating daily transaction reports
 * and daily transaction summary reports.
 * <p>
 * This configuration class defines two jobs:
 * <ul>
 *   <li>{@code generateDailyTransactionReportJob} – Processes raw transaction data in chunks using
 *       a reader, processor, and writer.</li>
 *   <li>{@code generateDailyTransactionSummaryReportJob} – Executes a tasklet to generate the summary report.</li>
 * </ul>
 * </p>
 * <p>
 * For more details, please visit my GitHub repository:
 * <a href="https://github.com/sharifrahim">https://github.com/sharifrahim</a>
 * </p>
 * 
 * @author Sharif
 * @version 1.0
 * @since 2025
 */
@Slf4j
@Configuration
@EnableBatchProcessing
public class GenerateReportConfig {

    /**
     * Creates the Job bean for generating the daily transaction report.
     * <p>
     * This job consists of a single step that processes items in chunks.
     * </p>
     *
     * @param jobRepository the JobRepository used for persisting job metadata
     * @param generateDailyTransactionReportStep the step that processes the daily transaction report
     * @return a configured Job instance named "generateDailyTransactionReportJob"
     */
    @Bean
    public Job generateDailyTransactionReportJob(JobRepository jobRepository, Step generateDailyTransactionReportStep) {
        log.info("Creating Job 'generateDailyTransactionReportJob'");
        return new JobBuilder("generateDailyTransactionReportJob", jobRepository)
                .start(generateDailyTransactionReportStep)
                .build();
    }

    /**
     * Creates the Step bean for generating the daily transaction report.
     * <p>
     * This step uses chunk processing to read raw transaction data, process it, and write the processed data.
     * The chunk size is set to 10.
     * </p>
     *
     * @param jobRepository the JobRepository used for persisting step metadata
     * @param reader the ItemReader to read raw transaction data
     * @param processor the ItemProcessor to convert raw data to processed data
     * @param writer the ItemWriter to output the processed data
     * @param transactionManager the PlatformTransactionManager to manage transactions for chunk processing
     * @return a configured Step instance named "generateDailyTransactionReportStep"
     */
    @Bean
    public Step generateDailyTransactionReportStep(JobRepository jobRepository,
            ItemReader<ReportDailyTransactionDto.RawData> reader,
            ItemProcessor<ReportDailyTransactionDto.RawData, ReportDailyTransactionDto.ProcessData> processor,
            ItemWriter<ReportDailyTransactionDto.ProcessData> writer, PlatformTransactionManager transactionManager) {

        log.info("Creating Step 'generateDailyTransactionReportStep' with chunk size 10");
        return new StepBuilder("generateDailyTransactionReportStep", jobRepository)
                .<ReportDailyTransactionDto.RawData, ReportDailyTransactionDto.ProcessData>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    /**
     * Creates the Job bean for generating the daily transaction summary report.
     * <p>
     * This job executes a single step that uses a tasklet to generate the summary report.
     * </p>
     *
     * @param jobRepository the JobRepository used for persisting job metadata
     * @param generateDailyTransactionSummaryReportStep the step that executes the summary report tasklet
     * @return a configured Job instance named "generateDailyTransactionSummaryReportJob"
     */
    @Bean
    public Job generateDailyTransactionSummaryReportJob(JobRepository jobRepository,
            Step generateDailyTransactionSummaryReportStep) {
        log.info("Creating Job 'generateDailyTransactionSummaryReportJob'");
        return new JobBuilder("generateDailyTransactionSummaryReportJob", jobRepository)
                .start(generateDailyTransactionSummaryReportStep)
                .build();
    }

    /**
     * Creates the Step bean for generating the daily transaction summary report.
     * <p>
     * This step uses a tasklet to generate the summary report, with a listener attached for additional monitoring.
     * </p>
     *
     * @param jobRepository the JobRepository used for persisting step metadata
     * @param generateDailyTransactionSummaryReportTasklet the tasklet that contains the logic to generate the summary report
     * @param transactionManager the PlatformTransactionManager to manage the tasklet transaction
     * @return a configured Step instance named "generateDailyTransactionSummaryReportStep"
     */
    @Bean
    public Step generateDailyTransactionSummaryReportStep(JobRepository jobRepository,
            GenerateDailyTransactionSummaryReportTasklet generateDailyTransactionSummaryReportTasklet, PlatformTransactionManager transactionManager) {
        log.info("Creating Step 'generateDailyTransactionSummaryReportStep'");
        return new StepBuilder("generateDailyTransactionSummaryReportStep", jobRepository)
                .listener(generateDailyTransactionSummaryReportTasklet)
                .tasklet(generateDailyTransactionSummaryReportTasklet, transactionManager)
                .build();
    }
}
