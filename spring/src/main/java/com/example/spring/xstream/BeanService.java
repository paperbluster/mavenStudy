package com.example.spring.xstream;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * @author wanjun
 * @create 2022-09-12 22:36
 */
@XStreamAlias("data")
public class BeanService {
    public static BeanService instance;
    @XStreamImplicit(itemFieldName = "jj")
    private List<Bean> configs;

    public static BeanService getInstance() {
        if (instance == null) {
            try {
                instance = (BeanService) (new XSreamUtil()).xml2bean("ff.xml", Bean.class, BeanService.class);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return instance;

    }

    public void save2file() {
        try {
            (new XSreamUtil()).bean2xml("ff.xml",this);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public List<Bean> getConfigs() {
        return configs;
    }

    public void setConfigs(List<Bean> configs) {
        this.configs = configs;
    }

    public void addConfigs(Bean bean){
        configs.add(bean);
    }
}
