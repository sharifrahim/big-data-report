package com.github.sharifrahim.bigdata.report.generate.big.data.report.entity;

import java.time.LocalDateTime;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.MainTaskEnum.MainTaskStatus;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.MainTaskEnum.MainTaskType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MainTask represents a primary task within the system.
 * <p>
 * This entity encapsulates details about a task, including its type, description, status,
 * and timestamps for scheduling, creation, and last update. The task type and status are stored
 * as enums and mapped as strings in the database.
 * </p>
 * <p>
 * The entity uses JPA lifecycle callbacks (@PrePersist and @PreUpdate) to automatically set
 * the creation and update timestamps.
 * </p>
 * <p>
 * For more information, please refer to my GitHub repository:
 * <a href="https://github.com/sharifrahim">https://github.com/sharifrahim</a>
 * </p>
 * 
 * @author Sharif
 * @version 1.0
 * @since 2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "main_tasks")
public class MainTask {

    /**
     * Unique identifier for the MainTask.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The type of the task (e.g., REPORT_DAILY_TRANSACTION, REPORT_DAILY_TRANSACTION_SUMMARY).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private MainTaskType type;

    /**
     * Detailed description of the task.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * The current status of the task (e.g., QUEUE, PROCESSING, COMPLETED, FAILED).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private MainTaskStatus status;

    /**
     * The scheduled time for the task execution.
     */
    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    /**
     * Timestamp indicating when the task was created.
     * This field is automatically set when the entity is persisted.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating when the task was last updated.
     * This field is automatically updated on entity updates.
     */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Sets the creation and update timestamps before persisting the entity.
     */
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Updates the update timestamp before the entity is updated.
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

