package com.ecut.service.impl;

import com.ecut.dao.UserDao;
import com.ecut.domain.User;
import com.ecut.service.UserService;
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
