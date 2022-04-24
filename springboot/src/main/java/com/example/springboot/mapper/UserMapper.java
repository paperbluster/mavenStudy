package com.example.springboot.mapper;

import com.example.springboot.entity.Client;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author wanjun
 * @create 2022-04-19 10:09
 */
// 用这个注解MapperScan扫描全部相关mapper路径，不需要用mapper注解
//@Mapper
public interface UserMapper {

    @Insert("insert into client values(1,'jojo','monster')")
    void insertOne();

    @Select("select * from client where client_id = #{name}")
    public Client find(int clientId);

    @Select("select * from client where client_id = #{clientId} and type = #{type}")
    public Client findAll(@Param("clientId")int clientId, @Param("type")String type);
}
