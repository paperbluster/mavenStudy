package com.example.spring.wrapper;


import com.example.spring.entity.Client;
import org.apache.ibatis.annotations.*;

import java.util.Map;

/**
 * @author wanjun
 * @create 2022-04-19 10:09
 */
public interface UserMapper {

    @Insert("insert into client values(1,'jojo','monster')")
    void insertOne();

    @Insert("insert into client(client_id,name,type) values(#{client},#{name},#{type})")
    void insertp(@Param("client")int client,@Param("name")String name,@Param("type")String type);

    @Select("select * from client where client_id = #{name}")
    public Client find(int clientId);

    //@ResultMap(value="aaa")

//    @Results(id = "aaa",value = {
//            @Result(column = "client_id",property = "clientId",id = true),
//            @Result(column = "name",property = "name"),
//            @Result(column = "type",property = "type")
//    }
//    )
    @Select("select client_id,name,type from client where client_id = #{name}")
    public Map<String,Object>  findDetail(int clientId);
}
