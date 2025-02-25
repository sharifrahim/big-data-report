package com.github.sharifrahim.bigdata.report.generate.big.data.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GenerateBigDataReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenerateBigDataReportApplication.class, args);
	}

}
