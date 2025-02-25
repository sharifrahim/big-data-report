package com.github.sharifrahim.bigdata.report.generate.big.data.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.entity.MainTask;

/**
 * MainTaskRepository provides CRUD operations for the MainTask entity.
 * <p>
 * This repository extends {@link JpaRepository} to inherit basic data access functionality
 * such as saving, deleting, and finding MainTask entities. Spring Data JPA will automatically
 * provide the implementation at runtime.
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
@Repository
public interface MainTaskRepository extends JpaRepository<MainTask, Long> {
}
