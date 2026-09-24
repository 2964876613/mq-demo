package com.itheima.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class SpringAmqpTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSimpleQueue() {
        // 1.队列名
        String queueName = "simple.queue";
        // 2.消息
        String message = "Hello,SpringAmqp!";
        // 3.发送消息
        rabbitTemplate.convertAndSend(queueName, message);
    }

    @Test
    public void testWorkQueue() {
        // 1.队列名
        String queueName = "work.queue";

        for (int i = 1; i <= 50; i++) {
            // 2.消息
            String message = "Hello,SpringAmqp!" + i;
            // 3.发送消息
            rabbitTemplate.convertAndSend(queueName, message);
        }
    }

    @Test
    public void testFanoutQueue() {
        // 1.交换机名
        String exchangeName = "hmall.fanout";
        // 2.消息
        String message = "Hello,everyone!";
        // 3.发送消息
        rabbitTemplate.convertAndSend(exchangeName, null, message);
    }

    @Test
    public void testDirectQueue() {
        // 1.交换机名
        String exchangeName = "hmall.direct";
        // 2.消息
        String message = "Hello,yellow";
        // 3.发送消息
        rabbitTemplate.convertAndSend(exchangeName, "yellow", message);
    }

    @Test
    public void testTopicQueue() {
        // 1.交换机名
        String exchangeName = "hmall.topic";
        // 2.消息
        String message = "今天天气不错";
        // 3.发送消息
        rabbitTemplate.convertAndSend(exchangeName, "china.weather", message);
    }
    @Test
    public void testSendObject() {
        // 1.准备消息
        Map<String, Object> msg = new HashMap<>(2);
        msg.put("name", "Jack");
        msg.put("age", 21);
        // 2.发送消息
        rabbitTemplate.convertAndSend("Object.queue", msg);
    }

    @Test
    public void testConfirmCallback() {
        // 0.创建correlation Data
        CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());
        cd.getFuture().addCallback(new ListenableFutureCallback<CorrelationData.Confirm>() {
            @Override
            public void onSuccess(CorrelationData.Confirm result) {
                // 判断是否成功
                if(result.isAck()){
                    log.debug("收到confirmCallback ack，消息发送成功！");
                }else {
                    log.debug("收到confirmCallback nack，消息发送失败！reason:{}", result.getReason());
                }
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("spring amqp处理确认结果异常", ex);
            }
        });

        // 1.交换机名
        String exchangeName = "hmall.direct";
        // 2.消息
        String message = "Hello,yellow";
        // 3.发送消息
        rabbitTemplate.convertAndSend(exchangeName, "yellow", message, cd);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSendDelayMessage() {
        rabbitTemplate.convertAndSend("normal.direct", "hi", "hello", message -> {
            message.getMessageProperties().setExpiration("10000");
            return message;
        });
    }
}