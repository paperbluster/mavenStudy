package com.example.spring.service;


import com.example.spring.entity.Client;
import com.example.spring.wrapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

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
        //userMapper.insertOne();
        Client client=new Client();
        client.getMap().put(1,1);
        client.getMap().put(2,2);
        client.getMap().put(3,3);
        client.setClientId(222);
        client.setName("ss");
        userMapper.insertp(client.getClientId(), client.getName(), client.parse());
        //throw new NullPointerException();
    }

    public String Find(){
        //Client vo=userMapper.find(1);
        Map<String,Object> map=userMapper.findDetail(222);
        Client vo=new Client();
        return vo.toString();
    }
}
