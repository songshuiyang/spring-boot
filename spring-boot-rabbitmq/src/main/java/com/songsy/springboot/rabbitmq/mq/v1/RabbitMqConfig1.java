package com.songsy.springboot.rabbitmq.mq.v1;

import com.songsy.springboot.rabbitmq.common.OrderMO;
import com.songsy.springboot.rabbitmq.mq.v2.MqDirectReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author songsy
 * @date 2019/11/4 16:08
 */
@Configuration
public class RabbitMqConfig1 {

    private Logger log = LoggerFactory.getLogger(RabbitMqConfig1.class);

    /**
     * 声明队列
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue("queue_submit_order", true);
    }
    /**
     * 声明Direct交换器
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("exchange_submit_order");
    }
    /**
     * 绑定Exchange和queue 正确地将消息路由到指定的Queue
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with("routing_key_submit_order");
    }


    /**
     * 消费者
     * @param orderMO
     */
//    @RabbitListener(queues = "queue_submit_order")
//    public void receiveTopic1(OrderMO orderMO) {
//        log.info("receive queue message: " + orderMO);
//    }

}
