package com.github.sharifrahim.bigdata.report.generate.big.data.report.constant;

/**
 * SubscriberEnum defines the enumerations for subscriber-related information,
 * including the types of reports and the subscriber status.
 * <p>
 * This class contains two enums:
 * <ul>
 *   <li>{@link ReportType} – specifies the types of reports that a subscriber may receive.</li>
 *   <li>{@link Status} – defines whether a subscriber is active or inactive.</li>
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
public class SubscriberEnum {

    /**
     * ReportType enumerates the available report types.
     * <ul>
     *   <li>{@code REPORT_DAILY_TRANSACTION} – Represents the daily transaction report.</li>
     *   <li>{@code REPORT_DAILY_TRANSACTION_SUMMARY} – Represents the summary report of daily transactions.</li>
     * </ul>
     */
    public enum ReportType {
        REPORT_DAILY_TRANSACTION, 
        REPORT_DAILY_TRANSACTION_SUMMARY
    }

    /**
     * Status enumerates the possible statuses of a subscriber.
     * <ul>
     *   <li>{@code ACTIVE} – Indicates that the subscriber is active.</li>
     *   <li>{@code INACTIVE} – Indicates that the subscriber is inactive.</li>
     * </ul>
     */
    public enum Status {
        ACTIVE,
        INACTIVE
    }
}
