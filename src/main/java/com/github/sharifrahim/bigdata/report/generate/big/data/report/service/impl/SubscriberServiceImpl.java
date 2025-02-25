package com.github.sharifrahim.bigdata.report.generate.big.data.report.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.SubscriberEnum.ReportType;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.SubscriberEnum.Status;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.entity.Subscriber;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.repository.SubscriberRepository;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.service.SubscriberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * SubscriberServiceImpl provides the implementation for retrieving active subscribers
 * for daily transaction reports and daily transaction summary reports.
 * <p>
 * This service leverages the {@link SubscriberRepository} to query subscribers based on their
 * report type, subscription period, and status. The methods retrieve the current active subscribers
 * for both daily transaction reports and summary reports.
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
@Service
public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberRepository subscriberRepository;

    /**
     * Retrieves a list of subscribers who are currently active and subscribed to receive daily transaction reports.
     *
     * @return a list of active subscribers for daily transaction reports
     */
    @Override
    public List<Subscriber> findCurrentActiveSubscribersOfDailyTransactionReport() {
        LocalDate today = LocalDate.now();
        log.info("Fetching active subscribers for daily transaction reports on {}", today);
        List<Subscriber> subscribers = subscriberRepository.findByReportTypeAndReportPeriodAndStatus(
                ReportType.REPORT_DAILY_TRANSACTION, today, Status.ACTIVE);
        log.info("Found {} active subscribers for daily transaction reports", subscribers.size());
        return subscribers;
    }

    /**
     * Retrieves a list of subscribers who are currently active and subscribed to receive daily transaction summary reports.
     *
     * @return a list of active subscribers for daily transaction summary reports
     */
    @Override
    public List<Subscriber> findCurrentActiveSubscribersOfDailyTransactionSummaryReport() {
        LocalDate today = LocalDate.now();
        log.info("Fetching active subscribers for daily transaction summary reports on {}", today);
        List<Subscriber> subscribers = subscriberRepository.findByReportTypeAndReportPeriodAndStatus(
                ReportType.REPORT_DAILY_TRANSACTION_SUMMARY, today, Status.ACTIVE);
        log.info("Found {} active subscribers for daily transaction summary reports", subscribers.size());
        return subscribers;
    }
}
