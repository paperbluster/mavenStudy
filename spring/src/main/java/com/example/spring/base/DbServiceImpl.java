package com.example.spring.base;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.*;

/**
 * @author wanjun
 * @create 2022-10-04 14:51
 */
public class DbServiceImpl<PK extends Comparable<PK>& Serializable,T extends BaseModel<PK>> implements DBService<T>, ApplicationListener<ContextClosedEvent> {
    /**入库线程池**/
    private static ExecutorService DB_POOL_SERVICE;
    /**logger**/
    private static final Logger LOGGER= (Logger) LoggerFactory.getLogger(DbServiceImpl.class);
    /**入库队列**/
    private static final LinkedBlockingQueue<Runnable> DB_SAVER_QUEUE=new LinkedBlockingQueue<>();

    private static final ConcurrentHashMap<String,BaseModel<?>> DB_OBJECT_WAITING_KEY=new ConcurrentHashMap<>(1000);
    private static LinkedTransferQueue<Runnable> RUNNABLEQUEUE=new LinkedTransferQueue<>();

    /**重试的次数**/
    private static final int MAX_RETRY_COUNT=5;

    // 入库累积次数
    @Autowired(required = false)
    @Qualifier("dbcache.db_accumulate_count")
    private Integer db_accumulate_count=5;

    // 入库线程池容量
    @Autowired(required = false)
    @Qualifier("dbcache.db_pool_capacity")
    private Integer dbPoolSize=5;

    // 入库线程池最大容量
    @Autowired(required = false)
    @Qualifier("dbcache.db_pool_max_capacity")
    private Integer dbPoolMaxSize=16;

    // 空闲线程最大存活时间
    @Autowired(required = false)
    @Qualifier("dbcache.db_pool_keep_alive_time")
    private Integer keepAliveTime=600;

    // 入库对象在队列中的存活时间(60*1000毫秒)
    @Autowired(required = false)
    @Qualifier("dbcache.dbcache.max_block_time_of_entity_cache")
    private Integer entityBlockTime=60000;

    @Autowired(required = false)
    @Qualifier("commonDaoImpl")
    private CommonDao commonDao;

    @Autowired
    private  DbSaveCacheService dbSaveCacheService;

    /**
     * 队列初始化
     */
    @PostConstruct
    void initialize(){
       ThreadGroup threadGroup=new ThreadGroup("缓存模块");
        NamedThreadFactory threadFactory=new NamedThreadFactory(threadGroup,"入库线程池");
        DB_POOL_SERVICE=new ThreadPoolExecutor(dbPoolSize,dbPoolMaxSize,keepAliveTime,TimeUnit.MILLISECONDS,DB_SAVER_QUEUE,threadFactory);
          Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHookRunnable()));
          LOGGER.debug("initialize DB Daemon Thread...");
          String threadName="数据库入库守护线程";
          ThreadGroup group=new ThreadGroup(threadName);
        NamedThreadFactory factory=new NamedThreadFactory(group,threadName);
        Thread thread=factory.newThread(CUSTOMER_TASK);
        thread.setDaemon(true);
        thread.start();
    }

    public class ShutdownHookRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                if (DB_POOL_SERVICE == null) {//线程池不存在
                    break;
                }
                if (DB_POOL_SERVICE.isShutdown()) {//线程池被关闭了
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {//创建日志发生中断异常
                    LOGGER.error(e);
                }
                if (DB_SAVER_QUEUE.isEmpty()) {
                    try {
                        DB_POOL_SERVICE.awaitTermination(15, TimeUnit.SECONDS);
                        DB_POOL_SERVICE.shutdown();
                        break;
                    } catch (InterruptedException e) {
                        LOGGER.error(e);
                    }
                }
            }
        }
    }

    /**
     * 入库消费者线程
     */
    public final Runnable CUSTOMER_TASK=new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    Runnable task = DB_SAVER_QUEUE.take();
                    if (task == null) {
                        LOGGER.debug("submit task.. QUEUE_SIZE:" + DB_SAVER_QUEUE.size());

                    }
                } catch (Exception ex) {
                    ex.getStackTrace();
                    LOGGER.debug("ERROR:{}" + ex.getStackTrace());
                }
            }
        }
    };

    /**
     * 创建入库线程
     */
    private <T> Runnable createTask(final DbCallback callback,final EntityCache<T> entityCache){
        return new Runnable() {
            @Override
            public void run() {
                handleTask(callback,entityCache);
            }
        };
    }

    /**
     * 创建单独入库任务
     */
//    private <T> Runnable createTaskAccumulatively(final DbCallback callback,final String key){
//        return new Runnable() {
//            @Override
//            public void run() {
//                handleTaskAccumulatively(callback,key);
//            }
//        };
//    }

    /**
     * 处理入库及回调事宜
     *
     */
    private <T> void handleTask(final DbCallback callback, final EntityCache<T> entityCache) {
        Collection<T> entities = entityCache.getEntities();
        if (entities != null && !entities.isEmpty()) {
            try {

            } catch (Exception ex) {
                LOGGER.error("执行入库时产生异常", ex);
                if (++entityCache.retryCount < MAX_RETRY_COUNT) {
                    DB_SAVER_QUEUE.add(createTask(callback, entityCache));
                }
                return;
                ;
            }
        }
        if (callback != null) {
            try {
                long curTime = System.currentTimeMillis();
                LOGGER.info("DbServiceImpl.handleTask() doAfter start entities size:" + entities.size());
                callback.doAfter();
                long costTime = System.currentTimeMillis() - curTime;
                LOGGER.info("DbServiceImpl.handleTask() doAfter and cost:" + costTime);
            } catch (Exception ex) {
                LOGGER.error("执行入库时产生异常", ex);
            }
        }
    }

    /**
     * 提交入库请求，立刻进入队列，支持多个队列
     * @param objs
     */
    public void put2DbServiceQueueInTime(T ...objs){
        EntityCache<T> entityCache=new EntityCache<T>(objs);
        dbSaveCacheService.saveModels(entityCache);
    }

    @Override
    public void put2DbServiceQueue(T obj) {
        put2DbServiceQueueInTime(obj);
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {

    }
}
