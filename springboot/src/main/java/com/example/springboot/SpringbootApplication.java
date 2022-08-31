package com.example.springboot;

import com.example.springboot.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

@SpringBootApplication
// 使用这个注解就不需要给每个mapper加mapper注解
@MapperScan("com.example.springboot.mapper")
public class SpringbootApplication {
    @Resource(name = "threadPoolInstance")
    private ExecutorService executorService;

    public static void main(String[] args) {
        ConfigurableApplicationContext context= SpringApplication.run(SpringbootApplication.class, args);
        UserService userService=context.getBean(UserService.class);
        //userService.Test();
        System.out.print("result:"+userService.Find());
        System.out.print("result:"+userService.FindMore());
    }


    public void find(UserService userService) {
        //TODO
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //TODO
                //执行业务逻辑
                System.out.println("result:"+userService.Find());
            }});
    }

    public void findAll(UserService userService) {
        //TODO
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //TODO
                //执行业务逻辑
                System.out.println("resultAll:"+userService.FindMore());
            }});
    }

}
