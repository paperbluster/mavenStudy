package com.example.spring.base;

/**
 * @author wanjun
 * @create 2022-10-04 18:06
 */
public interface DbSaveCacheService {
    public <T> void saveModels(final EntityCache<T> entityCache);

    public int handleSaveQueue(boolean execute) throws Exception;

    public int getSaveQueueSize();
}
