package com.songsy.springboot.rocketmq.v2.order;

import com.songsy.springboot.rocketmq.v2.OrderBO;
import com.songsy.springboot.rocketmq.v2.TransactionRocketTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author songsy
 * @date 2020/4/25 11:03
 */
@RestController
@RequestMapping("api/order")
public class OrderController {

    private AtomicLong atomicLong = new AtomicLong(0);

    @Autowired
    private TransactionRocketTemplate transactionRocketTemplate;

    @RequestMapping("submit")
    public Object submit(int type, String good) {
        OrderBO orderBO = new OrderBO();
        orderBO.setOrderNo(String.valueOf(atomicLong.getAndIncrement()));
        orderBO.setGood(good);
        orderBO.setType(type);
        String transactionId = UUID.randomUUID().toString();
        orderBO.setTransactionId(transactionId);
        // 发送半消息
        Message message = MessageBuilder.withPayload(orderBO).setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId).build();
        transactionRocketTemplate.sendMessageInTransaction(
                "transaction-topic",
                message,
                null);
        return "成功";
    }

}
