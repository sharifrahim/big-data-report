package com.github.sharifrahim.bigdata.report.generate.big.data.report.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.dto.ReportDailyTransactionDto;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.dto.ReportDailyTransactionSummaryDto;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.repository.TransactionRepository;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.service.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * TransactionServiceImpl provides implementations for retrieving transaction data
 * for reporting purposes.
 * <p>
 * This service leverages the {@link TransactionRepository} to:
 * <ul>
 *   <li>Fetch raw transaction data for a given merchant email within the current day.</li>
 *   <li>Retrieve a summarized view of transactions for a given merchant email for the current day.</li>
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
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    /**
     * Retrieves a stream of raw transaction data for the specified merchant email.
     * <p>
     * This method fetches all transactions where the merchant email matches the provided value
     * and the transaction date is between the start of today (inclusive) and the start of tomorrow (exclusive).
     * </p>
     *
     * @param merchantEmail the merchant's email address used to filter transactions
     * @return a {@link Stream} of {@link ReportDailyTransactionDto.RawData} containing raw transaction data
     */
    @Transactional(readOnly = true)
    @Override
    public Stream<ReportDailyTransactionDto.RawData> getTransactionsByMerchantEmail(String merchantEmail) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime startOfNextDay = today.plusDays(1).atStartOfDay();
        log.info("Fetching transactions for merchant: {} between {} and {}", merchantEmail, startOfDay, startOfNextDay);
        return transactionRepository.findTransactionByMerchantEmailAndDate(merchantEmail, startOfDay, startOfNextDay);
    }

    /**
     * Retrieves a summarized list of transactions for the specified merchant email.
     * <p>
     * This method aggregates transactions for the current day for the given merchant,
     * grouping by merchant email and currency. It returns a list of
     * {@link ReportDailyTransactionSummaryDto.RawData} objects containing the summary data.
     * </p>
     *
     * @param merchantEmail the merchant's email address used to filter and aggregate transactions
     * @return a list of summarized transaction data
     */
    @Override
    public List<ReportDailyTransactionSummaryDto.RawData> getTransactionsSummaryByMerchantEmail(String merchantEmail) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime startOfNextDay = today.plusDays(1).atStartOfDay();
        log.info("Fetching transaction summary for merchant: {} for date: {} ({} to {})", merchantEmail, today, startOfDay, startOfNextDay);
        return transactionRepository.findDailySummaryByMerchantAndDate(merchantEmail, today, startOfDay, startOfNextDay);
    }
}
