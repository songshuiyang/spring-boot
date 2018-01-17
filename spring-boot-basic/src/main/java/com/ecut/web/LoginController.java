package com.ecut.web;

import com.ecut.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author songshuiyang
 * @title:
 * @description:
 *
 * @date 2017/8/13 12:11
 */
@Controller
public class LoginController {

    // 从 application.properties 中读取配置，如取不到默认值为Hello Shanhy

    @Value("${application.hello:Hello Angel}")
    private String hello;


    @Resource
    private UserService userService;

    @RequestMapping(value = {"/","/index.html"}) // 可以配置多个映射路径
    public ModelAndView  loginPage(){
        System.out.println("*****************sdf");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg","songshuiyang");
        modelAndView.setViewName("index");
        return modelAndView;
    }
    @RequestMapping(value = {"/list"}) // 可以配置多个映射路径
    public String  index(){
        System.out.println("*****************list");
        return "index";
    }
}
