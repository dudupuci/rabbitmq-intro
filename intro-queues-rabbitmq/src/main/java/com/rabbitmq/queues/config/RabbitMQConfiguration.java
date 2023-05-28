package com.rabbitmq.queues.config;

import com.rabbitmq.queues.entities.Product;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class RabbitMQConfiguration {

    public static final String FIRST_QUEUE_NAME = "rabbit-queue1";
    public static final String SECOND_QUEUE_NAME = "product-queue";
    public static final String FIRST_EXCHANGE_NAME = "rabbit-exchange-1";
    public static final String SECOND_EXCHANGE_NAME = "product-exchange";
    public static final String FIRST_ROUTING_KEY = "rabbit-routing-key-1";
    public static final String SECOND_ROUTING_KEY = "product-routing-key";

    @Bean
    public Queue createQueue() {
        return QueueBuilder
                .durable(FIRST_QUEUE_NAME)
                .build();
    }

    @Bean
    public Queue createAnotherQueue() {
        return QueueBuilder
                .durable(SECOND_QUEUE_NAME)
                .build();
    }

    @Bean
    public DirectExchange exchangeOne() {
        return ExchangeBuilder.directExchange(FIRST_EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    @Bean
    public DirectExchange exchangeTwo() {
        return ExchangeBuilder.directExchange(SECOND_EXCHANGE_NAME)
                .durable(true)
                .build();
    }


    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(createQueue())
                .to(exchangeOne())
                .with(FIRST_ROUTING_KEY);
    }

    @Bean
    public Binding bindingTwo() {
        return BindingBuilder
                .bind(createAnotherQueue())
                .to(exchangeTwo())
                .with(SECOND_ROUTING_KEY);
    }

    @Component
    public class Receiver {
        @RabbitListener(queues = RabbitMQConfiguration.FIRST_QUEUE_NAME)
        public void receiveMessageQueue1(final String message) {
            System.out.println("Mensagem recebida para o RabbitMQ" + message);
        }
        @RabbitListener(queues = RabbitMQConfiguration.SECOND_QUEUE_NAME)
        public void receiveMessageQueue2(final String product) { // Defino como string pois no Controller é convertido para String
            System.out.println("Mensagem recebida para o RabbitMQ" + product);
        }
    }
}
