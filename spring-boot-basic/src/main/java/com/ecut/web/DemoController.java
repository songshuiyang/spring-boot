package com.ecut.web;

import com.ecut.domain.User;
import com.ecut.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
public class DemoController {

    @Resource
    private UserService userService;

    /**
     * 测试modelview
     * @return
     */
    @RequestMapping(value = {"/","/index.html"}) // 可以配置多个映射路径
    public ModelAndView  loginPage(){
        System.out.println("*****************sdf");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg","songshuiyang");
        modelAndView.setViewName("index");
        return modelAndView;
    }

    /**
     * 测试json
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/user"}) // 可以配置多个映射路径
    public User hello(){
        User user = new User();
        user.setUserName("songshuiyang");
        user.setPassword("asdfas");
        user.setCredits(123);
        return user;
    }
    /**
     * 测试String, 会解析成视图
     * @return
     */
    @RequestMapping(value = {"/string"})
    public String string(){
       return "string";
    }

    /**
     * 测试异常处理
     * @return
     */
    @RequestMapping(value = {"/exception"})
    public void exception(){
        throw new NullPointerException("参数错误");

    }
}
