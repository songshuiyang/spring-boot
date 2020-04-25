package com.songsy.springboot.rocketmq.v2;

import org.apache.rocketmq.spring.annotation.ExtRocketMQTemplateConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

/**
 * @author songsy
 * @date 2020/4/25 16:47
 */
@Service
@ExtRocketMQTemplateConfiguration(group = "transaction-group", nameServer = "127.0.0.1:9876")
public class TransactionRocketTemplate  extends RocketMQTemplate {

}
