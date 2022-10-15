package com.example.spring.base;

/**数据缓存服务接口
 * @author wanjun
 * @create 2022-10-04 14:48
 */
public interface DBService<T> {
    void put2DbServiceQueue(T obj);
}
