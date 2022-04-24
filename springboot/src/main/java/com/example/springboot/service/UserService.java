package com.example.springboot.service;

import com.example.springboot.entity.Client;
import com.example.springboot.mapper.UserMapper;
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

    // 这个注解是事务管理用的只应该给public非读协议使用
    @Transactional
    public void Test(){
        userMapper.insertOne();
        //throw new NullPointerException();
    }

    public String Find(){
        Client vo=userMapper.find(1);
        return vo.toString();
    }

    public String FindMore(){
        Client vo=userMapper.findAll(1,"monster");
        return vo.toString();
    }
}
