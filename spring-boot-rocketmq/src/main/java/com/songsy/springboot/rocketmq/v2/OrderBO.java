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
     * 模拟各种情况的订单
     * 1、正常场景
     * 2、订单提交失败的场景
     * 3、扣减库存失败的场景
     * 4、回查事务 检查到事务执行成功
     * 5、回查事务 检查到事务执行失败
     */
    private int type;
    /**
     * transactionId
     */
    private String transactionId;
}
