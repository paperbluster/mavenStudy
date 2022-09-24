package com.example.spring.xstream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wanjun
 * @create 2022-09-24 18:57
 */
@Component
public class Xml2FileManagerImpl implements Xml2FileManager{
    @Autowired
    ConfigCache configCache;

    @Override
    public void save() {
        Bean bean=Bean.valueOf(99,"asshole");
        configCache.addConfigs(bean);
        configCache.save2file();
    }
}
