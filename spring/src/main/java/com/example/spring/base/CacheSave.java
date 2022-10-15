package com.example.spring.base;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wanjun
 * @create 2022-10-04 18:56
 */
public class CacheSave implements Runnable {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(DbServiceImpl.class);

    @Override
    public void run() {
        DbSaveCacheService dbSaveCacheService = (DbSaveCacheService) MyApplication.context.getBean(DbSaveCacheImpl.class);
        try {
            while (true) {
                try {
                    dbSaveCacheService.handleSaveQueue(false);
                } catch (Exception e) {
                    LOGGER.error(e);
                }
                Thread.sleep(5 * 60 * 1000L);
            }
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
    }
}
