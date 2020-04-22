package com.songsy.springboot.rocketmq.v1;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author songsy
 * @date 2020/4/22 10:03
 */
@Component
public class RocketMqProducer {

    /**
     * 注入rocketMQ的模板
     */
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送消息
     *
     * @param topic
     * @param msg
     */
    public void sendMsg(String topic, String msg) {
        this.rocketMQTemplate.convertAndSend(topic, msg);
    }


}
