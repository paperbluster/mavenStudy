package com.example.spring.xstream;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author wanjun
 * @create 2022-09-24 13:49
 */
@Component
public class ConfigCache {
    private static BeanService beanService;

    public void init(){
        try{
            beanService=BeanService.getInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Bean> getConfigs(){
        return beanService.getConfigs();
    }

    public void addConfigs(Bean bean){
        beanService.addConfigs(bean);
    }

    public void save2file() {
        beanService.save2file();
    }
}
