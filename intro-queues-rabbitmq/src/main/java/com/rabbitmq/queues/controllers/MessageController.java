package com.rabbitmq.queues.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.queues.config.RabbitMQConfiguration;
import com.rabbitmq.queues.entities.Product;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/exchanges")
public class MessageController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping(value = "/producer/{exchangeName}/{messageData}")
    public String producer(@PathVariable("exchangeName") String exchange,
                           @PathVariable("messageData") String messageData) {


        amqpTemplate.convertAndSend(exchange, "", messageData);
        return "Message sent to the RabbitMQ Fanout Exchange Successfully";
    }

    @PostMapping("/messages")
    public String sendMessage(@RequestBody String message) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfiguration.FIRST_EXCHANGE_NAME,
                "",
                message
        );
        return "Mensagem enviada com sucesso para o RabbitMQ.";
    }
}