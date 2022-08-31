package com.example.spring.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.net.InetSocketAddress;

/**
 * @author wanjun
 * @create 2022-08-31 23:07
 */
public class TCPServerHandler extends IoHandlerAdapter {
    /** 客户端预定的心跳包内容 */
    private static final String HEART_BEAT_REQUEST = "客户端心跳包";
    int i=0,j=0;
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        System.out.println("来自客户端的消息: " + message.toString());
        if(message.equals(HEART_BEAT_REQUEST)){
            session.write("客户端!我收到了你的心跳包");
        }else {
            if(session.isActive()&&i++==0){
                session.write("客户端!你的事务已经创建了");
            }else if(session.isConnected()&&j++==0){
                session.write("客户端!你的事务已经连接过来了");
            }else{
                session.write("客户端!我收到了你的消息");
            }
        }
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        InetSocketAddress remoteAddress = (InetSocketAddress) session.getRemoteAddress();
        String clientIp = remoteAddress.getAddress().getHostAddress();
        System.out.println("session created with IP: " + clientIp+"\n");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        InetSocketAddress remoteAddress = (InetSocketAddress) session.getRemoteAddress();
        String clientIp = remoteAddress.getAddress().getHostAddress();
        System.out.println("session opened with IP: " + clientIp);
        SessionManager.getManager().add(session);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        System.out.println("session closed ");
        SessionManager.getManager().remove(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {
        System.out.println("session in idle");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        System.out.println("exception");
        session.closeOnFlush();
        SessionManager.getManager().remove(session);
    }
}
