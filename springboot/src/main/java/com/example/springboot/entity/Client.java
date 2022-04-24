package com.example.springboot.entity;

/**
 * @author wanjun
 * @create 2022-04-24 13:44
 */
public class Client {
    private int clientId;
    private String name;
    private String type;

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

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
                '}';
    }
}
