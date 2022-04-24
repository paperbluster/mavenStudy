package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.service.UserService;

/**
 * @author wanjun
 * @create 2022-04-19 11:35
 */
public class MyApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        UserService userService=context.getBean(UserService.class);
        userService.Test();
    }

}
