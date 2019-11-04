package com.songsy.springboot.sharding.controller;

import com.songsy.springboot.mybatis.entity.UserDO;
import com.songsy.springboot.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author songsy
 * @date 2019/11/1 10:12
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/list")
    public List<UserDO> list() {
        return userService.getUserList();
    }

    @PostMapping("/user/insert")
    public Boolean insert(@RequestBody UserDO userDO) {
        return userService.save(userDO);
    }

}
