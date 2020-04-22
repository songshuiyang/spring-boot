package com.songsy.springboot.rabbitmq.mq.v3;

import com.rabbitmq.client.Channel;
import com.songsy.springboot.rabbitmq.common.OrderMO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 死信队列
 * @author songshuiyang
 * @date 2020/4/12 12:29
 */
@Component
public class MqDeadReceiver {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Bean
    public Queue receiveDeadQueue() {
        return new Queue("测试死信队列-死信队列", true);
    }

    @Bean
    public Queue receiveNormalQueue() {
        Map<String,Object> map = new HashMap<>();
        // 绑定死信队列 通过队列属性设置，队列中所有消息都有相同的过期时间。
        map.put("x-message-ttl", 20000);
        map.put("x-dead-letter-exchange","测试死信队列-dead-exchange");
        map.put("x-dead-letter-routing-key", "测试死信队列-dead-routing-key");
        // return new Queue("",true,false,false,map);

        return QueueBuilder.durable("测试死信队列-正常队列")
                .withArguments(map).build();
    }

    @Bean
    public DirectExchange receiveNormalExchange(){
        return new DirectExchange("测试死信队列-normal-exchange");
    }

    @Bean
    public DirectExchange receiveDeadExchange(){
        return new DirectExchange("测试死信队列-dead-exchange");
    }

    @Bean
    public Binding receiveDeadBinding() {
        return BindingBuilder.bind(receiveDeadQueue()).to(receiveDeadExchange()).with("测试死信队列-dead-routing-key");
    }

    @Bean
    public Binding receiveNormalBinding() {
        return BindingBuilder.bind(receiveNormalQueue()).to(receiveNormalExchange()).with("测试死信队列-normal-routing-key");
    }

    @RabbitListener(queues = "测试死信队列-正常队列")
    public void rabbitMessageProcess1(Channel channel, Message message, OrderMO orderMO, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        String messageString = new String(message.getBody());
        logger.info("收到测试死信队列-正常队列mq消息:{} deliveryTag:{} ", messageString, deliveryTag);
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
            logger.error("异常", e);
            channel.basicReject(deliveryTag,false); // 消息拒绝
            //channel.basicNack(deliveryTag,false,false); // 和basicReject效果一样不过可以批量拒绝消息
        }
    }

}
