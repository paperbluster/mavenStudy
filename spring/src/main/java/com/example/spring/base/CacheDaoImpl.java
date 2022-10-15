package com.example.spring.base;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author wanjun
 * @create 2022-10-04 20:19
 */
public class CacheDaoImpl implements CacheDao {
    private static final Logger LOGGER= (Logger) LoggerFactory.getLogger(CacheDaoImpl.class);
    @Resource(name = "memcachedClient")
    protected XMemcachedClient memcachedClient;

    @Override
    public <T> T get(String key) {
        try{
            return memcachedClient.get(key);
        }catch (TimeoutException|InterruptedException|MemcachedException e){
            LOGGER.error("memcache get error");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> Map<String, T> get(String... key) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedClient.get(Arrays.asList(key));
    }

    @Override
    public boolean set(String key, Object o) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedClient.set(key,7*24*60*60,o);
    }

    @Override
    public boolean delete(String key) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedClient.delete(key);
    }

    @Override
    public boolean replace(String key, Object o, int exp) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedClient.replace(key,exp,o);
    }

    @Override
    public boolean set(String key, Object o, int exp) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedClient.set(key,exp,o);
    }

    @Override
    public <T> GetsResponse<T> getResponse(String key) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedClient.gets(key);
    }

    @Override
    public boolean cas(String key, int exp, Object value, long cas) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedClient.cas(key,exp,value,cas);
    }

    @Override
    public boolean add(String key, int exp, Object value) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedClient.add(key,exp,value);
    }

    @Override
    public <T> boolean cas(String keys, int exp, final T newValue) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedClient.cas(keys,exp,new CASOperation<T>(){
            @Override
            public int getMaxTries(){
                return 1;
            }

            @Override
            public T getNewValue(long currentCAS,T currentValue){
                return newValue;
            }
        });
    }

    @Override
    public boolean touch(String key) {
        try{
            return memcachedClient.touch(key,7*24*60*60);
        }catch (TimeoutException|InterruptedException|MemcachedException e){
            LOGGER.error("memcache touch error");
            e.printStackTrace();
        }
        return false;
    }
}
