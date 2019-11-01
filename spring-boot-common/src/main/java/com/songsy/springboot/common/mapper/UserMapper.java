package com.songsy.springboot.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.songsy.springboot.common.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author songsy
 * @date 2019/11/1 9:55
 */
public interface UserMapper extends BaseMapper<User> {

    @Update("update user set name = #{name} where id = #{id}")
    int updateUser(@Param("name") String name, @Param("id") int id);

    @Delete("delete from user where id = #{id}")
    int deleteUser(int id);

    @Select("select id, name from user where id = #{id}")
    User findUser(@Param("id") int id);

}
