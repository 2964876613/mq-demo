package com.itheima.consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {
    @Bean
    public FanoutExchange fanoutExchange() {
        /*return new FanoutExchange("hmall.fanout");*/
        return ExchangeBuilder.fanoutExchange("hmall.fanout").build();
    }

    @Bean
    public Queue fanoutQueue1() {
        /*return new Queue("fanout.queue1");*/
        return QueueBuilder.durable("fanout.queue1").build();
    }

    @Bean
    public Binding fanoutQueue1Binding(Queue fanoutQueue1, FanoutExchange fanoutExchange    ) {
        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
    }

    @Bean
    public Queue fanoutQueue2() {
        /*return new Queue("fanout.queue1");*/
        return QueueBuilder.durable("fanout.queue2").build();
    }

    @Bean
    public Binding fanoutQueue2Binding(Queue fanoutQueue2, FanoutExchange fanoutExchange    ) {
        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
    }


}
