package com.github.sharifrahim.bigdata.report.generate.big.data.report.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.TaskEnum.TaskType;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.entity.Task;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.repository.TaskRepository;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.service.TaskService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * TaskServiceImpl provides the implementation for managing {@link Task} entities.
 * <p>
 * This service implementation uses the {@link TaskRepository} to retrieve and persist Task entities.
 * It supports operations for fetching a task by its unique identifier, saving a task, and retrieving a task 
 * based on its reference, task type, and subscriber email.
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
@Slf4j
@RequiredArgsConstructor
@Component
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    /**
     * Retrieves a Task by its unique identifier.
     *
     * @param id the unique identifier of the Task
     * @return an {@link Optional} containing the Task if found; otherwise, an empty Optional
     */
    @Override
    public Optional<Task> getById(Long id) {
        log.debug("Fetching Task with id: {}", id);
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            log.info("Task found: {}", taskOpt.get());
        } else {
            log.warn("No Task found with id: {}", id);
        }
        return taskOpt;
    }

    /**
     * Persists the given Task entity.
     *
     * @param task the Task entity to save
     */
    @Override
    public void save(Task task) {
        log.debug("Saving Task with reference: {}", task.getReference());
        taskRepository.save(task);
        log.info("Task saved with id: {}", task.getId());
    }

    /**
     * Retrieves a Task based on its reference, task type, and subscriber email.
     * <p>
     * This method returns the Task if found; otherwise, it returns {@code null}.
     * </p>
     *
     * @param ref      the unique reference of the Task
     * @param taskType the type of the Task as defined in {@link TaskType}
     * @param email    the subscriber email associated with the Task
     * @return the Task matching the criteria if found; otherwise, {@code null}
     */
    @Override
    public Task getByReferenceAndTaskTypeAndSubscriberEmail(String ref, TaskType taskType, String email) {
        log.debug("Fetching Task with reference: {}, taskType: {}, subscriberEmail: {}", ref, taskType, email);
        Optional<Task> taskOpt = taskRepository.findByReferenceAndTypeAndSubscriberEmail(ref, taskType, email);
        if (taskOpt.isPresent()) {
            log.info("Task found: {}", taskOpt.get());
            return taskOpt.get();
        } else {
            log.warn("No Task found with reference: {}, taskType: {}, subscriberEmail: {}", ref, taskType, email);
            return null;
        }
    }
}
