package com.ecut.core.config.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * ControllerAdvice-控制器建言
 * 注解内部使用@ExceptionHandler、@InitBinder、@ModelAttribute注解的方法应用到所有的 @RequestMapping注解的方法
 * @author songshuiyang
 * @date 2018/1/21 15:37
 */
@ControllerAdvice
@EnableWebMvc
public class ExceptionHandlerAdvice {
    /**
     * 异常的全局处理
     * @param exception
     * @param webRequest
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView exception (Exception exception, WebRequest webRequest) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMsg", exception.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }
    @ModelAttribute
    public void addAttribute (Model model) {
        model.addAttribute("home","额外信息");
    }
    @InitBinder
    public void InitBinder(WebDataBinder webDataBinder){
        webDataBinder.setDisallowedFields("id");
    }
}
