package com.example.spring.mina;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author wanjun
 * @create 2022-08-31 23:19
 */
public class TCPClientHandler extends IoHandlerAdapter {
    public static final Logger logger= LoggerFactory.getLogger(TCPClientHandler.class);
    private final String values;

    public TCPClientHandler(String values) {
        this.values = values;
    }

    @Override
    public void sessionOpened(IoSession session) {
        session.write(values);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception{
        super.messageReceived(session,message);
        System.out.println("来自服务器的消息:"+message);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        System.out.println("服务器故障");
//        session.closeOnFlush();
//        SessionManager.getManager().remove(session);
    }

//    @Override
//    public void sessionIdle(IoSession session, IdleStatus status) {
//        System.out.println("服务器长时间不回应，被动断开连接");
//        session.closeOnFlush();
//        SessionManager.getManager().remove(session);
//    }
//
//    @Override
//    public void sessionClosed(IoSession session) throws Exception {
//        super.sessionClosed(session);
//        System.out.println("服务器主动断开连接 ");
//        SessionManager.getManager().remove(session);
//    }
}
