package com.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    private ConnectionFactory connectionFactory;
    public static final String QUEUE = "my-queue";
    public static final String EXCHANGE = "my-queue";

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Hello world!");

    }
}