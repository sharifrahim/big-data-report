package com.github.sharifrahim.bigdata.report.generate.big.data.report.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.dto.ReportDailyTransactionDto;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.dto.ReportDailyTransactionSummaryDto;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.entity.Transaction;

/**
 * TransactionRepository provides custom data access operations for the {@link Transaction} entity.
 * <p>
 * In addition to the standard CRUD operations provided by {@link JpaRepository}, this interface
 * defines custom query methods to retrieve raw transaction data and a daily summary of transactions.
 * The queries construct new DTO instances (using JPQL constructor expressions) to map the results directly
 * to the required data transfer objects.
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
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Finds transactions for a given merchant email and date range.
     * <p>
     * This query selects transactions whose merchant email matches the provided value and whose
     * transaction date is between the specified start of the day (inclusive) and start of the next day (exclusive).
     * The query constructs a new {@code ReportDailyTransactionDto.RawData} instance for each matching transaction.
     * </p>
     *
     * @param merchantEmail the merchant email to filter transactions by
     * @param startOfDay    the start of the day (inclusive)
     * @param startOfNextDay the start of the next day (exclusive)
     * @return a {@link Stream} of {@code ReportDailyTransactionDto.RawData} objects containing raw transaction data
     */
    @Query("""
           SELECT new com.github.sharifrahim.bigdata.report.generate.big.data.report.dto.ReportDailyTransactionDto$RawData(
               t.id, t.payerName, t.payerEmail, t.merchantEmail, t.amount, t.currency, t.transactionDate)
           FROM Transaction t
           WHERE t.merchantEmail = :merchantEmail
             AND t.transactionDate >= :startOfDay
             AND t.transactionDate < :startOfNextDay
           """)
    Stream<ReportDailyTransactionDto.RawData> findTransactionByMerchantEmailAndDate(
            @Param("merchantEmail") String merchantEmail,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("startOfNextDay") LocalDateTime startOfNextDay);

    /**
     * Retrieves the daily summary of transactions for a given merchant and date.
     * <p>
     * This query aggregates transactions by merchant email and currency for a specific day.
     * It calculates the sum of the amounts and constructs a new {@code ReportDailyTransactionSummaryDto.RawData}
     * instance for each group. The provided {@code date} parameter is used to populate the summary date.
     * </p>
     *
     * @param merchantEmail the merchant email to filter transactions by
     * @param date          the reporting date (to be included in the DTO)
     * @param startOfDay    the start of the day (inclusive)
     * @param startOfNextDay the start of the next day (exclusive)
     * @return a list of {@code ReportDailyTransactionSummaryDto.RawData} objects containing aggregated transaction summaries
     */
    @Query("""
           SELECT new com.github.sharifrahim.bigdata.report.generate.big.data.report.dto.ReportDailyTransactionSummaryDto$RawData(
               t.merchantEmail,
               SUM(t.amount),
               t.currency,
               :date
           )
           FROM Transaction t
           WHERE t.merchantEmail = :merchantEmail
             AND t.transactionDate >= :startOfDay
             AND t.transactionDate < :startOfNextDay
           GROUP BY t.merchantEmail, t.currency
           """)
    List<ReportDailyTransactionSummaryDto.RawData> findDailySummaryByMerchantAndDate(
            @Param("merchantEmail") String merchantEmail,
            @Param("date") LocalDate date,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("startOfNextDay") LocalDateTime startOfNextDay);
}
