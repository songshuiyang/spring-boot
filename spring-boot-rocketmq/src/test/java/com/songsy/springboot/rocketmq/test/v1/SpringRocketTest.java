package com.songsy.springboot.rocketmq.test.v1;

import com.songsy.springboot.rocketmq.v1.RocketMqProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author songsy
 * @date 2020/4/22 10:05
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringRocketTest {

    @Autowired
    private RocketMqProducer rocketMqProducer;

    @Test
    public void testSendMessage() {
        rocketMqProducer.sendMsg("my-topic","为什么不打印");
    }

}
