package com.songsy.springboot.rabbitmq.test;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author songshuiyang
 * @date 2020/4/4 12:07
 */
public class ConnectionFactoryTest {


    static final String EXECHANGE_NAME = "20200404_exchange";
    static final String ROUTING_NAME = "20200404_routing_key";
    static final String QUEUE_NAME = "20200404_queue";
    static final String QUEUE_NAME_2 = "20200404_queue_2";
    static final String QUEUE_NAME_3 = "20200404_queue_3";
    static final String QUEUE_NAME_4 = "20200404_queue_有过期时间队列";
    static final String QUEUE_NAME_5 = "20200404_queue_有过期时间队列2";

    static final String HOST = "111.230.226.158";
    static final Integer PROT = 5672;


    private ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("songsy");
        factory.setPassword("songsy");
        //factory.setVirtualHost();
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
        channel.queueDeclare(QUEUE_NAME_2, true, false, false, null);
        channel.queueDeclare(QUEUE_NAME_3, true, false, false, null);
        // 交换器 路由键 队列绑定
        channel.queueBind(QUEUE_NAME,EXECHANGE_NAME, ROUTING_NAME);
        channel.queueBind(QUEUE_NAME_2,EXECHANGE_NAME, ROUTING_NAME);
        channel.queueBind(QUEUE_NAME_3,EXECHANGE_NAME, ROUTING_NAME);

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

    /**
     * 消费者接受消息（推模式）
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        Address [] addresses = new Address[]{new Address(HOST, PROT)};

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("songsy");
        factory.setPassword("songsy");
        // 与生产者的不一样
        Connection connection = factory.newConnection(addresses);
        Channel channel = connection.createChannel();
        channel.basicQos(1);// 最多接受未被ack的消息个数

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("收到消息："  + new String(body));
       /*         try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                // 显示ack
//                if (true) {
//                    throw new NullPointerException();
//                }
                //channel.basicAck(envelope.getDeliveryTag(), false);

            }
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
        TimeUnit.SECONDS.sleep(5000);
        channel.close();
        connection.close();
    }

    /**
     * 消费者接受消息（拉模式）
     * @throws Exception
     */
    @Test
    public void test3() throws Exception {
        Address [] addresses = new Address[]{new Address(HOST, PROT)};

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("songsy");
        factory.setPassword("songsy");
        // 与生产者的不一样
        Connection connection = factory.newConnection(addresses);
        Channel channel = connection.createChannel();
        while (true) {
            GetResponse resp = channel.basicGet(QUEUE_NAME, false);
            if (resp == null) {
                System.out.println("Get Nothing!");
                TimeUnit.MILLISECONDS.sleep(1000);
            } else {
                String message = new String(resp.getBody(), "UTF-8");
                System.out.printf(" [    %2$s<===](%1$s) %3$s\n", "Receiver", QUEUE_NAME, message);
                TimeUnit.MILLISECONDS.sleep(500);
            }
        }

    }

    /**
     * 测试过期时间
     * @throws Exception
     */
    @Test
    public void test4() throws Exception {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        Map<String, Object> maps = new HashMap<>();
        maps.put("x-message-ttl",6000000);


        // 创建一个direct持久化非自动删除的交换器
        channel.exchangeDeclare(EXECHANGE_NAME, "direct", true, false, null);
        // 创建一个持久化队列
        channel.queueDelete(QUEUE_NAME_5);
        channel.queueDeclare(QUEUE_NAME_5, true, false, false, maps);


        // 交换器 路由键 队列绑定
        channel.queueBind(QUEUE_NAME_5,EXECHANGE_NAME, ROUTING_NAME);

        for (int i = 0 ; i< 5; i++) {
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

    /**
     * 死信队列（也可以当作延迟队列）
     * @throws Exception
     */
    @Test
    public void test5() throws Exception {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        String exchangeNameDlx = "死信-dlx交换器";
        String exchangeNameNormal = "死信-正常交换器";

        String queueNameDlx = "死信-dlx队列";
        String queueNameNormal = "死信-正常队列";

        String routingKey = "死信队列-routingKey";

        channel.exchangeDeclare(exchangeNameDlx, "direct", true, false, null);
        channel.exchangeDeclare(exchangeNameNormal, "fanout", true, false, null);

        Map<String, Object> maps = new HashMap<>();
        maps.put("x-message-ttl", 10000);
        maps.put("x-dead-letter-exchange", exchangeNameDlx);
        maps.put("x-dead-letter-routing-key", routingKey);

        channel.queueDeclare(queueNameNormal, true, false, false, maps);
        channel.queueBind(queueNameNormal,exchangeNameNormal, "rk");

        channel.queueDeclare(queueNameDlx, true, false, false, null);
        channel.queueBind(queueNameDlx,exchangeNameDlx, routingKey);


        for (int i = 0 ; i< 1; i++) {
            String message = "第" + i + "条死信队列消息";
            // 发送一条消息
            channel.basicPublish(exchangeNameNormal,
                    "rk",
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


    /**
     * 延迟队列消费
     * @throws Exception
     */
    @Test
    public void test6() throws Exception {
        Address [] addresses = new Address[]{new Address(HOST, PROT)};

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("songsy");
        factory.setPassword("songsy");
        String queueNameDlx = "死信-dlx队列";
        // 与生产者的不一样
        Connection connection = factory.newConnection(addresses);
        Channel channel = connection.createChannel();
        channel.basicQos(5);// 最多接受未被ack的消息个数

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("收到消息："  + new String(body));
       /*         try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                // 显示ack
//                if (true) {
//                    throw new NullPointerException();
//                }
                channel.basicAck(envelope.getDeliveryTag(), false);

            }
        };
        boolean autoAck = false;
        channel.basicConsume(queueNameDlx, autoAck, consumer);
        TimeUnit.SECONDS.sleep(5000);
        channel.close();
        connection.close();
    }

}
