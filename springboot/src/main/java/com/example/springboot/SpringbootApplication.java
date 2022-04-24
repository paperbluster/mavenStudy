package com.example.springboot;

import com.example.springboot.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
// 使用这个注解就不需要给每个mapper加mapper注解
@MapperScan("com.example.springboot.mapper")
public class SpringbootApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context= SpringApplication.run(SpringbootApplication.class, args);
        UserService userService=context.getBean(UserService.class);
        userService.Test();
    }

}
