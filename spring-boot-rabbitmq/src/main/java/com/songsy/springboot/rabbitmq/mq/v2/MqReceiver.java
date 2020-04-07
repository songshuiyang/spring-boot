package com.songsy.springboot.rabbitmq.mq.v2;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.songsy.springboot.rabbitmq.entity.OrderMO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @author songsy
 * @date 2020/4/7 10:55
 */
@Component
public class MqReceiver {

    private Logger log = LoggerFactory.getLogger(MqReceiver.class);

    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue_submit_order", durable = "true"),
            exchange = @Exchange(value = "exchange_submit_order", type = ExchangeTypes.DIRECT, durable = "true"),
            key = "routing_key_submit_order"))
    public void rabbitMessageProcess(Channel channel, Message message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        String messageString = new String(message.getBody());
        log.info("收到mq消息:{}", messageString);
        OrderMO orderMO = JSON.parseObject(message.getBody(), OrderMO.class);
        try {
            // 提交订单的处理 模拟异常场景
            log.info("提交订单处理{}" , orderMO.getOrderNo());
            if ("1".equals(orderMO.getOrderNo())) {
                throw new IllegalArgumentException("参数异常");
            } else if ("2".equals(orderMO.getOrderNo())) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //
            MessageProperties messageProperties = message.getMessageProperties();
            // 消息手动确认
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            log.error("[通知]-处理异常:{}", e.getMessage());
            //转发到重试队列 后续处理
            rabbitMessagingTemplate.convertAndSend("queue_submit_order_retry", orderMO);
            channel.basicNack(deliveryTag,false,false);
        }
    }


}
