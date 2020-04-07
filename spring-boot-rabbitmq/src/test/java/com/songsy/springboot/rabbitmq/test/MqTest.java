package com.songsy.springboot.rabbitmq.test;

import com.songsy.springboot.rabbitmq.RabbitMqApplication;
import com.songsy.springboot.rabbitmq.mq.v1.RabbitMqSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author songsy
 * @date 2019/11/4 16:18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitMqApplication.class)
public class MqTest {

    @Autowired
    private RabbitMqSender mqSender;

    @Test
    public void mq() {
        for (int i =0 ; i < 100; i ++) {
            mqSender.send("你好 Direct 交换机模式");
        }
    }

    @Test
    public void mq01() {
        mqSender.sendTopic("你好 Topic 交换机模式");
    }
    @Test
    public void mq02() {
        mqSender.sendFanout("你好 Fanout模式 交换机Exchange");
    }

    @Test
    public void mq03() {
        mqSender.sendHeaders("你好 Header模式 交换机Exchange");
    }

}
