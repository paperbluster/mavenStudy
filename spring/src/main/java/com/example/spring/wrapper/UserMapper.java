package com.example.spring.wrapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wanjun
 * @create 2022-04-19 10:09
 */
public interface UserMapper {

    @Insert("insert into client values(1,'jojo','monster')")
    void insertOne();
}
