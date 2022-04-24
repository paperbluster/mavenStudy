package com.example.spring.wrapper;


import com.example.spring.entity.Client;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author wanjun
 * @create 2022-04-19 10:09
 */
public interface UserMapper {

    @Insert("insert into client values(1,'jojo','monster')")
    void insertOne();

    @Select("select * from client where client_id = #{name}")
    public Client find(int clientId);
}
