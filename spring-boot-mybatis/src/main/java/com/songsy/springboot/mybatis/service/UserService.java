package com.songsy.springboot.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.songsy.springboot.mybatis.entity.UserDO;

import java.util.List;

/**
 * @author songsy
 * @date 2019/11/1 10:11
 */
public interface UserService extends IService<UserDO> {

    /**
     * 保存用户信息
     * @param entity
     * @return
     */
    @Override
    boolean save(UserDO entity);

    /**
     * 查询全部用户信息
     * @return
     */
    List<UserDO> getUserList();

}
