package com.ecut.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * description:
 * @author songshuiyang
 * @date 2018/1/17 22:47
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.ecut")
public class WebConfig extends WebMvcConfigurerAdapter {
    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver  viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        return  viewResolver;
    }

    /**
     * 添加静态资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/static/");
        registry.addResourceHandler("/assert/**").addResourceLocations("/WEB-INF/assert/");
    }
}
