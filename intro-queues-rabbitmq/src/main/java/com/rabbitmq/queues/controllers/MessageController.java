package com.rabbitmq.queues.controllers;

import com.rabbitmq.queues.config.RabbitMQConfiguration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/exchanges")
public class MessageController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/messages")
    public String sendMessage(@RequestBody String message) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfiguration.FIRST_EXCHANGE_NAME,
                RabbitMQConfiguration.FIRST_ROUTING_KEY,
                message
        );

        rabbitTemplate.convertAndSend(
                RabbitMQConfiguration.SECOND_EXCHANGE_NAME,
                RabbitMQConfiguration.SECOND_ROUTING_KEY,
                message
        );
        return "Mensagem enviada com sucesso para o RabbitMQ.";
    }
}