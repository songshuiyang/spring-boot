package com.songsy.springboot.api.limit.controller;

import com.songsy.springboot.api.limit.aspect.annotation.ApiLimit;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author songsy
 * @date 2019/11/1 18:30
 */
@RestController
public class UserController {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 10 秒中，可以访问5次
     * @return
     */
    @ApiLimit(key = "test", time = 10, count = 5)
    @GetMapping("/test")
    public String luaLimiter() {
        // 简单测试方法
        RedisAtomicInteger entityIdCounter = new RedisAtomicInteger("counter", redisTemplate.getConnectionFactory());
        String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
        return date + " 累计访问次数：" + entityIdCounter.getAndIncrement();
    }

}
