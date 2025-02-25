package com.github.sharifrahim.bigdata.report.generate.big.data.report.batch;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.TaskEnum.TaskStatus;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.dto.ReportDailyTransactionDto;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.entity.Task;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.service.TaskService;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.util.CsvUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ReportDailyTransactionItemWriter writes processed transaction data into a CSV file.
 * <p>
 * This writer generates a unique filename for each job execution based on the merchant’s email 
 * and the current timestamp. It then writes the provided chunk of processed data into the CSV file.
 * Additionally, after the step, it updates the associated task's status to COMPLETED.
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
public class ReportDailyTransactionItemWriter implements ItemWriter<ReportDailyTransactionDto.ProcessData> {

    private final TaskService taskService;

    // Static filename is generated once per job execution.
    private static String filename;

    /**
     * Writes a chunk of processed transaction data to a CSV file.
     * <p>
     * If the filename has not been generated yet, it is created using the merchant's email
     * (extracted from the first data item) and the current timestamp. The CSV file is then
     * populated with the processed data.
     * </p>
     *
     * @param chunk the chunk containing processed transaction data items
     * @throws Exception if an error occurs during the writing process
     */
    @Override
    public void write(Chunk<? extends ReportDailyTransactionDto.ProcessData> chunk) throws Exception {
        // Retrieve the list of processed data items from the chunk.
        List<ReportDailyTransactionDto.ProcessData> dataList = (List<ReportDailyTransactionDto.ProcessData>) chunk.getItems();
        
        log.debug("Received chunk with {} items to write.", dataList.size());

        // If no data is available, simply return.
        if (dataList.isEmpty()) {
            log.debug("Empty chunk received; nothing to write.");
            return;
        }

        // Generate a unique filename only once per job execution.
        if (filename == null) {
            String merchantEmail = dataList.get(0).getMerchantEmail();
            String merchantName = merchantEmail.split("@")[0]; // Extract merchant name from email (portion before '@')
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            filename = String.format("%s_%s.csv", merchantName, timestamp);
            log.info("Generated filename: {}", filename);
        }

        // Write the processed data to the CSV file using CsvUtil.
        CsvUtil.writeToCsv(filename, dataList);
        log.info("Successfully written {} records to CSV file: {}", dataList.size(), filename);

        // Placeholder for copying the file to cloud storage.
        log.info("Initiating file copy to cloud storage for file: {}", filename);
        // Example: CloudStorageUtil.copyToCloudStorage(filename);
    }

    /**
     * Updates the task status to COMPLETED after the step execution.
     * <p>
     * This method retrieves the job parameter 'taskId', fetches the corresponding Task, and updates its 
     * completion time and status.
     * </p>
     *
     * @param stepExecution the current step execution context
     * @return the exit status of the step
     */
    @AfterStep
    public ExitStatus afterStep(StepExecution stepExecution) {
        // Retrieve the 'taskId' job parameter.
        Long taskId = stepExecution.getJobParameters().getLong("taskId");
        log.debug("After step: updating task status for taskId: {}", taskId);

        // Retrieve the task and update its status if it exists.
        Optional<Task> taskOpt = taskService.getById(taskId);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setCompletedAt(LocalDateTime.now());
            task.setStatus(TaskStatus.COMPLETED);
            taskService.save(task);
            log.info("Task (id: {}) updated to COMPLETED at {}", taskId, task.getCompletedAt());
        } else {
            log.warn("Task not found for taskId: {}", taskId);
        }

        // Return the step's exit status.
        return stepExecution.getExitStatus();
    }
}
