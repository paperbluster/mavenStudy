package com.example.spring.base;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author wanjun
 * @create 2022-10-04 18:09
 */
@Component
public class DbSaveCacheImpl<PK extends Comparable<PK>& Serializable,T extends BaseModel<PK>> implements DbSaveCacheService {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(DbSaveCacheImpl.class);
    protected static Map<String, EntityCache<?>> saveSet = new ConcurrentHashMap<>();
    private static AtomicInteger toSaveCount = new AtomicInteger();
    private ReadWriteLock saveSetLock = new ReentrantReadWriteLock();

    @Autowired
    @Qualifier("commonDaoImpl")
    private CommonDao commonDao;

    @Override
    public <T> void saveModels(final EntityCache<T> entityCache) {
        Collection<T> entities = entityCache.getEntities();
        if (entities != null && !entities.isEmpty()) {
            String key = "";
            for (T entity : entities) {
                if (entity instanceof BaseModel) {
                    BaseModel<?> baseModel = (BaseModel<?>) entity;
                    key = entity.getClass().getSimpleName() + baseModel.getId();
                    break;
                }
            }
            if (key.isEmpty()) {
                LOGGER.error("warning！put saveData to saveSet error:key is empty");
            }
            saveSetLock.writeLock().lock();
            try {
                saveSet.put(key, entityCache);
            } catch (Exception e) {
                LOGGER.error("put saveData to saveSet error:" + e);
            } finally {
                saveSetLock.writeLock().unlock();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public int handleSaveQueue(boolean execute) throws Exception {
        List<EntityCache<?>> tempList = new ArrayList<EntityCache<?>>();
        saveSetLock.writeLock().lock();
        try {
            tempList.addAll(saveSet.values());
            saveSet.clear();
        } catch (Exception e) {
            LOGGER.error("save saveSet error:" + e);
        } finally {
            saveSetLock.writeLock().unlock();
        }
        if (tempList.size() > 0) {
            long now = System.currentTimeMillis();
            toSaveCount.incrementAndGet();
            if (commonDao == null) {
                LOGGER.error("save saveSet error:commonDao is null");
            } else {
                for (EntityCache<?> saveModel : tempList) {
                    Collection<T> entities = (Collection<T>) saveModel.getEntities();
                    commonDao.update(entities);
                }
            }
            toSaveCount.decrementAndGet();
            LOGGER.debug("save count:" + tempList.size() + " cost millionsecond:" + (System.currentTimeMillis()));
        }
        int saveCount = tempList.size();
        tempList.clear();
        return saveCount;
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    public void saveEntitiesByTime() throws Exception {
        this.handleSaveQueue(true);
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void saveSetSize() {
        saveSetLock.readLock().lock();
        try {
            LOGGER.info("the current time the save set size=" + saveSet.size());
        } finally {
            saveSetLock.readLock().unlock();
        }
    }

    @Override
    public int getSaveQueueSize() {
        saveSetLock.readLock().lock();
        try {
            return saveSet.size() + toSaveCount.intValue();
        } finally {
            saveSetLock.readLock().unlock();
        }
    }


}
