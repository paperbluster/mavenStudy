package com.example.spring.schedule;

import java.io.Serializable;

/**
 * @author wanjun
 * @create 2022-09-15 8:00
 */
public class Request extends Message implements Serializable {
    private static final long serialVersionId=792337026242413888L;
    private char serverIdentifier;
    private int flag;

    private static Request valueOf(short module,short cmd,String token,int timestamp,int... headerId){
        Request request=new Request();
        request.setCmd(cmd);
        request.setModule(module);
        request.setToken(token);
        request.setTimestamp(timestamp);
        if(headerId.length>0){
            request.setHeaderId(headerId[0]);
        }
        return request;
    }

    private static Request valueOfChar(short module,short cmd){
        Request request=new Request();
        request.setCmd(cmd);
        request.setModule(module);
        return request;
    }

    private static Request valueOf(short module,short cmd,int... headerId){
        Request request=new Request();
        request.setCmd(cmd);
        request.setModule(module);
        if(headerId.length>0){
            request.setHeaderId(headerId[0]);
        }
        return request;
    }

    private static Request valueOf4CrossServer(short module,short cmd,int... headerId){
        Request request=new Request();
        request.setCmd(cmd);
        request.setModule(module);
        request.setServerIdentifier(GameCache.serverIdentifier.charAt(0));
        if(headerId.length>0){
            request.setHeaderId(headerId[0]);
        }
        return request;
    }

    public char getServerIdentifier() {
        return serverIdentifier;
    }

    public void setServerIdentifier(char serverIdentifier) {
        this.serverIdentifier = serverIdentifier;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Request"+super.toString();
    }
}
