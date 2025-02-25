package com.github.sharifrahim.bigdata.report.generate.big.data.report.tasklet;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.SubscriberEnum.ReportType;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.TaskEnum.TaskStatus;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.TaskEnum.TaskType;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.dto.CreateTaskQueueMessageDto;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.entity.MainTask;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.entity.Subscriber;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.entity.Task;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.service.MainTaskService;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.service.SubscriberService;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.service.TaskService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * CreateReportTasklet is a Spring Batch tasklet responsible for creating tasks and sending messages
 * to the appropriate RabbitMQ queues for daily transaction reports.
 * <p>
 * It retrieves job parameters to identify the main task and report type, then queries active subscribers,
 * creates corresponding tasks, and sends messages to the appropriate queue.
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
public class CreateReportTasklet implements org.springframework.batch.core.step.tasklet.Tasklet {

    private final MainTaskService mainTaskService;
    private final TaskService taskService;
    private final SubscriberService subscriberService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.queue.daily-transaction-report}")
    private String dailyReportQueueName;

    @Value("${spring.queue.daily-transaction-report-summary}")
    private String dailyReportSummaryQueueName;

    /**
     * Executes the tasklet to create tasks and send messages to RabbitMQ queues.
     * <p>
     * This method performs the following steps:
     * <ol>
     *   <li>Retrieves job parameters (mainTaskId and reportType).</li>
     *   <li>Fetches the corresponding {@link MainTask} and converts the reportType parameter into {@link ReportType}.</li>
     *   <li>Determines the appropriate subscriber list, task type, and queue name based on reportType.</li>
     *   <li>For each active subscriber, creates a new {@link Task} with status QUEUE and a unique reference,
     *       then sends a message to the corresponding RabbitMQ queue.</li>
     * </ol>
     * </p>
     *
     * @param contribution the step contribution context
     * @param chunkContext the chunk context containing step information
     * @return {@link RepeatStatus#FINISHED} upon successful execution
     * @throws Exception if any error occurs during processing
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // Retrieve StepExecution and JobParameters
        StepExecution stepExecution = ((StepContext) chunkContext.getStepContext()).getStepExecution();
        JobParameters jobParameters = stepExecution.getJobParameters();
        Long mainTaskId = jobParameters.getLong("mainTaskId");
        String reportTypeCode = jobParameters.getString("reportType");

        log.info("Tasklet execution started for MainTask ID: {} with ReportType: {}", mainTaskId, reportTypeCode);

        // Retrieve main task and convert reportType parameter
        MainTask mainTask = mainTaskService.findById(mainTaskId);
        ReportType reportType = ReportType.valueOf(reportTypeCode);
        log.debug("Retrieved MainTask: {}", mainTask);

        List<Subscriber> subscriberList = null;
        TaskType taskType = null;
        String queueName = null;

        // Determine subscribers, task type, and queue name based on report type.
        if (ReportType.REPORT_DAILY_TRANSACTION.equals(reportType)) {
            subscriberList = subscriberService.findCurrentActiveSubscribersOfDailyTransactionReport();
            taskType = TaskType.REPORT_DAILY_TRANSACTION;
            queueName = dailyReportQueueName;
            log.info("Report type is REPORT_DAILY_TRANSACTION. Using queue: {}", queueName);
        } else if (ReportType.REPORT_DAILY_TRANSACTION_SUMMARY.equals(reportType)) {
            subscriberList = subscriberService.findCurrentActiveSubscribersOfDailyTransactionSummaryReport();
            taskType = TaskType.REPORT_DAILY_TRANSACTION_SUMMARY;
            queueName = dailyReportSummaryQueueName;
            log.info("Report type is REPORT_DAILY_TRANSACTION_SUMMARY. Using queue: {}", queueName);
        } else {
            log.error("Unsupported report type: {}", reportType);
            throw new IllegalArgumentException("Unsupported report type: " + reportType);
        }

        // If no active subscribers, finish execution.
        if (CollectionUtils.isEmpty(subscriberList)) {
            log.warn("No active subscribers found for report type: {}", reportType);
            return RepeatStatus.FINISHED;
        }

        // Iterate over each subscriber, create a task and send a message to the queue.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (Subscriber eachSubscriber : subscriberList) {
            // Create and save task
            Task task = new Task();
            task.setMainTask(mainTask);
            task.setStatus(TaskStatus.QUEUE);
            task.setType(taskType);
            task.setQueuedAt(LocalDateTime.now());
            task.setReference(UUID.randomUUID().toString());
            task.setSubscriberEmail(eachSubscriber.getEmail());
            taskService.save(task);
            log.info("Created Task with reference: {} for subscriber: {}", task.getReference(), eachSubscriber.getEmail());

            // Build message DTO
            CreateTaskQueueMessageDto msg = new CreateTaskQueueMessageDto();
            msg.setMessageId(task.getReference());
            msg.setSubscriberEmail(task.getSubscriberEmail());
            msg.setTaskType(task.getType().name());
            msg.setTimestamp(task.getQueuedAt().format(formatter));
            log.debug("Built message DTO: {}", msg);

            // Post message to the appropriate queue.
            rabbitTemplate.convertAndSend(queueName, msg);
            log.info("Message sent to queue {} for Task reference: {}", queueName, task.getReference());
        }

        log.info("Tasklet execution completed successfully for MainTask ID: {}", mainTaskId);
        return RepeatStatus.FINISHED;
    }
}

