package com.songsy.springboot.basic.web;

import com.songsy.springboot.basic.domain.User;
import com.songsy.springboot.basic.service.UserService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

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

    /**
     * 测试文件上传
     * @param file
     * @return
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public @ResponseBody String upload(MultipartFile file) {
        try {
            FileUtils.writeByteArrayToFile(new File("D:/upload/"+file.getOriginalFilename()),file.getBytes());
            return "ok";
        } catch (IOException e) {
            e.printStackTrace();
            return "wrong";
        }
    }
}
