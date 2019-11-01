package com.songsy.springboot.api.limit.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * API限流生效注解
 * @author songsy
 * @date 2019/11/1 18:05
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiLimit {

    /**
     * 限流唯一标示
     *
     * @return
     */
    String key() default "";

    /**
     * 限流时间
     *
     * @return
     */
    int time();

    /**
     * 限流次数
     *
     * @return
     */
    int count();

}
