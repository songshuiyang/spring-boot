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
public class MqFanoutReceiver {

    private Logger log = LoggerFactory.getLogger(MqFanoutReceiver.class);

    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;

    /**
     * 发送消息 fanout模式
     * @param channel
     * @param message
     * @param deliveryTag
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue_send_message_channel_1", durable = "true"),
            exchange = @Exchange(value = "exchange_send_message", type = ExchangeTypes.FANOUT, durable = "true")))
    public void rabbitMessageProcess1(Channel channel, Message message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        String messageString = new String(message.getBody());
        log.info("收到发送消息渠道1mq消息:{} deliveryTag:{} ", messageString, deliveryTag);
        channel.basicAck(deliveryTag, false);
    }


    /**
     * 发送消息 fanout模式
     * @param channel
     * @param message
     * @param deliveryTag
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue_send_message_channel_2", durable = "true"),
            exchange = @Exchange(value = "exchange_send_message", type = ExchangeTypes.FANOUT, durable = "true")))
    public void rabbitMessageProcess2(Channel channel, Message message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        String messageString = new String(message.getBody());
        log.info("收到发送消息渠道2mq消息:{} deliveryTag:{} ", messageString, deliveryTag);
        channel.basicAck(deliveryTag, false);
    }




}
