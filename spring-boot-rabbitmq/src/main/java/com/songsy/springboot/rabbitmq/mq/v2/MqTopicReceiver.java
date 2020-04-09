package com.songsy.springboot.rabbitmq.mq.v2;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
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
public class MqTopicReceiver {

    private Logger log = LoggerFactory.getLogger(MqTopicReceiver.class);

    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;

    /**
     * 发送消息 topic模式
     * @param channel
     * @param message
     * @param deliveryTag
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue_send_message_channel_1", durable = "true"),
            exchange = @Exchange(value = "exchange_send_message_topic_model", type = ExchangeTypes.TOPIC, durable = "true"),
            key = {"topic.#","other.#"}))
    public void rabbitMessageProcess1(Channel channel, Message message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        String messageString = new String(message.getBody());
        log.info("收到发送消息topic渠道1mq消息:{} deliveryTag:{} ", messageString, deliveryTag);
        channel.basicAck(deliveryTag, false);
    }


    /**
     * 发送消息 topic模式
     * @param channel
     * @param message
     * @param deliveryTag
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue_send_message_channel_2", durable = "true"),
            exchange = @Exchange(value = "exchange_send_message_topic_model", type = ExchangeTypes.TOPIC, durable = "true"),
            key = "topic.#"))
    public void rabbitMessageProcess2(Channel channel, Message message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        String messageString = new String(message.getBody());
        log.info("收到发送消息topic渠道2mq消息:{} deliveryTag:{} ", messageString, deliveryTag);
        channel.basicAck(deliveryTag, false);
    }




}
