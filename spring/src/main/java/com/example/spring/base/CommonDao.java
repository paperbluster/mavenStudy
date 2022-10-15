package com.example.spring.base;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author wanjun
 * @create 2022-10-04 19:08
 */
public interface CommonDao {
    /**
     * 保存实体对象
     * @param entities
     * @param <T>
     */
    <T> void save(T ...entities);

    /**
     * 更新实体对象
     * @param entities
     * @param <T>
     */
    <T> void update(T ...entities);

    /**
     * 更新列表
     * @param entities
     * @param <T>
     */
    <T> void update(Collection<T> entities) throws Exception;

    /**
     * 根据主键id取得实体对象
     * @param id
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T get(Serializable id,Class<T> clazz);

    /**
     * 删除实体
     * @param obj
     * @param <T>
     * @return
     */
    <T> int delete(T obj);

    <T> void insert(Collection<T> entities);
}
