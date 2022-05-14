package com.example.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wanjun
 * @create 2022-05-14 22:51
 */
@Configuration
//启动多线程注解
@EnableAsync
public class AsyncScheduledTaskConfig {
    @Value("${spring.CorePoolSize}")
    private int coreSize;
    @Value("${spring.MaxPoolSize}")
    private int maxsize;
    @Value("${spring.QueueCapacity}")
    private int queueLen;
    @Value("${spring.KeepAliveSeconds}")
    private int aliveSeconds;
    @Value("${spring.ThreadNamePrefix}")
    private String prefixName;

    @Bean("taskExector")
    public Executor taskExector() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int i = Runtime.getRuntime().availableProcessors();//获取到服务器的cpu内核
        executor.setCorePoolSize(coreSize);//核心池大小
        executor.setMaxPoolSize(maxsize);//最大线程数
        executor.setQueueCapacity(queueLen);//队列程度
        executor.setKeepAliveSeconds(aliveSeconds);//线程空闲时间
        executor.setThreadNamePrefix(prefixName);//线程前缀名称
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());//配置拒绝策略
        //线程初始化,spring封装的线程池ThreadPoolTaskExecutor不会自动初始化，需要手动
        executor.initialize();
        return executor;
    }
}
