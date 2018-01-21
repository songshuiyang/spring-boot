package com.ecut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.WebApplicationInitializer;

/**
 * springboot会自动加载启动类所在包下及其子包下的所有组件
 */
@SpringBootApplication
public class Application  extends SpringBootServletInitializer implements WebApplicationInitializer {

    /**
     * 配置 Spring Mvc
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception{
        SpringApplication.run(Application.class,args);
    }
}
