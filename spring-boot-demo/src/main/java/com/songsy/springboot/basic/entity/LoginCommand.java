package com.songsy.springboot.basic.entity;

/**
 * @author songshuiyang
 * @title:
 * @description:
 * @date 2017/8/13 12:14
 */
public class LoginCommand {
    String userName;
    String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginCommand{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
