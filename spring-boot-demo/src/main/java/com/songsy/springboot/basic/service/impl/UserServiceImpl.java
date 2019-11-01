package com.songsy.springboot.basic.service.impl;

import com.songsy.springboot.basic.dao.UserDao;
import com.songsy.springboot.basic.domain.User;
import com.songsy.springboot.basic.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author songshuiyang
 * @title:
 * @description:
 * @date 2017/8/13 12:09
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public boolean hasMatchUser(String userName, String password) {
        return false;
    }

    @Override
    public User findUserByName(String name) {
        return null;
    }

    @Override
    public void loginSuccess(User user) {

    }
}
