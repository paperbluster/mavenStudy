package com.example.spring;

import com.example.spring.mina.TCPServer;
import com.example.spring.service.UserService;
import com.example.spring.xstream.BeanService;
import com.example.spring.xstream.ConfigCache;
import com.example.spring.xstream.Xml2FileManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author wanjun
 * @create 2022-04-19 11:35
 */

public class MyApplication {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(AppConfig.class);
        //UserService userService=context.getBean(UserService.class);
        //从spring容器里取出CRUD对象进行SQL操作
        //userService.Test();
       // System.out.print("result:"+userService.Find()+"\n");
        //从spring容器里取出线程池对象进行多线程操作
        //testThreadPoolTaskExecutor tt=context.getBean(testThreadPoolTaskExecutor.class);
        //tt.test();
        //TCPServer tcpServer=context.getBean(TCPServer.class);
        //tcpServer.main();
        //BeanService bb= BeanService.getInstance();
        /**
         * XML配置表加载到缓存
         */
        ConfigCache configCache=context.getBean(ConfigCache.class);
        configCache.init();
        Xml2FileManager xml2FileManager=context.getBean(Xml2FileManager.class);
        xml2FileManager.save();
    }

}
