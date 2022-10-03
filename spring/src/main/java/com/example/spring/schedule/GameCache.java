package com.example.spring.schedule;

/**
 * @author wanjun
 * @create 2022-09-15 8:09
 */
public class GameCache {
    public static String serverIdentifier;//服务器标识服(A,B,C)

    public static String getServerIdentifier() {
        return serverIdentifier;
    }

    public static void setServerIdentifier(String serverIdentifier) {
        GameCache.serverIdentifier = serverIdentifier;
    }
}
