# Big Data Report

Big Data Report is a Spring Boot-based project designed to **demonstrate how to generate big data reports efficiently using Spring Boot**. This project showcases how to handle large datasets without performance bottlenecks or failure risks, ensuring that the report generation process is reliable and scalable — regardless of data size.

Leveraging Spring Batch for data processing and scheduling, RabbitMQ for asynchronous messaging, and OpenCSV for dynamic CSV file generation, the project also integrates **Java Streams** to stream large datasets efficiently without overloading memory.

For more details, visit the [project repository](https://github.com/sharifrahim/big-data-report).

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Configuration](#configuration)
  - [Running the Application](#running-the-application)
- [How It Works](#how-it-works)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Features

- ✅ **Big Data Handling:** Efficiently processes large datasets using Java Streams, ensuring memory-safe streaming.
- 📊 **Daily Transaction Reports:** Generate detailed daily transaction reports.
- 📈 **Daily Transaction Summary Reports:** Generate summary reports that aggregate daily transactions.
- ⚡ **Batch Processing & Scheduling:** Uses Spring Batch for job scheduling and chunk-based data processing.
- 📨 **Message-Driven Architecture:** Integrates RabbitMQ to trigger report generation asynchronously.
- 🗄️ **Dynamic CSV Generation:** Uses reflection and OpenCSV to dynamically create CSV files from DTOs.
- 🏛️ **Well-Structured Layers:** Clear separation between entities, repositories, services, and tasklets.
- 📋 **Failure-Resistant:** The process is designed to be fail-safe, ensuring that even large datasets do not cause job failures.
- 📖 **Comprehensive Logging:** Uses SLF4J with Logback for detailed logging and easy debugging.

## Tech Stack

- **Java:** 11 or higher
- **Spring Boot:** Core framework for building the application
- **Spring Batch:** Batch processing and job scheduling
- **Spring Data JPA:** Data persistence and repository abstraction
- **RabbitMQ:** Asynchronous messaging for job triggering
- **Lombok:** Reduces boilerplate code
- **OpenCSV:** CSV file generation
- **Maven:** Build and dependency management

## Project Structure

```plaintext
big-data-report/
├── src/main/java
│   └── com/github/sharifrahim/bigdata/report/generate/big/data/report
│       ├── config
│       │   ├── EndOfDayConfig.java
│       │   ├── GenerateReportConfig.java
│       │   └── RabbitMQConfig.java
│       ├── dto
│       │   ├── CreateTaskQueueMessageDto.java
│       │   ├── ReportDailyTransactionDto.java
│       │   └── ReportDailyTransactionSummaryDto.java
│       ├── entity
│       │   ├── MainTask.java
│       │   ├── Subscriber.java
│       │   ├── Task.java
│       │   └── Transaction.java
│       ├── listener
│       │   ├── DailyTransactionQueueListener.java
│       │   └── DailyTransactionSummaryQueueListener.java
│       ├── repository
│       │   ├── MainTaskRepository.java
│       │   ├── SubscriberRepository.java
│       │   ├── TaskRepository.java
│       │   └── TransactionRepository.java
│       ├── service
│       │   ├── MainTaskService.java
│       │   ├── MainTaskServiceImpl.java
│       │   ├── SubscriberService.java
│       │   ├── SubscriberServiceImpl.java
│       │   ├── TaskService.java
│       │   ├── TaskServiceImpl.java
│       │   ├── TransactionService.java
│       │   └── TransactionServiceImpl.java
│       ├── tasklet
│       │   ├── CreateReportTasklet.java
│       │   └── GenerateDailyTransactionSummaryReportTasklet.java
│       └── util
│           └── CsvUtil.java
└── pom.xml
```

## Getting Started

### Prerequisites

- **Java 11+**
- **Maven 3.6+**
- **RabbitMQ Server:** Ensure RabbitMQ is installed and running.
- **Database:** Configure your preferred database (e.g., H2, MySQL, PostgreSQL) in the application configuration.

### Installation

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/sharifrahim/big-data-report.git
   cd big-data-report
   ```

2. **Build the Project:**

   ```bash
   mvn clean install
   ```

### Configuration

Configure your application settings in `src/main/resources/application.properties` or `application.yml`. An example configuration:

```properties
# DataSource Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update

# RabbitMQ Configuration
spring.rabbitmq.host=localhost
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
spring.rabbitmq.reply.timeout=5000

# Queue Names
spring.queue.daily-transaction-report=dailyReportQueue
spring.queue.daily-transaction-report-summary=dailyReportSummaryQueue

# Batch Configuration (optional)
spring.batch.initialize-schema=always
```

### Running the Application

Run the application using Maven:

```bash
mvn spring-boot:run
```

Or run the packaged JAR:

```bash
java -jar target/big-data-report-1.0.jar
```

## How It Works

1. **Streaming Data Retrieval:**
   - The `TransactionServiceImpl` uses Java Streams to fetch transaction data efficiently, ensuring that large datasets are processed without excessive memory usage.

2. **Batch Processing & Scheduling:**
   - **Spring Batch Jobs:** Configured in `EndOfDayConfig` and `GenerateReportConfig`, these jobs process transactions in chunks.
   - **Tasklets:** `CreateReportTasklet` and `GenerateDailyTransactionSummaryReportTasklet` handle report generation logic.
   - **Schedulers:** `EndOfDayScheduler` triggers job executions at fixed intervals.

3. **Messaging with RabbitMQ:**
   - **Listeners:** `DailyTransactionQueueListener` and `DailyTransactionSummaryQueueListener` listen to RabbitMQ queues and trigger report jobs upon receiving messages.
   - **Message DTO:** `CreateTaskQueueMessageDto` carries task details used to initiate jobs.

4. **CSV File Generation:**
   - **CsvUtil:** Uses reflection and OpenCSV to dynamically generate CSV files from DTOs, ensuring the generated reports include both headers and data rows.

5. **Data Persistence:**
   - **Entities & Repositories:** JPA entities such as `MainTask`, `Task`, `Transaction`, and `Subscriber` are managed via Spring Data repositories.
   - **Service Layers:** Business logic is encapsulated in service implementations, ensuring a clean separation of concerns.

## Contributing

Contributions are welcome! Please fork the repository, make your changes, and submit a pull request. For major changes, open an issue first to discuss your proposed modifications.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For questions or further information, please contact [Sharif Rahim](https://github.com/sharifrahim).

