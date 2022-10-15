package com.example.spring.base;

import com.example.spring.AppConfig;
import com.example.spring.xstream.ConfigCache;
import com.example.spring.xstream.Xml2FileManager;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author wanjun
 * @create 2022-04-19 11:35
 */

public class MyApplication {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(MyApplication.class);
    public static AnnotationConfigApplicationContext context;

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        DbSaveCacheService dbSaveCacheService = context.getBean(DbSaveCacheService.class);
        try {
            for (int i = 0; i < 5; i++) {
                CacheSave cacheSave = new CacheSave();
                Thread t = new Thread(cacheSave, "saveThread" + i);
                t.start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                boolean flag = true;
                while (flag) {
                    LOGGER.error("开始执行停服保存内存数据操作");
                    int saveSize = dbSaveCacheService.getSaveQueueSize();
                    LOGGER.error("开始执行停服保存内存数据操作saveSize==" + saveSize);
                    if (saveSize <= 0) {
                        flag = false;
                    } else {
                        try {
                            dbSaveCacheService.handleSaveQueue(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                LOGGER.error("执行停服保存内存数据操作完成");
                LOGGER.info("stopping server...");
            }));
        }
    }

}
