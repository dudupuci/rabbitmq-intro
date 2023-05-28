package com.rabbitmq.queues.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.queues.config.RabbitMQConfiguration;
import com.rabbitmq.queues.entities.Product;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/exchanges")
public class MessageController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/messages")
    public String sendMessage(@RequestBody String message) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfiguration.FIRST_EXCHANGE_NAME,
                RabbitMQConfiguration.FIRST_ROUTING_KEY,
                message
        );
        return "Mensagem enviada com sucesso para o RabbitMQ.";
    }

    @PostMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String sendProduct(@RequestBody Product product) {
        try {
            var object = objectMapper.writeValueAsString(product);
            rabbitTemplate.convertAndSend(
                    RabbitMQConfiguration.SECOND_EXCHANGE_NAME,
                    RabbitMQConfiguration.SECOND_ROUTING_KEY,
                    object
            );
        } catch (Exception err) {
            throw new RuntimeException("Error while trying to serialize product.");
        }
        return "Produto enviado com sucesso para o RabbitMQ";
    }
}