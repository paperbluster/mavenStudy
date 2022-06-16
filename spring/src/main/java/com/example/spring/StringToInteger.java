package com.example.spring;

import com.example.spring.entity.baseAdapter;

/**
 * @author wanjun
 * @create 2022-06-17 1:17
 */
public class StringToInteger extends baseAdapter {
    @Override
    public <T> T parse(Object o, Class<T> type) {
        if (type.isInstance(o)) return type.cast(o);
        throw new RuntimeException("can not cast " + o.getClass() + " to '" + type);
    }
}
