package com.songsy.springboot.common.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsy.springboot.common.entity.User;
import com.songsy.springboot.common.mapper.UserMapper;
import com.songsy.springboot.common.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author songsy
 * @date 2019/11/1 10:11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public boolean save(User entity) {
        return super.save(entity);
    }

    @Override
    public List<User> getUserList() {
        return baseMapper.selectList(Wrappers.<User>lambdaQuery());
    }

}
