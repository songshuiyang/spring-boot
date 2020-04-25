package com.songsy.springboot.rocketmq.v2.good;

import com.songsy.springboot.rocketmq.v2.OrderBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author songsy
 * @date 2020/4/25 17:30
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "transaction-topic",
        consumerGroup = "transaction-group",
        selectorExpression = "*"
)
public class GoodConsumer implements RocketMQListener<OrderBO> {

    @Override
    public void onMessage(OrderBO orderBO) {
        if (orderBO.getType() == 3) {
            log.error("扣减库存异常:"  + orderBO);
            throw new IllegalArgumentException("扣减库存异常");
        }
        log.info("扣减库存成功:"  + orderBO);
    }

}
