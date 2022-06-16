package com.example.spring;

import com.example.spring.entity.baseAdapter;

/**
 * @author wanjun
 * @create 2022-06-16 23:59
 */
public enum MyEnum {

    string2map("1","baseAdapter");

    private String codeName;
    private String adapterName;

    MyEnum(String codeName,String adapterName) {
        this.codeName = codeName;
        this.adapterName=adapterName;
    }
}
