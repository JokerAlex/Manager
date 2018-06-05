package com.manager.service;


import com.manager.dao.UserMapper;
import com.manager.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private UserMapper userMapper;

    @Autowired
    public LoginService(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    public User checkUser(String userName, String password){
        boolean isNameNull = !userName.trim().equals("") && userName != null;
        boolean isPasswordNull = !password.trim().equals("") && password != null;
        if ( isNameNull && isPasswordNull){
            return userMapper.checkUser(userName,password);
        }
        return null;
    }
}
