package com.songsy.springboot.sharding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author songsy
 * @date 2019/11/1 9:39
 */
@SpringBootApplication(scanBasePackages = "com.songsy.springboot")
public class ShardingSphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingSphereApplication.class, args);
    }
}
