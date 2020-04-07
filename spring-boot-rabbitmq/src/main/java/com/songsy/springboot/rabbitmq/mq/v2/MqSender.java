//package com.songsy.springboot.rabbitmq.mq.v2;
//
//import com.songsy.springboot.rabbitmq.entity.OrderMO;
//import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * @author songsy
// * @date 2020/4/7 11:07
// */
//@Component
//public class MqSender {
//
//    @Autowired
//    private RabbitMessagingTemplate rabbitMessagingTemplate;
//
//    public void send(OrderMO orderMO) {
//        rabbitMessagingTemplate.convertAndSend("exchange_submit_order","queue_submit_order", orderMO);
//    }
//
//}
