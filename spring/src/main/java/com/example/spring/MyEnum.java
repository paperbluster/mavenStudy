package com.example.spring;

import com.example.spring.entity.baseAdapter;

/**
 * @author wanjun
 * @create 2022-06-16 23:59
 */
public enum MyEnum {

    string2map(Integer.class,StringToInteger.class);

//    private String codeName;
//    private String adapterName;
    private Class<?> type;
    private Class<StringToInteger> abstractType;

    MyEnum(Class<?> type, Class<StringToInteger> abstractType) {
        this.type=type;
        this.abstractType= abstractType;
    }
}
