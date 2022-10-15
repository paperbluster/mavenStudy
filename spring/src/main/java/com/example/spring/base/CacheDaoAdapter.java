package com.example.spring.base;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author wanjun
 * @create 2022-10-04 19:47
 */
@Scope("prototype")
@Repository
public class CacheDaoAdapter {
    private static final Logger LOGGER= (Logger) LoggerFactory.getLogger(CacheDaoAdapter.class);

    @Qualifier("cacheDao")
    @Autowired(required = true)
    protected CacheDao cacheDao;

    private Serialization serialization=new KryoSerialization();

    public CacheDaoAdapter(Serialization serialization){
        this.serialization=serialization;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        try {
            byte[] result = cacheDao.get(key);
            if (null != result) {
                return (T) serialization.deserialize((result));
            }
        } catch (Exception e) {
            try {
                cacheDao.delete(key);
            } catch (TimeoutException | InterruptedException | MemcachedException e1) {
                LOGGER.error("delete key error");
                e1.printStackTrace();
            }
            LOGGER.error("deserialize error");
            e.printStackTrace();
        }
        return null;
    }

    public <T> void set(String key,T obj) {
        byte[] bytes;
        try {
            bytes = serialization.serialize(obj);
            cacheDao.set(key, bytes);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public <T> void set(String key,T obj,int exp) {
        byte[] bytes;
        try {
            bytes = serialization.serialize(obj);
            cacheDao.set(key, bytes,exp);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void delete(String key) {
        try {
            cacheDao.delete(key);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public boolean touch(String key){
        try{
            return cacheDao.touch(key);
        }catch (Exception e){
            try{
                cacheDao.delete(key);
            }catch (TimeoutException|InterruptedException|MemcachedException e1){
                LOGGER.error("delete key error");
                e1.printStackTrace();
            }
            LOGGER.error("deserialize error");
            e.printStackTrace();
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public <T> Map<String,T> get(String... keys) {
        Map<String, T> result = new HashMap<>();
        try {
            Map<String, Object> map = cacheDao.get(keys);
            if (null != map) {
                Iterator keyset = map.keySet().iterator();
                while (keyset.hasNext()) {
                    Map.Entry entry = (Map.Entry) keyset.next();
                    result.put((String) entry.getKey(), (T) serialization.deserialize((byte[]) entry.getValue()));
                }
            }
        } catch (Exception e) {
            LOGGER.error("deserialize error");
            e.printStackTrace();
        }
        return result;
    }
}
