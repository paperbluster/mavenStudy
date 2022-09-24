package com.example.spring.xstream;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author wanjun
 * @create 2022-09-12 22:51
 */

public class Bean {

    @XStreamAsAttribute
    private int id;
    @XStreamAsAttribute
    private String power;

    public static Bean valueOf(int id, String power){
        Bean bean=new Bean();
        bean.setId(id);
        bean.setPower(power);
        return bean;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }
}
