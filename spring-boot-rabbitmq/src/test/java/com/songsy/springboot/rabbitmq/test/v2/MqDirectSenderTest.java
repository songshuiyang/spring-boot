package com.songsy.springboot.rabbitmq.test.v2;

import com.songsy.springboot.rabbitmq.RabbitMqApplicationPort9011;
import com.songsy.springboot.rabbitmq.common.OrderMO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.plugin2.message.Message;

/**
 * @author songsy
 * @date 2020/4/7 11:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitMqApplicationPort9011.class)
public class MqDirectSenderTest {

    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 给错误交换器发消息，不会报错
     */
    @Test
    public void test0() {
        OrderMO orderMO = new OrderMO();
        orderMO.setOrderNo("错误交换器");
        rabbitMessagingTemplate.convertAndSend("无名交换器", "queue_submit_order", orderMO);

    }

    /**
     * 给错误的路由键发消息，不会报错
     */
    @Test
    public void test1() {
        OrderMO orderMO = new OrderMO();
        orderMO.setOrderNo("给错误的路由键发消息");
        rabbitTemplate.convertAndSend("错误exchange_submit_order", "错误routingKey", orderMO);
    }


    /**
     * 正确
     */
    @Test
    public void test2() {
        OrderMO orderMO = new OrderMO();
        orderMO.setOrderNo("0");
        // String payload = JSON.toJSONString(orderMO);
        rabbitMessagingTemplate.convertAndSend("exchange_submit_order", "routing_key_submit_order", orderMO);
    }



    /**
     * 连续发10条消息
     */
    @Test
    public void test3() {
        for (int i = 10; i < 200; i ++) {
            OrderMO orderMO = new OrderMO();
            orderMO.setOrderNo(String.valueOf(i));
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            // String payload = JSON.toJSONString(orderMO);
            rabbitMessagingTemplate.convertAndSend("exchange_submit_order", "routing_key_submit_order", orderMO);
        }
    }

    /**
     * 模拟消费端抛异常，消息会进重试队列的情况
     */
    @Test
    public void test4() {
        OrderMO orderMO = new OrderMO();
        orderMO.setOrderNo("1");
        // String payload = JSON.toJSONString(orderMO);
        rabbitMessagingTemplate.convertAndSend("exchange_submit_order", "routing_key_submit_order", orderMO);
    }

    /**
     * 模拟消费端抛异常没有try catch，所以会一直刷
     */
    @Test
    public void test5() {
        OrderMO orderMO = new OrderMO();
        orderMO.setOrderNo("1");
        // String payload = JSON.toJSONString(orderMO);
        rabbitMessagingTemplate.convertAndSend("exchange_submit_order", "routing_key_submit_order_exception", orderMO);
    }


}
