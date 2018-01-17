package com.ecut.web;

import com.ecut.domain.User;
import com.ecut.entity.LoginCommand;
import com.ecut.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
        modelAndView.addObject("msg",hello);
        modelAndView.setViewName("login");
        return modelAndView;
    }
    @RequestMapping(value = "/index")
    public String index(){
        return "login";
    }


    @RequestMapping(value = "loginCheck")
    public ModelAndView loginCheck(HttpServletRequest httpServletRequest,LoginCommand loginCommand){
        boolean isValidUser = userService.hasMatchUser(loginCommand.getUserName(),loginCommand.getPassword());
        if(isValidUser){
            User user = userService.findUserByName(loginCommand.getUserName());
            userService.loginSuccess(user);
            user.setLastIp(httpServletRequest.getLocalAddr());
            userService.loginSuccess(user);
            httpServletRequest.getSession().setAttribute("user",user);
            return new ModelAndView("loginSuccess");
        }else{
            return new ModelAndView("login", "error", "用户名或密码错误。");
        }
    }



}
