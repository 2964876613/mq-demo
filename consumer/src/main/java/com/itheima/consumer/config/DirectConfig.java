/*
package com.itheima.consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectConfig {
    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange("hmall.direct").build();
    }

    @Bean
    public Queue directQueue1() {
        return QueueBuilder.durable("direct.queue1").build();
    }

    @Bean
    public Binding directQueue1BindingRed(Queue directQueue1, DirectExchange directExchange ) {
        return BindingBuilder.bind(directQueue1()).to(directExchange).with("red");
    }

    @Bean
    public Binding directQueue1BindingBlue(Queue directQueue1, DirectExchange directExchange ) {
        return BindingBuilder.bind(directQueue1()).to(directExchange).with("blue");
    }




}
*/
