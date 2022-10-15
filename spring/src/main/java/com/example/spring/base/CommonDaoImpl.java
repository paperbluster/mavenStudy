package com.example.spring.base;


import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author wanjun
 * @create 2022-10-04 19:14
 */
@Component
public class CommonDaoImpl extends BaseDao implements CommonDao {
    private static final Logger LOGGER= (Logger) LoggerFactory.getLogger(CommonDaoImpl.class);
    private static String UPDATE_PREFFIX="update";
    private static String DELETE_PREFFIX="delete";
    private static String INSERT_PREFFIX="insert";

    protected static String getUpdateStatementName(Class<? extends  Object> cls){
        return UPDATE_PREFFIX+cls.getSimpleName();
    }

    protected static String getDeleteStatementName(Class<? extends  Object> cls){
        return DELETE_PREFFIX+cls.getSimpleName();
    }

    protected static String getInsertStatementName(Class<? extends  Object> cls){
        return INSERT_PREFFIX+cls.getSimpleName();
    }

    @Override
    public <T> void save(T... entities) {

    }

    @Override
    public <T> void update(T... entities) {

    }

    public <T> int update(T parameterObject)throws Exception {
        int i = sqlSession.update(getUpdateStatementName(parameterObject.getClass()), parameterObject);
        if (i != 1) {
            BaseModel<?> bm = (BaseModel<?>) parameterObject;
            LOGGER.error("className:" + parameterObject.getClass() + "---parameterObjectId:" + bm.getId());
        }
        return i;
    }

    @Override
    public <T> void update(Collection<T> entities) throws Exception {
        for(Iterator<T> iterator=entities.iterator();iterator.hasNext();){
            T entity=iterator.next();
            try {
                this.update(entity);
            }catch (DataAccessException ex){
                LOGGER.debug("CommonDaoImpl.update() ex ",ex);
            }
        }
    }

    @Override
    public <T> T get(Serializable id, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> int delete(T obj) {
        return sqlSession.delete(getDeleteStatementName(obj.getClass()), obj);
    }

    public <T> int insert(T parameterObject) {
        int i = sqlSession.insert(getInsertStatementName(parameterObject.getClass()), parameterObject);
        return i;
    }

    @Override
    public <T> void insert(Collection<T> entities) {
        for(Iterator<T> iterator=entities.iterator();iterator.hasNext();){
            T entity=iterator.next();
            try {
                this.insert(entity);
            }catch (DataAccessException ex){
                LOGGER.debug("CommonDaoImpl.insert() ex ",ex);
            }
        }
    }
}
