package com.example.spring.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * @author wanjun
 * @create 2022-08-31 23:19
 */
public class TCPClientHandler extends IoHandlerAdapter {
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
}
