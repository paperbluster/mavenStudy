package com.example.spring;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.Callable;

/**
 * @author wanjun
 * @create 2022-05-14 17:09
 */
public class CallableThread implements Callable<Integer> {
    @Override
    @Async("taskExector")
    @Scheduled(fixedRate = 2000)
    public Integer call() throws Exception {
        int j=0;
        for(int i=0;i<10;i++)
            j+=(int)(Math.random() * 10 );
        System.out.println(j);
        return j;
    }
}
