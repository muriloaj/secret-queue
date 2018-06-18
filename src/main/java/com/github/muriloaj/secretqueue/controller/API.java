package com.github.muriloaj.secretqueue.controller;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class API {

    private String queue = "secret-queue";

    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;

    @RequestMapping(value = "send",
            method = RequestMethod.POST)
    public String sendMessage(@RequestBody String secret) {
        ConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitHost);
        AmqpAdmin admin = new RabbitAdmin(connectionFactory);
        admin.declareQueue(new Queue(queue));
        AmqpTemplate template = new RabbitTemplate(connectionFactory);
        template.convertAndSend(queue, secret.trim().concat(" at ").concat(new java.util.Date().toString()));
        return "OK";
    }

    @RequestMapping(value = "take")
    public String takeMessage() {
        ConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitHost);
        AmqpAdmin admin = new RabbitAdmin(connectionFactory);
        admin.declareQueue(new Queue(queue));
        AmqpTemplate template = new RabbitTemplate(connectionFactory);
        return (String) template.receiveAndConvert(queue);
    }
}
