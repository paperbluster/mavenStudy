package com.example.spring.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wanjun
 * @create 2022-08-31 23:07
 */
public class TCPServerHandler extends IoHandlerAdapter {
    public static final Logger logger = LoggerFactory.getLogger(TCPServerHandler.class);
    //private static ConcurrentHashMap<Long, DelaySession> DelaySessionMap = new ConcurrentHashMap<>();
    /**
     * 客户端预定的心跳包内容
     */
    private static final String HEART_BEAT_REQUEST = "客户端心跳包";
    int i = 0, j = 0;

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        System.out.println("来自客户端的消息: " + message.toString());
        if (message.equals(HEART_BEAT_REQUEST)) {
            session.write("客户端!我收到了你的心跳包");
        } else {
            if (session.isActive() && i++ == 0) {
                session.write("客户端!你的事务已经创建了");
            } else if (session.isConnected() && j++ == 0) {
                session.write("客户端!你的事务已经连接过来了");
            } else {
                session.write("客户端!我收到了你的消息");
            }
        }
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        InetSocketAddress remoteAddress = (InetSocketAddress) session.getRemoteAddress();
        String clientIp = remoteAddress.getAddress().getHostAddress();
        logger.info("session created with IP: " + clientIp);
        //DelaySessionMap.remove(session.getId());
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        InetSocketAddress remoteAddress = (InetSocketAddress) session.getRemoteAddress();
        String clientIp = remoteAddress.getAddress().getHostAddress();
        System.out.println("session opened with IP: " + clientIp);
        SessionManager.getManager().add(session);
        //DelaySessionMap.remove(session.getId());
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        logger.info("客户端主动断开连接,服务器等待30S后关闭与客户端连接");
//        while (true){
//            if(session.isConnected()){
//                break;
//            }
//            long ioTime = System.currentTimeMillis() - session.getLastIoTime();
//            if(ioTime>30000){
                SessionManager.getManager().remove(session);
//                System.out.println("服务器移除客户端事务");
//                break;
//            }
//        }
//        DelaySession delaySession = new DelaySession(System.currentTimeMillis(), session);
//        DelaySessionMap.put(session.getId(), delaySession);
    }

    /**
     * 每30秒删除过期事务连接
     */
//    @Scheduled(cron="0 0/300 * * * ?")
//    private void removeExpiredSession() {
//        logger.info("定时清除过期延时事务");
//        long now = System.currentTimeMillis();
//        for (Map.Entry<Long, DelaySession> entry : DelaySessionMap.entrySet()) {
//            if (entry.getKey() + 30000 < now) {
//                SessionManager.getManager().remove(entry.getValue().getSession());
//                DelaySessionMap.remove(entry.getKey());
//            }
//        }
//    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {
        logger.info("客户端长时间空闲，被动断开连接");
        session.closeOnFlush();
        SessionManager.getManager().remove(session);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        logger.warn("客户端故障");
//        session.closeOnFlush();
//        SessionManager.getManager().remove(session);
    }

//    public class DelaySession {
//        private long expire;
//        private IoSession session;
//
//        public DelaySession(long expire, IoSession session) {
//            this.expire = expire;
//            this.session = session;
//        }
//
//        public long getExpire() {
//            return expire;
//        }
//
//        public void setExpire(long expire) {
//            this.expire = expire;
//        }
//
//        public IoSession getSession() {
//            return session;
//        }
//
//        public void setSession(IoSession session) {
//            this.session = session;
//        }
//    }

}
