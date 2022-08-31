package com.example.spring;

import com.example.spring.mina.TCPServer;
import com.example.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

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
        TCPServer tcpServer=context.getBean(TCPServer.class);
        tcpServer.main();
    }

}
