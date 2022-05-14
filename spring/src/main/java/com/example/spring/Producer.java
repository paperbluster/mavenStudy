package com.example.spring;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author wanjun
 * @create 2022-05-14 16:40
 */
public class Producer implements Runnable{
    private String name;

    public Producer(String name) {
        this.name = name;
    }

    @Override
    @Async("taskExector")
    @Scheduled(fixedRate = 2000)
    public void run() {
        //while(true)
        System.out.println(name);
    }
}
