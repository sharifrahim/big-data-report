package com.github.sharifrahim.bigdata.report.generate.big.data.report.scheduler;

import java.time.LocalDateTime;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.MainTaskEnum.MainTaskStatus;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.MainTaskEnum.MainTaskType;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.SubscriberEnum.ReportType;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.entity.MainTask;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.service.MainTaskService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * EndOfDayScheduler is responsible for scheduling the execution of end-of-day report jobs.
 * <p>
 * This component runs at a fixed rate (every minute) and performs the following steps:
 * <ul>
 *   <li>Creates a new MainTask with status PENDING and type REPORT_EOD, and saves it using {@link MainTaskService}.</li>
 *   <li>Launches two job executions of the createReportJob:
 *       <ul>
 *           <li>The first execution uses the report type REPORT_DAILY_TRANSACTION.</li>
 *           <li>The second execution uses the report type REPORT_DAILY_TRANSACTION_SUMMARY.</li>
 *       </ul>
 *   </li>
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
@RequiredArgsConstructor
@Component
public class EndOfDayScheduler {

    private final JobLauncher jobLauncher;
    private final Job createReportJob;
    private final MainTaskService mainTaskService;

    /**
     * Schedules the historical fetch and report generation every minute.
     * <p>
     * This method creates a new MainTask record with status PENDING and type REPORT_EOD,
     * then launches two executions of the createReportJob with different report types to generate
     * the end-of-day reports.
     * </p>
     */
    @Scheduled(fixedRate = 60000) // 60000 milliseconds = 1 minute
    public void scheduleHistoricalFetch() {
        try {
            log.info("Starting scheduled end-of-day report generation at {}", LocalDateTime.now());

            // Create a new main task for end-of-day reporting.
            MainTask mainTask = new MainTask();
            mainTask.setStatus(MainTaskStatus.PENDING);
            mainTask.setType(MainTaskType.REPORT_EOD);
            mainTask.setScheduledAt(LocalDateTime.now());
            mainTaskService.save(mainTask);
            log.info("Created MainTask with ID {} and status PENDING", mainTask.getId());

            // Launch first job execution with report type REPORT_DAILY_TRANSACTION.
            JobParameters parameters1 = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .addLong("mainTaskId", mainTask.getId())
                    .addString("reportType", ReportType.REPORT_DAILY_TRANSACTION.name())
                    .toJobParameters();
            jobLauncher.run(createReportJob, parameters1);
            log.info("Launched createReportJob for REPORT_DAILY_TRANSACTION with MainTask ID {}", mainTask.getId());

            // Launch second job execution with report type REPORT_DAILY_TRANSACTION_SUMMARY.
            JobParameters parameters2 = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis() + 1) // Slight difference for uniqueness.
                    .addLong("mainTaskId", mainTask.getId())
                    .addString("reportType", ReportType.REPORT_DAILY_TRANSACTION_SUMMARY.name())
                    .toJobParameters();
            jobLauncher.run(createReportJob, parameters2);
            log.info("Launched createReportJob for REPORT_DAILY_TRANSACTION_SUMMARY with MainTask ID {}", mainTask.getId());

        } catch (Exception e) {
            log.error("Error during scheduled report generation: {}", e.getMessage(), e);
        }
    }
}
