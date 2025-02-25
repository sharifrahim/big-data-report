package com.github.sharifrahim.bigdata.report.generate.big.data.report.constant;

/**
 * TaskEnum defines the enumerations for task types and statuses used in the application.
 * <p>
 * This class contains two enums:
 * <ul>
 *   <li>{@link TaskType} – Enumerates the types of tasks available in the system.</li>
 *   <li>{@link TaskStatus} – Enumerates the various statuses that a task can have.</li>
 * </ul>
 * </p>
 * <p>
 * For more details, please refer to my GitHub repository:
 * <a href="https://github.com/sharifrahim">https://github.com/sharifrahim</a>
 * </p>
 * 
 * @author Sharif
 * @version 1.0
 * @since 2025
 */
public class TaskEnum {

    /**
     * TaskType enumerates the types of tasks supported by the system.
     * <ul>
     *   <li>{@code REPORT_DAILY_TRANSACTION} – Represents a task for processing daily transaction reports.</li>
     *   <li>{@code REPORT_DAILY_TRANSACTION_SUMMARY} – Represents a task for processing daily transaction summary reports.</li>
     * </ul>
     */
    public enum TaskType {
        REPORT_DAILY_TRANSACTION,
        REPORT_DAILY_TRANSACTION_SUMMARY
    }
    
    /**
     * TaskStatus enumerates the possible statuses of a task.
     * <ul>
     *   <li>{@code QUEUE} – The task is queued for processing.</li>
     *   <li>{@code PROCESSING} – The task is currently being processed.</li>
     *   <li>{@code COMPLETED} – The task has completed successfully.</li>
     *   <li>{@code FAILED} – The task has failed to complete.</li>
     * </ul>
     */
    public enum TaskStatus {
        QUEUE,
        PROCESSING,
        COMPLETED,
        FAILED
    }
}
