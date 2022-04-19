package com.example.springboot.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wanjun
 * @create 2022-04-19 10:09
 */
@Mapper
public interface UserMapper {

    @Insert("insert into client values(1,'jojo','monster')")
    void insertOne();
}
