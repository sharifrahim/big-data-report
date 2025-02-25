package com.github.sharifrahim.bigdata.report.generate.big.data.report.entity;


import java.time.LocalDate;
import java.time.LocalDateTime;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.SubscriberEnum.ReportType;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.SubscriberEnum.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Subscriber represents a user who subscribes to reports.
 * <p>
 * This entity stores the subscriber's unique email, the type of report they receive,
 * subscription start and end dates, and their current status. Timestamps for when the record
 * was created and last updated are automatically maintained.
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
@Entity
@Table(name = "subscribers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscriber {

    /**
     * Unique identifier for the subscriber.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique email address of the subscriber.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * The type of report the subscriber receives.
     * Must correspond to one of the defined ReportType values.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", nullable = false, length = 50)
    private ReportType reportType;

    /**
     * The start date of the subscriber's reporting period.
     */
    @Column(name = "period_from", nullable = false)
    private LocalDate periodFrom; // Subscription start date

    /**
     * The end date of the subscriber's reporting period.
     */
    @Column(name = "period_to", nullable = false)
    private LocalDate periodTo;   // Subscription end date

    /**
     * The current status of the subscriber (e.g., ACTIVE, INACTIVE).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private Status status;

    /**
     * Timestamp indicating when the subscriber record was created.
     * This value is set at the time of creation and is not updated.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * Timestamp indicating when the subscriber record was last updated.
     */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    /**
     * Automatically updates the updatedAt field before updating the entity.
     */
    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}
