package com.example.spring.base;

/**实体标识接口，用于告知锁创建器具体实体类是实体对象
 * @author wanjun
 * @create 2022-10-04 14:35
 */
public interface IEntity<T extends Comparable> {

    /**
     * 获取实体标识
     * @return
     */
    T getIdentity();

}
