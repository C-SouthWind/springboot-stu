package com.chj.mapper;

import com.chj.pojo.UserPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//这个注解表示了这是一个mabatis的mapper类
@Mapper
@Repository
public interface UserMapper {

    List<UserPojo> queryUsers();

    UserPojo queryUserById(@Param("id") Integer id);

    Integer addUser(UserPojo userPojo);

    Integer updateUser(UserPojo userPojo);

    Integer deleteUser(@Param("id") Integer id);
}
