package com.github.sharifrahim.bigdata.report.generate.big.data.report.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.TaskEnum.TaskType;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.entity.Task;

/**
 * TaskRepository provides data access operations for the {@link Task} entity.
 * <p>
 * In addition to the standard CRUD operations inherited from {@link JpaRepository},
 * this interface defines a custom query method to retrieve a Task based on its unique
 * reference, task type, and the subscriber's email.
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
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Retrieves a Task based on the given reference, task type, and subscriber email.
     * <p>
     * This method is useful for locating a specific task in the system using its unique reference,
     * the type of the task (as defined in {@link TaskType}), and the email of the subscriber who owns the task.
     * </p>
     *
     * @param reference the unique reference identifier for the task
     * @param type the type of the task as defined in the {@link TaskType} enum
     * @param subscriberEmail the email address of the subscriber associated with the task
     * @return an {@link Optional} containing the Task if found; otherwise, an empty {@link Optional}
     */
    Optional<Task> findByReferenceAndTypeAndSubscriberEmail(String reference, TaskType type, String subscriberEmail);
}
