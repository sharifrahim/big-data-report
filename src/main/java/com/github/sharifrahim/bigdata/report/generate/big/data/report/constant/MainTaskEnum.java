package com.github.sharifrahim.bigdata.report.generate.big.data.report.constant;

/**
 * MainTaskEnum holds the enumeration definitions for main task types and statuses
 * used throughout the application.
 * <p>
 * This class defines two enums:
 * <ul>
 *   <li>{@link MainTaskType} – specifies the types of tasks, such as end-of-day and end-of-month reports.</li>
 *   <li>{@link MainTaskStatus} – specifies the status of a task (pending, completed, or failed).</li>
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
public class MainTaskEnum {

    /**
     * MainTaskType defines the types of main tasks available in the application.
     * <p>
     * <ul>
     *   <li>{@code REPORT_EOD} – Represents an end-of-day report task.</li>
     *   <li>{@code REPORT_EOM} – Represents an end-of-month report task.</li>
     * </ul>
     * </p>
     */
    public enum MainTaskType {
        REPORT_EOD,
        REPORT_EOM
    }
    
    /**
     * MainTaskStatus defines the possible statuses for a main task.
     * <p>
     * <ul>
     *   <li>{@code PENDING} – The task is pending execution.</li>
     *   <li>{@code COMPLETED} – The task has completed successfully.</li>
     *   <li>{@code FAILED} – The task has encountered an error and failed.</li>
     * </ul>
     * </p>
     */
    public enum MainTaskStatus {
        PENDING,
        COMPLETED,
        FAILED
    }
}

