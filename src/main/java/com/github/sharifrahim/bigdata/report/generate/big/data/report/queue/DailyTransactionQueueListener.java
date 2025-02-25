package com.github.sharifrahim.bigdata.report.generate.big.data.report.queue;

import java.time.LocalDateTime;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.TaskEnum.TaskStatus;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.TaskEnum.TaskType;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.dto.CreateTaskQueueMessageDto;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.entity.Task;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.service.TaskService;
import com.rabbitmq.client.Channel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * DailyTransactionQueueListener listens for messages from the daily transaction queue
 * and triggers the generation of a daily transaction report job.
 * <p>
 * Upon receiving a {@code CreateTaskQueueMessageDto} message, it:
 * <ul>
 *   <li>Retrieves the corresponding {@code Task} based on the message's reference, task type, and subscriber email.</li>
 *   <li>Updates the task's execution timestamp and status to PROCESSING.</li>
 *   <li>Builds job parameters and launches the daily transaction report job using the {@link JobLauncher}.</li>
 *   <li>If any exception occurs, the message is rejected and requeued.</li>
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
public class DailyTransactionQueueListener {

    private final JobLauncher jobLauncher;
    private final Job generateDailyTransactionReportJob;
    private final TaskService taskService;

    /**
     * Receives messages from the daily transaction queue and processes them.
     *
     * @param message    the incoming message containing task details
     * @param channel    the RabbitMQ channel for message acknowledgment
     * @param amqpMessage the raw AMQP message object
     * @throws Exception if an error occurs during message processing
     */
    @RabbitListener(queues = "#{@dailyTransactionQueueName}", containerFactory = "rabbitListenerContainerFactory")
    public void receiveMessage(CreateTaskQueueMessageDto message, Channel channel, Message amqpMessage)
            throws Exception {
        try {
            log.info("Received message with ID: {} for subscriber: {}", message.getMessageId(), message.getSubscriberEmail());

            // Retrieve the corresponding task using the message details.
            Task task = taskService.getByReferenceAndTaskTypeAndSubscriberEmail(
                    message.getMessageId(),
                    TaskType.valueOf(message.getTaskType()),
                    message.getSubscriberEmail()
            );

            if (task == null) {
                log.error("No task found for messageId: {}, taskType: {}, subscriberEmail: {}",
                        message.getMessageId(), message.getTaskType(), message.getSubscriberEmail());
                throw new Exception("Task not found");
            }

            // Update the task's execution timestamp and set its status to PROCESSING.
            task.setExecutedAt(LocalDateTime.now());
            task.setStatus(TaskStatus.PROCESSING);
            taskService.save(task);
            log.info("Task (ID: {}) updated to PROCESSING", task.getId());

            // Build job parameters with the task details.
            JobParameters parameters = new JobParametersBuilder()
                    .addLong("taskId", task.getId())
                    .addString("merchantEmail", task.getSubscriberEmail())
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            // Launch the daily transaction report job.
            jobLauncher.run(generateDailyTransactionReportJob, parameters);
            log.info("Launched job 'generateDailyTransactionReportJob' for task ID: {}", task.getId());

        } catch (Exception e) {
            log.error("Error processing message with ID: {}. Error: {}", message.getMessageId(), e.getMessage(), e);
            // Reject and requeue the message in case of errors.
            channel.basicNack(amqpMessage.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
