package com.example.spring.service;


import com.example.spring.entity.Client;
import com.example.spring.wrapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wanjun
 * @create 2022-04-19 10:18
 */
@Component
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void Test(){
        userMapper.insertOne();
        //throw new NullPointerException();
    }

    public String Find(){
        Client vo=userMapper.find(1);
        return vo.toString();
    }
}
