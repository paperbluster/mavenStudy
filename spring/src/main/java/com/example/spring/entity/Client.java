package com.example.spring.entity;

import com.alibaba.fastjson.JSONObject;
import com.example.spring.MyColumn;
import com.example.spring.MyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wanjun
 * @create 2022-04-24 13:44
 */
public class Client {

    private int clientId;
    private String name;
    private String type;
    private Map<Integer,Integer> map=new HashMap<>();

    @MyColumn(name = "type",type= MyEnum.string2map)
    public Map<Integer,Integer> deparse(String sr){
        Map<Integer,Integer> map=new HashMap<>();
        if(sr.isEmpty()){
            return map;
        }
        String[] arr=sr.split("|");
        for(String str:arr){
            String[] detail=str.split("#");
            map.put(Integer.parseInt(detail[0]),Integer.parseInt(detail[1]));
        }
        return map;
    }

    public String parse() {
        if (map == null || map.isEmpty()) {
            return "";
        }
        StringBuilder sb = null;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (sb == null) {
                sb = new StringBuilder();
            } else {
                sb.append("|");
            }
            sb.append(entry.getKey()).append("#").append(entry.getValue());
        }
        return sb.toString();
    }



    public Map<Integer, Integer> getMap() {
        return map;
    }

    public void setMap(Map<Integer, Integer> map) {
        this.map = map;
    }

    public int getClientId() {
        return clientId;
    }

    @MyColumn(name = "client_id",type= MyEnum.string2map)
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    @MyColumn(name = "name",type= MyEnum.string2map)
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", map=" + map +
                '}';
    }
}
