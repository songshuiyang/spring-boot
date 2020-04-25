package com.songsy.springboot.rocketmq.v2;

import lombok.Data;

/**
 * @author songsy
 * @date 2020/4/25 17:12
 */
@Data
public class OrderBO {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 商品名
     */
    private String good;

    /**
     * 默认各种情况的订单
     * 1、
     */
    private int type;

    private String transactionId;
}
