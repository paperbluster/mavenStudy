package com.example.spring.schedule;

import java.io.Serializable;

/**
 * @author wanjun
 * @create 2022-09-15 7:57
 */
public class Message implements Serializable {
    private static final long serialVersionId=1969969111611715840L;
    //消息头id
    private int headerId;
    private short module;
    private short cmd;
    private String token;
    private int timestamp;
    private int playerId;

    private MsgBody msgBody;

    public static long getSerialVersionId() {
        return serialVersionId;
    }

    public int getHeaderId() {
        return headerId;
    }

    public void setHeaderId(int headerId) {
        this.headerId = headerId;
    }

    public short getModule() {
        return module;
    }

    public void setModule(short module) {
        this.module = module;
    }

    public short getCmd() {
        return cmd;
    }

    public void setCmd(short cmd) {
        this.cmd = cmd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        return "[module="+module+",cmd="+cmd+",msgBody="+msgBody+"]";
    }
}
