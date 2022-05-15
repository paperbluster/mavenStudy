package com.example.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author wanjun
 * @create 2022-05-14 23:29
 */
@Service
public class testThreadPoolTaskExecutor {

    //如果bean没有命名则默认方法名字，有命名取命名
    @Qualifier("jokeThread")
    //这个注解只能在这个类本身是bean的前提下使用
    @Autowired
    ThreadPoolTaskExecutor executor;

    public void test() throws ExecutionException, InterruptedException {
        //2.循环创建任务对象
        List<FutureTask<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            //Producer producer=new Producer("上传者"+i);
            //无返回值,runnable是没有用的
            //executor.submit(producer);

            //无返回值，只能是runnable线程任务
            //executor.execute(producer);
            //有返回值,线程是继承Callable就可以
            CallableThread callableThread = new CallableThread();
            FutureTask<Integer> futureTask = new FutureTask<>(callableThread);
            list.add(futureTask);
            executor.submit(futureTask);
        }
        for (FutureTask<Integer> futureTask : list) {
            System.out.println("result:" + futureTask.get());
        }
        //3.关闭线程池
        executor.shutdown();
    }
}
