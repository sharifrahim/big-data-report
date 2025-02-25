package com.github.sharifrahim.bigdata.report.generate.big.data.report.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.tasklet.CreateReportTasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * EndOfDayConfig sets up the Spring Batch job and step for creating end-of-day reports.
 * <p>
 * This configuration defines:
 * <ul>
 *   <li>A Job bean named "createReportJob" which starts with a single step.</li>
 *   <li>A Step bean named "createReportStep" that executes a {@code CreateReportTasklet} under
 *       transaction management.</li>
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
public class EndOfDayConfig {

    /**
     * Creates the Job bean for generating the end-of-day report.
     * <p>
     * The job is built using a {@link JobBuilder} and starts with the {@code createReportStep}.
     * </p>
     *
     * @param jobRepository   the JobRepository used to persist job metadata
     * @param createReportStep the step to be executed as part of the job
     * @return a configured Job instance named "createReportJob"
     */
    @Bean
    public Job createReportJob(JobRepository jobRepository, Step createReportStep) {
        log.info("Creating Job 'createReportJob'");
        return new JobBuilder("createReportJob", jobRepository)
                .start(createReportStep)
                .build();
    }

    /**
     * Creates the Step bean for executing the report creation task.
     * <p>
     * The step is built using a {@link StepBuilder} and executes the {@code CreateReportTasklet}
     * under the management of the provided {@link PlatformTransactionManager}.
     * </p>
     *
     * @param jobRepository          the JobRepository used to persist step metadata
     * @param createReportTasklet    the tasklet that contains the report generation logic
     * @param transactionManager     the PlatformTransactionManager used to manage transactions
     * @return a configured Step instance named "createReportStep"
     */
    @Bean
    public Step createReportStep(JobRepository jobRepository, CreateReportTasklet createReportTasklet,
                                 PlatformTransactionManager transactionManager) {
        log.info("Creating Step 'createReportStep'");
        return new StepBuilder("createReportStep", jobRepository)
                .tasklet(createReportTasklet, transactionManager)
                .build();
    }
}
