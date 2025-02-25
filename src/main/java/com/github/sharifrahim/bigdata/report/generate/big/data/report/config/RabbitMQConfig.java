package com.github.sharifrahim.bigdata.report.generate.big.data.report.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;


/**
 * RabbitMQConfig configures the RabbitMQ connection, listener container factory,
 * message converter, and AMQP template for sending and receiving messages.
 * <p>
 * This configuration class uses properties defined in your application properties or YAML file
 * to set up:
 * <ul>
 *   <li>The RabbitMQ connection details (username, password, host, reply timeout, etc.)</li>
 *   <li>Concurrency settings for RabbitMQ listeners</li>
 *   <li>Queue names for daily transaction report and summary messages</li>
 * </ul>
 * </p>
 * <p>
 * For more details, please refer to my GitHub repository:
 * <a href="https://github.com/sharifrahim">https://github.com/sharifrahim</a>
 * </p>
 * 
 * @author Sharif
 * @version 1.0
 * @since 2025
 */
@Slf4j
@Configuration
public class RabbitMQConfig {

    // RabbitMQ connection properties
    @Value("${spring.rabbitmq.username}")
    private String username;
    
    @Value("${spring.rabbitmq.password}")
    private String password;
    
    @Value("${spring.rabbitmq.host}")
    private String host;
    
    @Value("${spring.rabbitmq.reply.timeout}")
    private Integer replyTimeout;
    
    // RabbitMQ listener concurrency settings
    @Value("${spring.rabbitmq.listener.simple.concurrency}")
    private Integer concurrentConsumers;
    
    @Value("${spring.rabbitmq.listener.simple.max-concurrency}")
    private Integer maxConcurrentConsumers;

    // Queue names for transaction reporting
    @Value("${spring.queue.daily-transaction-report}")
    private String dailyTransactionQueueName;
    
    @Value("${spring.queue.daily-transaction-report-summary}")
    private String dailyTransactionSummaryQueueName;

    /**
     * Creates and configures a RabbitMQ {@link ConnectionFactory} using the provided
     * connection properties.
     *
     * @return a configured {@link ConnectionFactory} instance
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        log.debug("Initializing RabbitMQ ConnectionFactory with host: {}", host);
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setVirtualHost("/");
        connectionFactory.setHost(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }
    
    /**
     * Configures a {@link SimpleRabbitListenerContainerFactory} for RabbitMQ listeners.
     * <p>
     * This factory is set up to use a JSON message converter, manual acknowledgment, and
     * concurrency settings defined in the configuration.
     * </p>
     *
     * @param connectionFactory the RabbitMQ connection factory
     * @return a configured {@link SimpleRabbitListenerContainerFactory} instance
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        log.debug("Configuring RabbitListenerContainerFactory with {} concurrent consumers and max {} consumers.",
                concurrentConsumers, maxConcurrentConsumers);
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        factory.setConcurrentConsumers(concurrentConsumers); // Minimum concurrent consumers
        factory.setMaxConcurrentConsumers(maxConcurrentConsumers); // Maximum concurrent consumers
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL); // Manual acknowledgment mode for fine-grained control
        return factory;
    }
    
    /**
     * Creates a JSON message converter using Jackson.
     *
     * @return a {@link MessageConverter} instance for JSON conversion
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        log.debug("Initializing Jackson2JsonMessageConverter");
        return new Jackson2JsonMessageConverter();
    }
    
    /**
     * Configures the AMQP template for sending and receiving messages.
     * <p>
     * The template uses the JSON message converter and a specified reply timeout.
     * </p>
     *
     * @param connectionFactory the RabbitMQ connection factory
     * @return a configured {@link AmqpTemplate} instance
     */
    @Bean
    public AmqpTemplate rabbitTemplateGen(ConnectionFactory connectionFactory) {
        log.debug("Configuring RabbitTemplate with reply timeout: {} ms", replyTimeout);
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setReplyTimeout(replyTimeout);
        rabbitTemplate.setUseDirectReplyToContainer(false);
        return rabbitTemplate;
    }
    
    /**
     * Provides the name of the daily transaction report queue.
     *
     * @return the daily transaction report queue name
     */
    @Bean
    public String dailyTransactionQueueName() {
        log.debug("Using daily transaction queue name: {}", dailyTransactionQueueName);
        return dailyTransactionQueueName;
    }
    
    /**
     * Provides the name of the daily transaction report summary queue.
     *
     * @return the daily transaction report summary queue name
     */
    @Bean
    public String dailyTransactionSummaryQueueName() {
        log.debug("Using daily transaction summary queue name: {}", dailyTransactionSummaryQueueName);
        return dailyTransactionSummaryQueueName;
    }
}
