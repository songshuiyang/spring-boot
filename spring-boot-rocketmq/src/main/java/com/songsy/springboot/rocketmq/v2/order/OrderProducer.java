package com.songsy.springboot.rocketmq.v2.order;

import com.alibaba.fastjson.JSONObject;
import com.songsy.springboot.rocketmq.v2.OrderBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

/**
 * @author songsy
 * @date 2020/4/25 11:07
 */
@Slf4j
@Component
@RocketMQTransactionListener(rocketMQTemplateBeanName = "transactionRocketTemplate")
public class OrderProducer implements RocketMQLocalTransactionListener {

    /**
     * 事务的二次确认 执行本地事务
     * @param message
     * @param o
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(org.springframework.messaging.Message message, Object o) {
        OrderBO orderBO = JSONObject.parseObject( (byte [])message.getPayload() , OrderBO.class);
        try {
            if (orderBO.getType() == 2) {
                throw new Exception("订单提交异常");
            }
            if (orderBO.getType() == 4 || orderBO.getType() == 5) {
                log.info("模拟事务回查的情况:" + orderBO);
                return RocketMQLocalTransactionState.UNKNOWN;
            }
            log.info("订单提交成功:" + orderBO);
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.error("订单提交异常:" + orderBO, e);
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 回查事务
     * @param message
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        OrderBO orderBO = JSONObject.parseObject( (byte [])message.getPayload() , OrderBO.class);
        if (orderBO.getType() == 4) {
            log.info("回查事务 检查到事务执行成功:" + orderBO);
            return RocketMQLocalTransactionState.COMMIT;
        }
        if(orderBO.getType() == 5) {
            log.info("回查事务 检查到事务执行失败:" + orderBO);
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        return RocketMQLocalTransactionState.UNKNOWN;
    }
}
