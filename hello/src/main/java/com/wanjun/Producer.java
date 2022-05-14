package com.wanjun;

/**
 * @author wanjun
 * @create 2022-05-14 16:40
 */
public class Producer implements Runnable{
    private String name;

    public Producer(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        //while(true)
        System.out.println(name);
    }
}
