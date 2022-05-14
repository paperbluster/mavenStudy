package com.example.spring;

import com.example.spring.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wanjun
 * @create 2022-04-19 11:35
 */
public class MyApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService=context.getBean(UserService.class);
        //userService.Test();
        System.out.print("result:"+userService.Find());
    }

}
