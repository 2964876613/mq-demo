package com.itheima.consumer.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.lang.model.type.ExecutableType;
import java.time.LocalDateTime;
import java.util.Map;

@Component
@Slf4j
public class SpringRabbitListener {

    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueue(String message) {
        log.info("监听到simple.queue的消息：【{}】", message);
    }

    @RabbitListener(queues = "work.queue")
    public void listenWorkQueue1(String message) throws InterruptedException {
        System.out.println("消费者1接收到消息: " + message + "," + LocalDateTime.now());
        Thread.sleep(25);
    }

    @RabbitListener(queues = "work.queue")
    public void listenWorkQueue2(String message) throws InterruptedException {
        System.err.println("消费者2......接收到消息: " + message + "," + LocalDateTime.now());
        Thread.sleep(200);
    }

    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueue1(String message) {
        log.info("消费者1监听到fanout.queue1的消息：【{}】", message);
    }

    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueue2(String message) {
        log.info("消费者2监听到fanout.queue2的消息：【{}】", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1", declare = "true"),
            exchange =  @Exchange(name = "hmall.direct", type = ExchangeTypes.DIRECT),
            key = {"red", "blue"}
    ))
    public void listenDirectQueue1(String message) {
        log.info("消费者1监听到direct.queue1的消息：【{}】", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue2", declare = "true"),
            exchange =  @Exchange(name = "hmall.direct", type = ExchangeTypes.DIRECT),
            key = {"red", "yellow"}
    ))
    public void listenDirectQueue2(String message) {
        log.info("消费者2监听到direct.queue2的消息：【{}】", message);
    }

    @RabbitListener(queues = "topic.queue1")
    public void listenTopicQueue1(String message) {
        log.info("消费者1监听到topic.queue1的消息：【{}】", message);
    }

    @RabbitListener(queues = "topic.queue2")
    public void listenTopicQueue2(String message) {
        log.info("消费者2监听到topic.queue2的消息：【{}】", message);
    }

    @RabbitListener(queues = "Object.queue")
    public void listenObjectQueue(Map<String,Object> message) {
        log.info("消费者监听到topic.queue的消息：【{}】", message);
    }
}
