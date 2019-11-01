package com.songsy.springboot.basic.core.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 继承 HandlerInterceptorAdapter 实现自定义拦截器
 * 计算处理时间
 * @author songshuiyang
 * @date 2018/1/21 15:17
 */
public class RequestHandlerIntercepor extends HandlerInterceptorAdapter {

    private final static Logger logger = LoggerFactory.getLogger(RequestHandlerIntercepor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime",startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (Long)request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        logger.info("本次请求处理时间为:" + new Long(endTime - startTime) + "ms");
        request.removeAttribute("startTime");
        request.setAttribute("handingTime",endTime - startTime);

    }
}
