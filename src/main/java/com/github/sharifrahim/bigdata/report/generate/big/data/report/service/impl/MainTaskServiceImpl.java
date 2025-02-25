package com.github.sharifrahim.bigdata.report.generate.big.data.report.service.impl;

import org.springframework.stereotype.Component;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.entity.MainTask;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.repository.MainTaskRepository;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.service.MainTaskService;

import lombok.RequiredArgsConstructor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * MainTaskServiceImpl provides implementations for managing {@link MainTask} entities.
 * <p>
 * This service implementation uses the {@link MainTaskRepository} to persist and retrieve MainTask records.
 * It supports saving a new MainTask and retrieving an existing MainTask by its unique identifier.
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
public class MainTaskServiceImpl implements MainTaskService {

    private final MainTaskRepository mainTaskRepository;

    /**
     * Saves the given {@link MainTask} entity.
     * <p>
     * This method delegates to the MainTaskRepository to persist the provided MainTask.
     * </p>
     *
     * @param mainTask the MainTask entity to save
     */
    @Override
    public void save(MainTask mainTask) {
        log.debug("Saving MainTask with reference: {}", mainTask.getDescription());
        mainTaskRepository.save(mainTask);
        log.info("MainTask saved successfully with ID: {}", mainTask.getId());
    }

    /**
     * Retrieves a {@link MainTask} by its unique identifier.
     * <p>
     * This method uses the repository's {@code getById} method to retrieve the MainTask.
     * </p>
     *
     * @param mainTaskId the unique identifier of the MainTask
     * @return the MainTask entity if found; otherwise, an exception is thrown by the repository
     */
    @Override
    public MainTask findById(Long mainTaskId) {
        log.debug("Retrieving MainTask with ID: {}", mainTaskId);
        MainTask mainTask = mainTaskRepository.getById(mainTaskId);
        log.info("MainTask retrieved: {}", mainTask);
        return mainTask;
    }
}

