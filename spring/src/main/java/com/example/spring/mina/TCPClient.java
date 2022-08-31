package com.example.spring.mina;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoService;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author wanjun
 * @create 2022-08-31 23:19
 */
public class TCPClient {
    public static final Logger logger = LoggerFactory.getLogger(TCPClient.class);

    public static void main(String[] args) {
        IoConnector connector = new NioSocketConnector();
        // 尝试连接服务器失效时间30s
        //connector.setConnectTimeoutMillis(30000);
        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),
                        LineDelimiter.WINDOWS.getValue(),
                        LineDelimiter.WINDOWS.getValue())));
        //客户端发心跳包
        KeepAliveFilter heartBeat = new KeepAliveFilter(new HeartbeatFactory());
        //idle 事件回调
        heartBeat.setForwardEvent(true);
        heartBeat.setRequestTimeoutHandler(KeepAliveRequestTimeoutHandler.LOG);
        // 心跳检测间隔时间10s
        heartBeat.setRequestInterval(10);
        // 心跳检测超时时间30s
        heartBeat.setRequestTimeout(15);
        connector.getFilterChain().addLast("heart beat", heartBeat);

        connector.setHandler(new TCPClientHandler("你好！服务器，我是客户端，我开启了和你的事务"));
        // 监听服务器中途断开
        connector.addListener(new IoServiceListener() {
            @Override
            public void serviceActivated(IoService service) throws Exception {

            }

            @Override
            public void serviceIdle(IoService service, IdleStatus idleStatus) throws Exception {

            }

            @Override
            public void serviceDeactivated(IoService service) throws Exception {

            }

            @Override
            public void sessionCreated(IoSession session) throws Exception {

            }

            @Override
            public void sessionClosed(IoSession session) throws Exception {
            }

            @Override
            public void sessionDestroyed(IoSession session) throws Exception {
                int i = 1;
                ConnectFuture connectFuture = null;
                while (true) {
                    logger.info("服务器中途断开后尝试恢复连接服务器第" + i++ + "次");
                    connectFuture = connector.connect(new InetSocketAddress("localhost", 9124));
                    connectFuture.awaitUninterruptibly();
                    if (!connectFuture.isConnected()) {
                        continue;
                    }
                    session = connectFuture.getSession();
                    if (session != null && session.isConnected())
                        session.write("服务器!你收到我的事务重连了吗？");
                    logger.info("尝试恢复连接服务器成功");
                    break;
                }
            }
        });
        ConnectFuture connectFuture = null;
        try {
            connectFuture = connector.connect(new InetSocketAddress("localhost", 9124));
            connectFuture.awaitUninterruptibly();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (connectFuture != null) {
            try {
                if (connectFuture.isConnected()) {
                    IoSession session = connectFuture.getSession();
                    if (session != null && session.isConnected())
                        session.write("服务器!你收到我的事务连接了吗？");
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                int i = 1;
                while (true) {
                    if (connectFuture.isConnected()) {
                        break;
                    }
                    logger.info("首次尝试连接服务器第" + i++ + "次");
                    connectFuture = connector.connect(new InetSocketAddress("localhost", 9124));
                    connectFuture.awaitUninterruptibly();
                    if (!connectFuture.isConnected()) {
                        continue;
                    }
                    IoSession session = connectFuture.getSession();
                    if (session != null && session.isConnected())
                        session.write("服务器!你收到我的事务连接了吗？");
                    logger.info("尝试连接服务器成功");
                    break;
                }
            }
        }

    }
}
