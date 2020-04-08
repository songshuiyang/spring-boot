package com.songsy.springboot.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author songsy
 * @date 2019/11/1 16:51
 */
@SpringBootApplication
@ComponentScan("com.songsy.springboot.rabbitmq.common")
public class RabbitMqApplicationPort9011 implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMqApplicationPort9011.class, args);
    }

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        factory.setPort(9011);
    }
}
