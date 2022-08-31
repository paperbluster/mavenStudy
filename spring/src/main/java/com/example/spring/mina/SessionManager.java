package com.example.spring.mina;

import org.apache.mina.core.session.IoSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wanjun
 * @create 2022-08-31 23:28
 */
public class SessionManager {
    private final static Map<Long, IoSession> sessions = new ConcurrentHashMap<>();
    private final static SessionManager manager = new SessionManager();

    public static SessionManager getManager() {
        return manager;
    }

    private SessionManager() {}

    public void add(IoSession ioSession) {
        if (ioSession == null) return;

        sessions.put(ioSession.getId(), ioSession);
    }

    public void remove(IoSession ioSession) {
        if (ioSession == null) return;

        sessions.remove(ioSession);
    }

    public void removeAll() {
        if (sessions.size() == 0) return;

        sessions.clear();
    }

    public void update(Object message) {
        for (IoSession ioSession: sessions.values()) {
            ioSession.write(message);
        }
    }
}
