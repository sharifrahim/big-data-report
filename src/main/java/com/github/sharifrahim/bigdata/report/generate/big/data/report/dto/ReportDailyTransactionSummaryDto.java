package com.github.sharifrahim.bigdata.report.generate.big.data.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

/**
 * ReportDailyTransactionSummaryDto contains data transfer objects used for summarizing daily transaction data.
 * <p>
 * It provides two inner classes:
 * <ul>
 *   <li>{@link RawData} – Represents the raw summary data, including the merchant email,
 *       total amount, currency, and transaction date.</li>
 *   <li>{@link ProcessData} – Represents the processed summary data with formatted amount
 *       and date strings, ready for reporting.</li>
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
@Data
public class ReportDailyTransactionSummaryDto {

    /**
     * RawData represents the unprocessed summary of daily transactions for a merchant.
     * <p>
     * It includes the merchant's email, the total transaction amount, the currency,
     * and the date of the transactions.
     * </p>
     */
    @Data
    public static class RawData implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * The email address of the merchant.
         */
        private String merchantEmail;

        /**
         * The total amount of all transactions.
         */
        private BigDecimal totalAmount;

        /**
         * The currency in which the transactions are denominated.
         */
        private String currency;

        /**
         * The date of the transactions.
         */
        private LocalDate date;

        /**
         * Constructs a new RawData instance with the specified details.
         *
         * @param merchantEmail the merchant's email address
         * @param totalAmount   the total transaction amount
         * @param currency      the currency code
         * @param date          the transaction date
         */
        public RawData(String merchantEmail, BigDecimal totalAmount, String currency, LocalDate date) {
            this.merchantEmail = merchantEmail;
            this.totalAmount = totalAmount;
            this.currency = currency;
            this.date = date;
        }
    }

    /**
     * ProcessData represents the processed and formatted summary data for reporting.
     * <p>
     * It contains string representations of the merchant email, amount, and transaction date.
     * </p>
     */
    @Data
    public static class ProcessData implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * The email address of the merchant.
         */
        private String merchantEmail;

        /**
         * The formatted total amount as a string.
         */
        private String amount;

        /**
         * The formatted transaction date.
         */
        private String transactionDate;
    }
}
