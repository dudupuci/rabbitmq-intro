package com.rabbitmq.queues.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class RabbitMQConfiguration {

    public static final String FIRST_QUEUE_NAME = "marketingQueue";
    public static final String SECOND_QUEUE_NAME = "financeQueue";
    public static final String THIRD_QUEUE_NAME = "adminQueue";
    public static final String FIRST_EXCHANGE_NAME = "fanout-exchange";

    @Bean
    public Queue marketingQueue() {
        return QueueBuilder.durable(FIRST_QUEUE_NAME).build();
    }

    @Bean
    public Queue financeQueue() {
        return QueueBuilder.durable(SECOND_QUEUE_NAME).build();
    }

    @Bean
    public Queue adminQueue() {
        return QueueBuilder.durable(THIRD_QUEUE_NAME).build();
    }

    @Bean
    public FanoutExchange exchange() {
        return ExchangeBuilder.fanoutExchange(FIRST_EXCHANGE_NAME).build();
    }

    @Bean
    public Binding marketingBinding() {
        return BindingBuilder.bind(marketingQueue()).to(exchange());
    }

    @Bean
    public Binding financeBinding() {
        return BindingBuilder.bind(financeQueue()).to(exchange());
    }

    @Bean
    public Binding adminBinding() {
        return BindingBuilder.bind(adminQueue()).to(exchange());
    }

    @Component
    public class Receiver {
        @RabbitListener(queues = RabbitMQConfiguration.FIRST_QUEUE_NAME)
        public void receiveMessageForQueue(final String message) {
            System.out.println("Mensagem recebida para o RabbitMQ: " + message.toUpperCase());
        }

        @RabbitListener(queues = RabbitMQConfiguration.SECOND_QUEUE_NAME)
        public void receiveMessageForQueue2(final String message) {
            System.out.println("Mensagem recebida para o RabbitMQ: " + message.toUpperCase());
        }

        @RabbitListener(queues = RabbitMQConfiguration.THIRD_QUEUE_NAME)
        public void receiveMessageForQueue3(final String message) {
            System.out.println("Mensagem recebida para o RabbitMQ: " + message.toUpperCase());
        }

    }

    // Configuração adicional para criar os elementos no RabbitMQ
    @Bean
    public String configureRabbitMQ(ConnectionFactory connectionFactory) {
        AmqpAdmin admin = new RabbitAdmin(connectionFactory);
        admin.declareQueue(marketingQueue());
        admin.declareQueue(financeQueue());
        admin.declareQueue(adminQueue());
        admin.declareExchange(exchange());
        admin.declareBinding(marketingBinding());
        admin.declareBinding(financeBinding());
        admin.declareBinding(adminBinding());
        return "Connection created";
    }
}
