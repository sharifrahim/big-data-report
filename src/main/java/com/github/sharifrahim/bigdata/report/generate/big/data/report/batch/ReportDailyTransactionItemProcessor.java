package com.github.sharifrahim.bigdata.report.generate.big.data.report.batch;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.github.sharifrahim.bigdata.report.generate.big.data.report.dto.ReportDailyTransactionDto;

import lombok.extern.slf4j.Slf4j;

/**
 * ReportDailyTransactionItemProcessor processes raw daily transaction data and converts it into
 * a processed format for daily transaction reporting.
 * <p>
 * This class formats the transaction date using a custom pattern and updates the transaction amount.
 * It is used as an ItemProcessor in a Spring Batch job.
 * </p>
 * <p>
 * For more details, please see my GitHub repository:
 * <a href="https://github.com/sharifrahim">https://github.com/sharifrahim</a>
 * </p>
 *
 * @author Sharif
 * @version 1.0
 * @since 2025
 */
@Slf4j
@Component
public class ReportDailyTransactionItemProcessor
        implements ItemProcessor<ReportDailyTransactionDto.RawData, ReportDailyTransactionDto.ProcessData> {

    // Formatter for the transaction date in the format "dd/MM/yyyy HH:mm:ss"
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Processes a raw transaction item by formatting the transaction date and updating the amount.
     *
     * @param item the raw transaction data; may be null, in which case null is returned
     * @return a ProcessData object containing the processed data, or null if the input is null
     * @throws Exception if any error occurs during processing
     */
    @Override
    public ReportDailyTransactionDto.ProcessData process(ReportDailyTransactionDto.RawData item) throws Exception {
        // Check if the input item is null and log a debug message if so.
        if (item == null) {
            log.debug("Received null transaction item; skipping processing.");
            return null;
        }

        // Log the start of processing for this transaction.
        log.debug("Processing transaction for payer: {}", item.getPayerName());

        // Format the transaction date using the specified formatter.
        String formattedDate = item.getTransactionDate().format(FORMATTER);
        log.debug("Formatted transaction date: {}", formattedDate);

        // Update the transaction amount.
        // Here, the amount is converted to BigDecimal and added with BigDecimal.ZERO as a placeholder.
        BigDecimal updatedAmount = new BigDecimal(item.getAmount().toString()).add(BigDecimal.ZERO);
        log.debug("Calculated updated amount: {}", updatedAmount);

        // Create a new ProcessData object with the converted and formatted values.
        ReportDailyTransactionDto.ProcessData processedData = new ReportDailyTransactionDto.ProcessData(
                item.getPayerName(),
                item.getPayerEmail(),
                item.getMerchantEmail(),
                updatedAmount.toString(),
                formattedDate
        );

        // Log the successful processing of the transaction.
        log.info("Successfully processed transaction for payer: {}", item.getPayerName());
        return processedData;
    }
}
