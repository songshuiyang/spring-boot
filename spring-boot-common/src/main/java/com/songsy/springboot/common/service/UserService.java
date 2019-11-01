package com.songsy.springboot.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.songsy.springboot.common.entity.User;

import java.util.List;

/**
 * @author songsy
 * @date 2019/11/1 10:11
 */
public interface UserService extends IService<User> {

    /**
     * 保存用户信息
     * @param entity
     * @return
     */
    @Override
    boolean save(User entity);

    /**
     * 查询全部用户信息
     * @return
     */
    List<User> getUserList();

}
