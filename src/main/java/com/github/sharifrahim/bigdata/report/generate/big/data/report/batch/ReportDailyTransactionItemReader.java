package com.github.sharifrahim.bigdata.report.generate.big.data.report.batch;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.TaskEnum.TaskStatus;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.dto.ReportDailyTransactionDto;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.entity.Task;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.service.TaskService;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.service.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ReportDailyTransactionItemReader reads raw daily transaction data for report processing.
 * <p>
 * It retrieves job parameters to update the task status and to obtain the merchant email,
 * then lazily loads the transactions from the TransactionService based on that email.
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
public class ReportDailyTransactionItemReader implements ItemReader<ReportDailyTransactionDto.RawData> {

    private final TransactionService transactionService;
    private final TaskService taskService;
    
    // Iterator for the raw transaction data; lazily initialized.
    private Iterator<ReportDailyTransactionDto.RawData> transactionIterator;
    // Merchant email retrieved from job parameters.
    private String merchantEmail;
    
    /**
     * Prepares the reader before the step execution.
     * <p>
     * Retrieves the task ID and merchant email from job parameters,
     * updates the task status to PROCESSING, and sets the merchant email.
     * </p>
     *
     * @param stepExecution the current step execution context
     */
    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        // Retrieve taskId from job parameters
        Long taskId = stepExecution.getJobParameters().getLong("taskId");
        log.debug("Starting beforeStep for taskId: {}", taskId);
        
        // Retrieve task using taskService and update its status
        Optional<Task> taskOpt = taskService.getById(taskId);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setExecutedAt(LocalDateTime.now());
            task.setStatus(TaskStatus.PROCESSING);
            taskService.save(task);
            log.info("Updated task (id: {}) status to PROCESSING at {}", taskId, task.getExecutedAt());
        } else {
            log.warn("No task found for taskId: {}", taskId);
        }
        
        // Retrieve merchant email from job parameters for later use in read()
        this.merchantEmail = stepExecution.getJobParameters().getString("merchantEmail");
        log.debug("Merchant email set to: {}", merchantEmail);
    }

    /**
     * Reads the next raw transaction data item.
     * <p>
     * The iterator is lazily initialized by fetching all transactions for the given merchant email.
     * If no merchant email is provided or no data is available, returns null.
     * </p>
     *
     * @return the next ReportDailyTransactionDto.RawData item, or null if there are no more items
     * @throws Exception if any error occurs during data retrieval
     */
    @Override
    @Transactional(readOnly = true)
    public ReportDailyTransactionDto.RawData read()
            throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        // Lazy-load the iterator if it hasn't been initialized yet.
        if (transactionIterator == null) {
            if (merchantEmail == null || merchantEmail.isEmpty()) {
                log.warn("Merchant email is missing; no transactions to read.");
                transactionIterator = Collections.emptyIterator();
            } else {
                // Fetch transactions for the specified merchant email and collect into a List.
                List<ReportDailyTransactionDto.RawData> transactions = transactionService
                        .getTransactionsByMerchantEmail(merchantEmail)
                        .collect(Collectors.toList());
                log.info("Fetched {} transactions for merchant: {}", transactions.size(), merchantEmail);
                transactionIterator = transactions.iterator();
            }
        }
        
        // Return the next item if available, otherwise log the completion of data reading.
        if (transactionIterator.hasNext()) {
            ReportDailyTransactionDto.RawData nextItem = transactionIterator.next();
            log.debug("Returning next transaction item for merchant: {}", merchantEmail);
            return nextItem;
        } else {
            log.info("No more transaction items available for merchant: {}", merchantEmail);
            return null;
        }
    }
}

