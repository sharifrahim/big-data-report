package com.github.sharifrahim.bigdata.report.generate.big.data.report.service;

import java.util.Optional;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.TaskEnum.TaskType;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.entity.Task;

/**
 * TaskService defines operations for managing {@link Task} entities.
 * <p>
 * This service interface provides methods to:
 * <ul>
 *   <li>Retrieve a Task by its unique identifier.</li>
 *   <li>Persist a Task entity.</li>
 *   <li>Retrieve a Task based on its reference, task type, and subscriber email.</li>
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
public interface TaskService {

    /**
     * Retrieves a Task by its unique identifier.
     *
     * @param id the unique identifier of the Task
     * @return an {@link Optional} containing the Task if found, otherwise an empty Optional
     */
    Optional<Task> getById(Long id);

    /**
     * Persists the given Task entity.
     *
     * @param task the Task entity to save
     */
    void save(Task task);

    /**
     * Retrieves a Task based on its reference, task type, and subscriber email.
     * <p>
     * This method is useful for locating a specific Task using a unique reference
     * along with its type and the email of the associated subscriber.
     * </p>
     *
     * @param ref the unique reference of the Task
     * @param taskType the type of the Task as defined in {@link TaskType}
     * @param email the subscriber email associated with the Task
     * @return the Task that matches the provided criteria
     */
    Task getByReferenceAndTaskTypeAndSubscriberEmail(String ref, TaskType taskType, String email);
}
