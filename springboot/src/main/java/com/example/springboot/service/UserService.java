package com.example.springboot.service;

import com.example.springboot.mapper.UserMapper;
import org.apache.ibatis.annotations.Insert;
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
}
