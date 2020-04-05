package com.songsy.springboot.rabbitmq.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;

/**
 * @author songshuiyang
 * @date 2020/4/5 10:19
 */
public class VhostTest {

    static final String EXECHANGE_NAME = "20200405_vhost1_exchange";
    static final String ROUTING_NAME = "20200405_vhost1_routing_key";
    static final String QUEUE_NAME = "20200405_vhost1_queue";

    static final String HOST = "111.230.226.158";
    static final Integer PROT = 5672;

    private ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("songsy1");
        factory.setPassword("songsy1");
        factory.setVirtualHost("vhost1");
        factory.setHost(HOST);
        factory.setPort(PROT);
        return factory;
    }

    private Connection getConnection() throws Exception {
        Connection connection = getConnectionFactory().newConnection();
        return connection;
    }


    /**
     * 生产者发送消息
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        // 创建一个direct持久化非自动删除的交换器
        channel.exchangeDeclare(EXECHANGE_NAME, "direct", true, false, null);
        // 创建一个持久化队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        // 交换器 路由键 队列绑定
        channel.queueBind(QUEUE_NAME,EXECHANGE_NAME, ROUTING_NAME);


        for (int i = 0 ; i< 100; i++) {
            String message = "第" + i + "条消息";
            // 发送一条消息
            channel.basicPublish(EXECHANGE_NAME,
                    ROUTING_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes());
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        channel.close();
        connection.close();

    }

}
