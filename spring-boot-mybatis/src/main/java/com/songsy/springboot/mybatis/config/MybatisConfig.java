package com.songsy.springboot.mybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author songsy
 * @date 2019/11/1 10:21
 */
@Configuration
@MapperScan("com.songsy.springboot.common.mapper")
public class MybatisConfig {

}
