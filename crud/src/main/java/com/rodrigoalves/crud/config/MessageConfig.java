package com.rodrigoalves.crud.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig {

    @Value("${crud.rabbitmq.exchange.create}")
    String exchangeCreate;
    @Value("${crud.rabbitmq.exchange.update}")
    String exchangeUpdate;
    @Value("${crud.rabbitmq.exchange.delete}")
    String exchangeDelete;

    @Bean
    public Exchange declareExchangeCreate() {
        return ExchangeBuilder.directExchange(exchangeCreate).durable(true).build();
    }

    @Bean
    public Exchange declareExchangeUpdate() {
        return ExchangeBuilder.directExchange(exchangeUpdate).durable(true).build();
    }

    @Bean
    public Exchange declareExchangeDelete() {
        return ExchangeBuilder.directExchange(exchangeDelete).durable(true).build();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
