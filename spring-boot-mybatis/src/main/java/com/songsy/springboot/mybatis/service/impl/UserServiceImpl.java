package com.songsy.springboot.mybatis.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsy.springboot.mybatis.entity.UserDO;
import com.songsy.springboot.mybatis.mapper.UserMapper;
import com.songsy.springboot.mybatis.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author songsy
 * @date 2019/11/1 10:11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Override
    public boolean save(UserDO entity) {
        return super.save(entity);
    }

    @Override
    public List<UserDO> getUserList() {
        return baseMapper.selectList(Wrappers.<UserDO>lambdaQuery());
    }

}
