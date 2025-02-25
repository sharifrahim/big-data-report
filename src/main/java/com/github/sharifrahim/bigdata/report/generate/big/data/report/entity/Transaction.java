package com.github.sharifrahim.bigdata.report.generate.big.data.report.entity;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Transaction represents a financial transaction between a payer and a merchant.
 * <p>
 * This entity captures the details of a single transaction, including:
 * <ul>
 *   <li>The payer's name and email address</li>
 *   <li>The merchant's email address</li>
 *   <li>The transaction amount and currency</li>
 *   <li>The date and time the transaction occurred</li>
 * </ul>
 * The transaction date is set to the current time upon creation.
 * </p>
 * <p>
 * For more information, please visit my GitHub repository:
 * <a href="https://github.com/sharifrahim">https://github.com/sharifrahim</a>
 * </p>
 * 
 * @author Sharif
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    /**
     * Unique identifier for the transaction.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the payer involved in the transaction.
     */
    @Column(name = "payer_name", nullable = false)
    private String payerName;

    /**
     * Email address of the payer.
     */
    @Column(name = "payer_email", nullable = false)
    private String payerEmail;

    /**
     * Email address of the merchant receiving the payment.
     */
    @Column(name = "merchant_email", nullable = false)
    private String merchantEmail;

    /**
     * The amount of the transaction.
     * Precision and scale are set to support values up to 10 digits with 2 decimal places.
     */
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    /**
     * The currency in which the transaction is conducted.
     */
    @Column(name = "currency", nullable = false, length = 10)
    private String currency;

    /**
     * The date and time when the transaction occurred.
     * This field is automatically set to the current time upon entity creation.
     */
    @Column(name = "transaction_date", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime transactionDate = LocalDateTime.now();
}

