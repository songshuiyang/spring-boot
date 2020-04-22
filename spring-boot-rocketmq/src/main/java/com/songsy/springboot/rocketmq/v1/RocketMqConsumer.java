package com.songsy.springboot.rocketmq.v1;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author songsy
 * @date 2020/4/22 10:03
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "my-topic",
        consumerGroup = "my-group",
        selectorExpression = "*"
)
public class RocketMqConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String msg) {
        log.info("接收到消息 -> " + msg);
    }

}
