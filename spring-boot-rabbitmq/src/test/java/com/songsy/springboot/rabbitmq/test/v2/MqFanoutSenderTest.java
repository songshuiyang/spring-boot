package com.songsy.springboot.rabbitmq.test.v2;

import com.songsy.springboot.rabbitmq.RabbitMqApplicationPort9011;
import com.songsy.springboot.rabbitmq.common.OrderMO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author songsy
 * @date 2020/4/7 11:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitMqApplicationPort9011.class)
public class MqFanoutSenderTest {

    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;

    /**
     * fanout模式会给所有绑定了交换器的队列发消息
     */
    @Test
    public void test0() {
        // routingKey可以为空
        rabbitMessagingTemplate.convertAndSend("exchange_send_message", "", "JAVA开发");
    }


}
