package com.ecut.service;

import com.ecut.domain.User;

/**
 * @author songshuiyang
 * @title:
 * @description:
 * @date 2017/8/13 12:08
 */
public interface UserService {
    /**
     *
     * @param userName
     * @param password
     * @return
     */
    boolean hasMatchUser(String userName, String password);

    /**
     *
     * @param name
     * @return
     */
    User findUserByName(String name);

    /**
     *
     * @param user
     */
    void loginSuccess(User user);
}
