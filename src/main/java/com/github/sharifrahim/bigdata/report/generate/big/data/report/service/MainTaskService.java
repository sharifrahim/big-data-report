package com.github.sharifrahim.bigdata.report.generate.big.data.report.service;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.entity.MainTask;

/**
 * MainTaskService defines operations for managing {@link MainTask} entities.
 * <p>
 * This service interface provides methods for saving and retrieving MainTask records.
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
public interface MainTaskService {

    /**
     * Persists the given {@link MainTask} entity.
     *
     * @param mainTask the MainTask entity to save
     */
    void save(MainTask mainTask);

    /**
     * Retrieves a {@link MainTask} entity by its unique identifier.
     *
     * @param mainTaskId the unique identifier of the MainTask
     * @return the MainTask entity if found, otherwise {@code null}
     */
    MainTask findById(Long mainTaskId);
}
