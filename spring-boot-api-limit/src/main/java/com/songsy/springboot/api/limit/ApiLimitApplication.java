package com.songsy.springboot.api.limit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * API 接口限流demo
 *
 * @author songsy
 * @date 2019/11/1 18:03
 */
@SpringBootApplication
public class ApiLimitApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiLimitApplication.class, args);
    }

}
