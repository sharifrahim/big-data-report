package com.github.sharifrahim.bigdata.report.generate.big.data.report.tasklet;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.TaskEnum.TaskStatus;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.dto.ReportDailyTransactionSummaryDto;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.entity.Task;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.service.TaskService;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.service.TransactionService;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.util.CsvUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * GenerateDailyTransactionSummaryReportTasklet is responsible for generating the daily transaction summary report.
 * <p>
 * This tasklet performs the following operations:
 * <ol>
 *   <li>Before the step execution, it retrieves the main task using the provided job parameter "taskId" and sets
 *       its status to PROCESSING. It also fetches the merchant email from job parameters and retrieves the transaction
 *       summary list for that merchant via {@link TransactionService}.</li>
 *   <li>During execution, it writes the fetched transaction summary data to a CSV file using {@link CsvUtil}.
 *       (An additional step to copy the file to cloud storage can be implemented.)</li>
 *   <li>After the step execution, it updates the main task, setting the completed timestamp and marking its status
 *       as COMPLETED.</li>
 * </ol>
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
public class GenerateDailyTransactionSummaryReportTasklet implements org.springframework.batch.core.step.tasklet.Tasklet, StepExecutionListener {

    private final TransactionService transactionService;
    private final TaskService taskService;
    
    // Holds the transaction summary data for the merchant.
    private List<ReportDailyTransactionSummaryDto.RawData> transactionSummaryList;
    // The MainTask associated with this job execution.
    private Task task;
    
    /**
     * Called before the step execution to set up the necessary data.
     * <p>
     * This method retrieves the main task using the job parameter "taskId" and updates its status to PROCESSING.
     * It also retrieves the merchant email from job parameters, and if provided, fetches the transaction summary data.
     * </p>
     *
     * @param stepExecution the current step execution context
     */
    @Override
    public void beforeStep(StepExecution stepExecution) {
        // Retrieve task id from the Job Parameters.
        Long taskId = stepExecution.getJobParameters().getLong("taskId");
        log.info("BeforeStep: Retrieving MainTask with ID: {}", taskId);
        
        Optional<Task> taskOpt = taskService.getById(taskId);
        if (taskOpt.isPresent()) {
            task = taskOpt.get();
            task.setExecutedAt(LocalDateTime.now());
            task.setStatus(TaskStatus.PROCESSING);
            taskService.save(task);
            log.info("MainTask with ID {} set to PROCESSING", task.getId());
        } else {
            log.warn("No MainTask found for ID: {}", taskId);
        }
        
        // Retrieve merchant email from the Job Parameters.
        String merchantEmail = stepExecution.getJobParameters().getString("merchantEmail");
        log.info("Merchant email retrieved: {}", merchantEmail);
        
        if (merchantEmail == null || merchantEmail.isEmpty()) {
            log.warn("Merchant email is null or empty. Setting transaction summary list as empty.");
            this.transactionSummaryList = Collections.emptyList();
            return;
        }
        
        // Fetch the transaction summary list for the merchant.
        transactionSummaryList = transactionService.getTransactionsSummaryByMerchantEmail(merchantEmail);
        log.info("Fetched {} transaction summary records for merchant: {}", transactionSummaryList.size(), merchantEmail);
    }
    
    /**
     * Executes the tasklet.
     * <p>
     * This method writes the transaction summary data to a CSV file using {@link CsvUtil}.
     * (Placeholder for copying the file to cloud storage can be added.)
     * </p>
     *
     * @param contribution the step contribution context
     * @param chunkContext the chunk context containing step information
     * @return {@link RepeatStatus#FINISHED} once execution is complete
     * @throws Exception if an error occurs during execution
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        String fileName = "fileName.csv";  // Consider making this dynamic if needed.
        log.info("Executing tasklet: Writing transaction summary data to CSV file: {}", fileName);
        
        CsvUtil.writeToCsv(fileName, transactionSummaryList);
        log.info("CSV file {} written successfully", fileName);
        
        // TODO: Implement file copy to cloud storage.
        log.info("Initiating file copy to cloud storage for file: {}", fileName);
        
        return RepeatStatus.FINISHED;
    }
    
    /**
     * Called after the step execution to update the MainTask status.
     * <p>
     * This method updates the MainTask's createdAt timestamp and marks its status as COMPLETED.
     * </p>
     *
     * @param stepExecution the current step execution context
     * @return the exit status of the step execution
     */
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("AfterStep: Updating MainTask with ID {} to COMPLETED", task.getId());
        task.setCreatedAt(LocalDateTime.now());
        task.setStatus(TaskStatus.COMPLETED);
        taskService.save(task);
        log.info("MainTask with ID {} updated successfully", task.getId());
        return stepExecution.getExitStatus();
    }
}


