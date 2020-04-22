package com.songsy.springboot.rabbitmq.test.v3;

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
public class MqSenderTest {

    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;

    /**
     * 生产者
     */
    @Test
    public void test0() {
        for (int i = 0 ; i < 100; i ++) {
            rabbitMessagingTemplate.convertAndSend("only_one_queue", "发送一条" + i + "only_one_queue消息");
        }
    }


}
