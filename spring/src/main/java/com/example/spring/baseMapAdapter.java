package com.example.spring;


import java.util.HashMap;
import java.util.Map;

/**
 * @author wanjun
 * @create 2022-06-17 1:39
 */
public class baseMapAdapter<T,V> {

    public Map<T, V> parse(Object o, Class<T> key, Class<V> type) {
        Map<T, V> map = new HashMap<>();
        if (o instanceof String) {
            String str = (String) o;
            if (str.isEmpty()) {
                return map;
            }
            String[] arr = str.split("|");
            for (String sb : arr) {
                String[] detail = sb.split("#");
                map.put(key.cast(detail[0]), type.cast(detail[1]));
            }
            return map;
        } else {
            return map;
        }
    }

}
