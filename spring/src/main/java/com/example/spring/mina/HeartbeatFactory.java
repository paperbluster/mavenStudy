package com.example.spring.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

/**服务器被动心跳(客户端心跳包)，服务器规定时间没有客户端心跳包则关闭客户端连接
 * @author wanjun
 * @create 2022-08-31 23:32
 */
public class HeartbeatFactory implements KeepAliveMessageFactory {
    /** 心跳包内容 */
    private static final String HEART_BEAT_REQUEST = "客户端心跳包";

    /***
     * 判断是否是心跳请求
     * @param session
     * @param message
     * @return
     */
    @Override
    public boolean isRequest(IoSession session, Object message) {
        return message.equals(HEART_BEAT_REQUEST);
        //return false;
    }

    /**
     * 客户端心跳不需要返回
     * @param session
     * @param message
     * @return
     */
    @Override
    public boolean isResponse(IoSession session, Object message) {
        return false;
    }

    @Override
    public Object getRequest(IoSession session) {
        // 客户端心跳包发送数据
        session.write(HEART_BEAT_REQUEST);
        return null;
    }

    /**
     * 被动型不需要
     * @param session
     * @param request
     * @return
     */
    @Override
    public Object getResponse(IoSession session, Object request) {
//        System.out.println("received message is = " + request);
//        // 服务端心跳包发送数据
//        session.write("服务端心跳包");
        return "服务端响应心跳包";
    }
}
