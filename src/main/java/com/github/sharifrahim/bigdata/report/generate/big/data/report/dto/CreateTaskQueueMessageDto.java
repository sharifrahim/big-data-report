package com.github.sharifrahim.bigdata.report.generate.big.data.report.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * CreateTaskQueueMessageDto represents the payload for a task queue message.
 * <p>
 * This data transfer object (DTO) is used to encapsulate the information needed to create a task
 * in the queue, including a unique message identifier, task type, subscriber email, and the timestamp
 * when the message was created.
 * </p>
 * <p>
 * For more information, please visit my GitHub repository:
 * <a href="https://github.com/sharifrahim">https://github.com/sharifrahim</a>
 * </p>
 * 
 * @author Sharif
 * @version 1.0
 * @since 2025
 */
@Data
public class CreateTaskQueueMessageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unique identifier for the task queue message.
     */
    private String messageId;

    /**
     * The type of task to be created. This should match one of the defined task types.
     */
    private String taskType;

    /**
     * The email address of the subscriber associated with the task.
     */
    private String subscriberEmail;

    /**
     * The timestamp when the message was created. It is recommended to use ISO 8601 format.
     */
    private String timestamp;
}
