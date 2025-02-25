package com.github.sharifrahim.bigdata.report.generate.big.data.report.entity;

import java.time.LocalDateTime;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.TaskEnum.TaskStatus;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.TaskEnum.TaskType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Task represents an individual task within the system.
 * <p>
 * This entity encapsulates the details of a task, including a unique reference, associations to a MainTask,
 * task type, status, subscriber email, and various timestamps that track the task's lifecycle.
 * The timestamps for creation and last update are automatically managed via JPA lifecycle callbacks.
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
public class Task {

    /**
     * Unique identifier for the Task.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * A unique reference string for the task.
     */
    @Column(nullable = false, length = 255, unique = true)
    private String reference;

    /**
     * Association to the parent MainTask.
     * Uses lazy fetching to defer loading until needed.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_task_id", nullable = false)
    private MainTask mainTask;

    /**
     * The type of the task.
     * Should correspond to one of the defined TaskType values.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private TaskType type;

    /**
     * The current status of the task.
     * Should correspond to one of the defined TaskStatus values.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private TaskStatus status;
    
    /**
     * The subscriber's email associated with this task.
     */
    @Column(name = "subscriberEmail")
    private String subscriberEmail;

    /**
     * Timestamp indicating when the task was queued.
     */
    @Column(name = "queued_at")
    private LocalDateTime queuedAt;

    /**
     * Timestamp indicating when the task execution started.
     */
    @Column(name = "executed_at")
    private LocalDateTime executedAt;

    /**
     * Timestamp indicating when the task execution completed.
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    /**
     * Timestamp indicating when the task was created.
     * This field is set automatically during persistence.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating when the task was last updated.
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

