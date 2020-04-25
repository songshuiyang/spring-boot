package com.songsy.springboot.rocketmq.test.v0;

import com.songsy.springboot.rocketmq.vo.ProducerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

/**
 * @author songsy
 * @date 2020/4/23 9:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RocketmqDemoApplicationTests {


    @Autowired
    private ProducerService producerService;

    @Test
    public void test1() {
        boolean result = producerService.send("my-topic-v0", "my-tags-v0", "Hello RocketMQ my-tags-v0");
        assertTrue(result);
    }


    @Test
    public void test2() {
        boolean result = producerService.send("my-topic-v0", "my-tags-v1", "Hello RocketMQ my-tags-v1");
        assertTrue(result);
    }

}
