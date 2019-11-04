package com.songsy.springboot.api.limit.aspect;

import com.songsy.springboot.api.limit.aspect.annotation.ApiLimit;
import com.songsy.springboot.api.limit.common.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author songsy
 * @date 2019/11/1 18:06
 * @see ApiLimit 注解AOP
 */
@Slf4j
@Aspect
@Configuration
public class ApiLimitAspect {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    private DefaultRedisScript<Number> redisLuaScript;

    @Around("@annotation(com.songsy.springboot.api.limit.aspect.annotation.ApiLimit)")
    public Object interceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        ApiLimit apiLimit = method.getAnnotation(ApiLimit.class);
        if (apiLimit != null) {
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            String ipAddress = IpUtils.getIpAdrress(request);

            String string = ipAddress + "-" + targetClass.getName() + "- " + method.getName() + "-" + apiLimit.key();
            List<String> keys = Collections.singletonList(string);
            Number number = redisTemplate.execute(redisLuaScript, keys, apiLimit.count(), apiLimit.time());

            if (number != null && number.intValue() != 0 && number.intValue() <= apiLimit.count()) {
                log.info("限流时间段内访问第：{} 次", number.toString());
                return joinPoint.proceed();
            }

        } else {
            return joinPoint.proceed();
        }
        log.error("已经到设置限流次数");
        throw new RuntimeException("已经到设置限流次数");
    }
}
