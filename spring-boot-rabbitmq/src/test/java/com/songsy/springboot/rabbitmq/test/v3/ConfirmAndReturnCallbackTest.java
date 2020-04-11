package com.songsy.springboot.rabbitmq.test.v3;

import com.songsy.springboot.rabbitmq.RabbitMqApplicationPort9011;
import com.songsy.springboot.rabbitmq.common.OrderMO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author songshuiyang
 * @date 2020/4/11 23:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitMqApplicationPort9011.class)
public class ConfirmAndReturnCallbackTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 给错误的交换器及路由键发消息
     */
    @Test
    public void test1() {
        OrderMO orderMO = new OrderMO();
        orderMO.setOrderNo("给错误的交换器及路由键发消息");
        rabbitTemplate.convertAndSend("错误exchange_submit_order", "错误queue_submit_order", orderMO);
    }


    /**
     * 给错误的交换器发消息
     */
    @Test
    public void test2() {
        OrderMO orderMO = new OrderMO();
        orderMO.setOrderNo("给错误的交换器发消息");
        rabbitTemplate.convertAndSend("错误exchange_submit_order", "queue_submit_order", orderMO);
    }

    /**
     * 给错误的路由键发消息
     */
    @Test
    public void test3() {
        OrderMO orderMO = new OrderMO();
        orderMO.setOrderNo("给错误的路由键发消息");
        rabbitTemplate.convertAndSend("exchange_submit_order", "错误queue_submit_order", orderMO);
    }

    /**
     * 给正确的路由键发消息
     */
    @Test
    public void test4() {
        OrderMO orderMO = new OrderMO();
        orderMO.setOrderNo("给正确的路由键发消息");
        rabbitTemplate.convertAndSend("exchange_submit_order", "queue_submit_order", orderMO);
    }

}
