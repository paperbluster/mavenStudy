package test1.transaction;


import com.baomidou.mybatisplus.annotation.TableField;

import javax.persistence.Column;

/**
 * @author wanjun
 * @create 2022-05-10 16:09
 */
public class Client {
    private int client;
    private String name;
    private String type;

    public Client() {
        //super();
    }

    public Client(int client, String name, String type) {
        this.client = client;
        this.name = name;
        this.type = type;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
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
                "client=" + client +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
