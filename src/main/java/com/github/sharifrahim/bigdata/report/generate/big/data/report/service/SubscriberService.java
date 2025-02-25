package com.github.sharifrahim.bigdata.report.generate.big.data.report.service;

import java.util.List;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.entity.Subscriber;

/**
 * SubscriberService defines operations for retrieving active subscribers for daily transaction reports.
 * <p>
 * This service interface provides methods to fetch the list of subscribers who are currently active
 * for both the daily transaction report and the daily transaction summary report.
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
public interface SubscriberService {

    /**
     * Retrieves a list of subscribers that are currently active and subscribed to receive daily transaction reports.
     *
     * @return a list of active subscribers for daily transaction reports
     */
    List<Subscriber> findCurrentActiveSubscribersOfDailyTransactionReport();

    /**
     * Retrieves a list of subscribers that are currently active and subscribed to receive daily transaction summary reports.
     *
     * @return a list of active subscribers for daily transaction summary reports
     */
    List<Subscriber> findCurrentActiveSubscribersOfDailyTransactionSummaryReport();
}
