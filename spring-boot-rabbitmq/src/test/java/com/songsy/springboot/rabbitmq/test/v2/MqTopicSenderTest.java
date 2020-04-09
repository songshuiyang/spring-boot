package com.songsy.springboot.rabbitmq.test.v2;

import com.songsy.springboot.rabbitmq.RabbitMqApplicationPort9011;
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
public class MqTopicSenderTest {

    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;

    /**
     * topic模式
     */
    @Test
    public void test0() {
        rabbitMessagingTemplate.convertAndSend("exchange_send_message_topic_model", "topic.first1", "JAVA开发topic.first1");
    }

    /**
     * topic模式
     */
    @Test
    public void test1() {
        rabbitMessagingTemplate.convertAndSend("exchange_send_message_topic_model", "other.first1", "JAVA开发topic.first1");
    }


}
