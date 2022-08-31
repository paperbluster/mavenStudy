package com.example.spring.mina;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author wanjun
 * @create 2022-08-31 23:19
 */
public class TCPClient {
    public static void main(String[] args) {
        IoConnector connector = new NioSocketConnector();
        // 连接失效时间30s
        connector.setConnectTimeoutMillis(30000);
        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),
                        LineDelimiter.WINDOWS.getValue(),
                        LineDelimiter.WINDOWS.getValue())));
        //客户端发心跳包
        KeepAliveFilter heartBeat = new KeepAliveFilter(new HeartbeatFactory());
        //idle 事件回调
        heartBeat.setForwardEvent(true);
        heartBeat.setRequestTimeoutHandler(KeepAliveRequestTimeoutHandler.LOG);
        // 心跳检测间隔时间30s
        heartBeat.setRequestInterval(30);
        // 心跳检测超时时间30s
        heartBeat.setRequestTimeout(30);
        connector.getFilterChain().addLast("heart beat", heartBeat);

        connector.setHandler(new TCPClientHandler("你好！服务器，我是客户端，我开启了和你的事务"));
        ConnectFuture connectFuture=null;
        try {
            connectFuture=connector.connect(new InetSocketAddress("localhost", 9124));
            connectFuture.awaitUninterruptibly();
        }catch (Exception exception){
        exception.printStackTrace();
        }
        if(connectFuture!=null){
            IoSession session=connectFuture.getSession();
            session.write("服务器!你收到我的事务连接了吗？");
        }

    }
}
