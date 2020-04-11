package com.songsy.springboot.rabbitmq.mq.v2;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.songsy.springboot.rabbitmq.common.OrderMO;
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

/**
 * @author songsy
 * @date 2020/4/7 10:55
 */
@Component
public class MqDirectReceiver {

    private Logger log = LoggerFactory.getLogger(MqDirectReceiver.class);

    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;

    /**
     * 提交订单
     * @param channel
     * @param message
     * @param deliveryTag
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue_submit_order", durable = "true"),
            exchange = @Exchange(value = "exchange_submit_order", type = ExchangeTypes.DIRECT, durable = "true"),
            key = "routing_key_submit_order"))
    public void rabbitMessageProcess1(Channel channel, Message message, OrderMO orderMO, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        String messageString = new String(message.getBody());
        log.info("收到提交订单mq消息:{} deliveryTag:{} ", messageString, deliveryTag);
        // 添加Jackson2JsonMessageConverter bean后直接在方法参数上可以实现反序列化操作，而不用下面这种方式
        // OrderMO orderMO = JSON.parseObject(message.getBody(), OrderMO.class);
        try {
            // 提交订单的处理 模拟异常场景
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
        } catch (Exception e) {
            log.error("[通知]-处理异常:{}", e.getMessage());
            // 转发到重试队列 后续处理
            rabbitMessagingTemplate.convertAndSend("exchange_submit_order","routing_key_submit_order_retry", orderMO);

            // channel.basicReject(deliveryTag,false); // 消息拒绝
            channel.basicNack(deliveryTag,false,false); // 和basicReject效果一样不过可以批量拒绝消息
        }
    }

    /**
     * 提交订单异常队列
     * @param channel
     * @param message
     * @param deliveryTag
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue_submit_order_exception", durable = "true"),
            exchange = @Exchange(value = "exchange_submit_order", type = ExchangeTypes.DIRECT, durable = "true"),
            key = "routing_key_submit_order_exception"))
    public void rabbitMessageProcess2(Channel channel, Message message, OrderMO orderMO,  @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        String messageString = new String(message.getBody());
        log.info("收到订单异常mq消息:{} deliveryTag:{} ", messageString, deliveryTag);
        int result = 12 / 0;
        // 消息手动确认
        channel.basicAck(deliveryTag, false);
    }

    /**
     * 提交订单重试队列
     * @param channel
     * @param message
     * @param deliveryTag
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue_submit_order_retry", durable = "true"),
            exchange = @Exchange(value = "exchange_submit_order", type = ExchangeTypes.DIRECT, durable = "true"),
            key = "routing_key_submit_order_retry"))
    public void rabbitMessageProcess3(Channel channel, Message message, OrderMO orderMO,  @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        String messageString = new String(message.getBody());
        log.info("收到订单重试mq消息:{} deliveryTag:{} ", messageString, deliveryTag);
        try {
            MessageProperties messageProperties = message.getMessageProperties();
            // 消息手动确认
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("[通知]-处理异常:{}", e.getMessage());
            channel.basicNack(deliveryTag,false,false);
        }
    }

}
