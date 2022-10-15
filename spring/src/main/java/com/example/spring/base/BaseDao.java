package com.example.spring.base;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author wanjun
 * @create 2022-10-04 19:42
 */
public class BaseDao extends SqlSessionDaoSupport {
    @Resource(name = "sqlSession")
    protected org.mybatis.spring.SqlSessionTemplate sqlSession;

    @PostConstruct
    public void initDao(){
        super.setSqlSessionTemplate(sqlSession);
    }


}
