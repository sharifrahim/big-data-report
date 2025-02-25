package com.github.sharifrahim.bigdata.report.generate.big.data.report.service;

import java.util.List;
import java.util.stream.Stream;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.dto.ReportDailyTransactionDto;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.dto.ReportDailyTransactionSummaryDto;

/**
 * TransactionService defines operations to retrieve transaction data for reporting purposes.
 * <p>
 * This service interface provides methods to:
 * <ul>
 *   <li>Retrieve a stream of raw transaction data for a specific merchant email.</li>
 *   <li>Retrieve a list of aggregated transaction summary data for a specific merchant email.</li>
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
public interface TransactionService {

    /**
     * Retrieves a stream of raw transaction data for the specified merchant email.
     * <p>
     * The returned stream consists of {@link ReportDailyTransactionDto.RawData} objects that encapsulate
     * individual transaction details such as payer information, transaction amount, currency, and date.
     * </p>
     *
     * @param merchantEmail the merchant's email used to filter the transactions
     * @return a stream of raw transaction data
     */
    Stream<ReportDailyTransactionDto.RawData> getTransactionsByMerchantEmail(String merchantEmail);

    /**
     * Retrieves a summary of transactions for the specified merchant email.
     * <p>
     * This method aggregates transactions for a given merchant and returns a list of
     * {@link ReportDailyTransactionSummaryDto.RawData} objects containing summarized information,
     * such as the total amount and currency for the day.
     * </p>
     *
     * @param merchantEmail the merchant's email used to filter and aggregate the transactions
     * @return a list of aggregated transaction summary data
     */
    List<ReportDailyTransactionSummaryDto.RawData> getTransactionsSummaryByMerchantEmail(String merchantEmail);
}
