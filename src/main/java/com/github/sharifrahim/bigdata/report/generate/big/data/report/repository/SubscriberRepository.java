package com.github.sharifrahim.bigdata.report.generate.big.data.report.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.SubscriberEnum.ReportType;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.constant.SubscriberEnum.Status;
import com.github.sharifrahim.bigdata.report.generate.big.data.report.entity.Subscriber;

/**
 * SubscriberRepository provides data access operations for the {@link Subscriber} entity.
 * <p>
 * In addition to the standard CRUD operations inherited from {@link JpaRepository}, this interface
 * defines a custom query method to find subscribers by report type, report period, and status.
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
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    /**
     * Finds subscribers by report type, where the given date falls within their subscription period,
     * and they have the specified status.
     * 
     * @param reportType the type of report to filter subscribers by
     * @param date the date that should fall within the subscriber's period (between periodFrom and periodTo)
     * @param status the subscriber status to filter by (e.g., ACTIVE or INACTIVE)
     * @return a list of subscribers matching the specified criteria
     */
    @Query("SELECT s FROM Subscriber s WHERE s.reportType = :reportType " +
           "AND s.periodFrom <= :date AND s.periodTo >= :date " +
           "AND s.status = :status")
    List<Subscriber> findByReportTypeAndReportPeriodAndStatus(
            @Param("reportType") ReportType reportType,
            @Param("date") LocalDate date,
            @Param("status") Status status);
}
