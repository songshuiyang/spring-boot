package com.songsy.springboot.rabbitmq.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义RabbitTemplate
 *
 * @author songshuiyang
 * @date 2020/4/11 23:36
 */
@Configuration
public class RabbitTemplateConfig {

    private Logger log = LoggerFactory.getLogger(RabbitTemplateConfig.class);

    /**
     * confrim回调能检测到消息是否到达 broker，通过ack变量来判断，但不能保证消息准确投递到目标 queue
     *
     */
    final RabbitTemplate.ConfirmCallback confirmCallback= new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            log.info("ConfirmCallback >> correlationData:{}, ack:{}, cause:{}", correlationData , ack, cause);
            if(!ack){
                log.info("异常处理....");
            }
        }
    };

    /**
     * return回调能检测到消息是否到达 queue，如果没有到达指定队列就会触发下面的逻辑
     */
    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText, String exchange, String routingKey) {
            log.info("ReturnCallback >> return exchange: {}, routingKey: {}, replyCode: {}, replyText: {}", exchange, routingKey, replyCode, replyText);
        }
    };


    @Bean(name = "rabbitTemplate")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        // 开启 mandatory
        template.setMandatory(true);
        template.setConfirmCallback(confirmCallback);
        template.setReturnCallback(returnCallback);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

}
