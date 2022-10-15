package com.example.spring.base;

import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author wanjun
 * @create 2022-10-04 20:09
 */
public interface CacheDao {

    <T> T get(String key);

    <T> Map<String, T> get(String... key) throws TimeoutException, InterruptedException, MemcachedException;

    boolean set(String key, Object o) throws TimeoutException, InterruptedException, MemcachedException;

    boolean delete(String key) throws TimeoutException, InterruptedException, MemcachedException;

    boolean replace(String key, Object o, int exp) throws TimeoutException, InterruptedException, MemcachedException;

    boolean set(String key, Object o, int exp) throws TimeoutException, InterruptedException, MemcachedException;

    <T> GetsResponse<T> getResponse(String key) throws TimeoutException, InterruptedException, MemcachedException;

    boolean cas(String key, int exp, Object value, long cas) throws TimeoutException, InterruptedException, MemcachedException;

    boolean add(String key, int exp, Object value) throws TimeoutException, InterruptedException, MemcachedException;

    <T> boolean cas(String keys, int exp, T newValue) throws TimeoutException, InterruptedException, MemcachedException;

    boolean touch(String key);
}
