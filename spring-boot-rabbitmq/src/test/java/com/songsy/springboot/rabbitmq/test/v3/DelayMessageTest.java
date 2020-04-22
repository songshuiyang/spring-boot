package com.songsy.springboot.rabbitmq.test.v3;

import com.songsy.springboot.rabbitmq.RabbitMqApplicationPort9011;
import com.songsy.springboot.rabbitmq.common.OrderMO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author songshuiyang
 * @date 2020/4/12 11:43
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitMqApplicationPort9011.class)
public class DelayMessageTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 向延迟队列中发送消息
     */
    @Test
    public void sendDelayMessage1() {
        OrderMO orderMO = new OrderMO();
        orderMO.setOrderNo("0");
        rabbitTemplate.convertAndSend(
                "测试死信队列-normal-exchange",
                "测试死信队列-normal-routing-key",
                orderMO
        );
    }

    /**
     * 向队列中发送消息 抛异常
     */
    @Test
    public void sendDelayMessage2() {
        OrderMO orderMO = new OrderMO();
        orderMO.setOrderNo("1");
        rabbitTemplate.convertAndSend(
                "测试死信队列-normal-exchange",
                "测试死信队列-normal-routing-key",
                orderMO
        );
    }


    /**
     * 向队列中发送过期消息
     */
    @Test
    public void sendDelayMessage3() {
        OrderMO orderMO = new OrderMO();
        orderMO.setOrderNo("0");
        rabbitTemplate.convertAndSend(
//                "exchange_submit_order",
//                "routing_key_submit_order",
                "测试死信队列-normal-exchange",
                "测试死信队列-normal-routing-key",
                orderMO,
                message -> {
                    //设置10秒过期
                    message.getMessageProperties().setExpiration("10000");
                    return message;
                }
        );
        logger.info("{}ms后执行", 60000);
    }

}
