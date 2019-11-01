package com.songsy.springboot.sharding.test;

import com.songsy.springboot.common.entity.User;
import com.songsy.springboot.common.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author songsy
 * @date 2019/11/1 11:19
 */
@Slf4j
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 新增数据
     */
    @Test
    public void test0 () {
        for (int i= 0 ; i< 20; i++) {
            User user = new User();
            user.setName("");
            user.setId(i);
            user.setAge(i + 10);
            userMapper.insert(user);
        }
    }
    /**
     * 删除数据
     */
    @Test
    public void test1 () {
        userMapper.deleteUser(1);
    }
    /**
     * 修改数据
     */
    @Test
    public void test2 () {
        userMapper.updateUser("20", 2);
    }

    /**
     * 查找数据
     */
    @Test
    public void test3 () {
        log.info("" +  userMapper.findUser(9));
    }

}
