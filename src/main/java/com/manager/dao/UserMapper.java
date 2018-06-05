package com.manager.dao;

import com.manager.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User checkUser(@Param("userName") String userName, @Param("password") String password);
}
