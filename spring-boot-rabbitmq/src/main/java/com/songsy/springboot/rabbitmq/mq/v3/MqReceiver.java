package com.songsy.springboot.rabbitmq.mq.v3;

import com.rabbitmq.client.Channel;
import com.songsy.springboot.rabbitmq.mq.v1.RabbitMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;


/**
 * @author songshuiyang
 * @date 2020/4/11 21:23
 */
@Component
public class MqReceiver {

    private Logger logger = LoggerFactory.getLogger(MqReceiver.class);

    /**
     * 声明队列
     * @return
     */
    @Bean
    public Queue onlyOneQqueue() {
        return new Queue("only_one_queue", true);
    }

    /**
     * 消费者 测试只声明一个队列，然后只使用默认的交换器发送消息
     * @param message
     */
    @RabbitListener(queues = "only_one_queue")
    public void receive(Channel channel, Message message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag ) throws Exception {
        logger.info("receive:{}", message);
        // 消息手动确认
        channel.basicAck(deliveryTag, false);
    }

}
