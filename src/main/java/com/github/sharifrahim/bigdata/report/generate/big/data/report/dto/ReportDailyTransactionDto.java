package com.github.sharifrahim.bigdata.report.generate.big.data.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * ReportDailyTransactionDto serves as a container for transaction data used in reporting.
 * <p>
 * It contains two inner classes:
 * <ul>
 *   <li>{@link RawData} – represents the raw transaction data as received.</li>
 *   <li>{@link ProcessData} – represents the processed/converted transaction data
 *       ready for reporting.</li>
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
@Data
public class ReportDailyTransactionDto {

    /**
     * RawData represents the raw details of a transaction.
     * <p>
     * It includes the transaction identifier, payer details, merchant email,
     * amount, currency, and the transaction timestamp.
     * </p>
     */
    @Data
    public static class RawData implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * Unique identifier of the transaction.
         */
        private long id;

        /**
         * Name of the payer.
         */
        private String payerName;

        /**
         * Email address of the payer.
         */
        private String payerEmail;

        /**
         * Email address of the merchant.
         */
        private String merchantEmail;

        /**
         * The amount involved in the transaction.
         */
        private BigDecimal amount;

        /**
         * The currency used for the transaction.
         */
        private String currency;

        /**
         * The date and time when the transaction occurred.
         */
        private LocalDateTime transactionDate;

        /**
         * Constructs a new RawData instance with the specified details.
         *
         * @param id               the transaction identifier
         * @param payerName        the name of the payer
         * @param payerEmail       the email address of the payer
         * @param merchantEmail    the email address of the merchant
         * @param amount           the transaction amount
         * @param currency         the currency used in the transaction
         * @param transactionDate  the timestamp of the transaction
         */
        public RawData(long id, String payerName, String payerEmail, String merchantEmail, BigDecimal amount,
                       String currency, LocalDateTime transactionDate) {
            this.id = id;
            this.payerName = payerName;
            this.payerEmail = payerEmail;
            this.merchantEmail = merchantEmail;
            this.amount = amount;
            this.currency = currency;
            this.transactionDate = transactionDate;
        }
    }

    /**
     * ProcessData represents the processed transaction data that will be used for reporting.
     * <p>
     * This class holds the payer's and merchant's information, the transaction amount
     * as a formatted string, and the transaction date in a desired format.
     * </p>
     */
    @Data
    public static class ProcessData implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * Name of the payer.
         */
        private String payerName;

        /**
         * Email address of the payer.
         */
        private String payerEmail;

        /**
         * Email address of the merchant.
         */
        private String merchantEmail;

        /**
         * The formatted transaction amount.
         */
        private String amount;

        /**
         * The formatted transaction date.
         */
        private String transactionDate;

        /**
         * Constructs a new ProcessData instance with the specified details.
         *
         * @param payerName       the name of the payer
         * @param payerEmail      the email address of the payer
         * @param merchantEmail   the email address of the merchant
         * @param amount          the formatted transaction amount
         * @param transactionDate the formatted transaction date
         */
        public ProcessData(String payerName, String payerEmail, String merchantEmail, String amount,
                           String transactionDate) {
            this.payerName = payerName;
            this.payerEmail = payerEmail;
            this.merchantEmail = merchantEmail;
            this.amount = amount;
            this.transactionDate = transactionDate;
        }
    }
}
