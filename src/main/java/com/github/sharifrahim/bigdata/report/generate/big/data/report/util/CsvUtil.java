package com.github.sharifrahim.bigdata.report.generate.big.data.report.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import com.opencsv.CSVWriter;

import lombok.extern.slf4j.Slf4j;

/**
 * CsvUtil is a utility class for writing a list of data objects to a CSV file.
 * <p>
 * This utility uses reflection to dynamically retrieve the field names (for the header)
 * and field values (for each row) from the first object in the provided list.
 * If the CSV file does not exist, a header row is written first.
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
public class CsvUtil {

    /**
     * Writes the data list to a CSV file.
     * <p>
     * If the file doesn't exist, a header row is created using the field names of the first object.
     * Each object in the list is then written as a CSV row by accessing its field values via reflection.
     * </p>
     *
     * @param filename the name of the CSV file
     * @param dataList the list of data objects to be written to the CSV file
     * @param <T>      the type of the data objects
     * @throws IOException if an I/O error occurs during writing
     */
    public static <T> void writeToCsv(String filename, List<T> dataList) throws IOException {
        if (dataList == null || dataList.isEmpty()) {
            log.warn("Data list is null or empty; no CSV file will be written.");
            return;
        }

        File file = new File(filename);
        boolean fileExists = file.exists();
        log.info("Writing CSV to file: {} (File exists: {})", filename, fileExists);

        try (FileWriter fileWriter = new FileWriter(file, true);
             CSVWriter csvWriter = new CSVWriter(fileWriter)) {

            // Use reflection to get the fields of the first object.
            Field[] fields = dataList.get(0).getClass().getDeclaredFields();

            // Write header only if the file is newly created.
            if (!fileExists) {
                String[] headers = new String[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    headers[i] = fields[i].getName();
                }
                csvWriter.writeNext(headers);
                log.debug("CSV header written: {}", String.join(", ", headers));
            }

            // Write data rows.
            for (T data : dataList) {
                String[] row = new String[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    fields[i].setAccessible(true);
                    try {
                        Object value = fields[i].get(data);
                        row[i] = (value != null) ? value.toString() : "";
                    } catch (IllegalAccessException e) {
                        log.error("Unable to access field {}: {}", fields[i].getName(), e.getMessage());
                        row[i] = "";
                    }
                }
                csvWriter.writeNext(row);
                log.debug("CSV row written: {}", String.join(", ", row));
            }
        } catch (IOException e) {
            log.error("Error writing CSV file: {}", filename, e);
            throw e;
        }
    }
}
