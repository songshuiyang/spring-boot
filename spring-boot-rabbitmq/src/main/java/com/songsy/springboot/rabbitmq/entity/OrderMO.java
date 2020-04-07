package com.songsy.springboot.rabbitmq.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author songsy
 * @date 2020/4/7 11:01
 */
@Data
public class OrderMO implements Serializable {

    private static final long serialVersionUID = -7128203829971899888L;

    private String orderNo;

}
