package com.example.spring.mina;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author wanjun
 * @create 2022-08-31 23:09
 */
@Component
public class TCPServer {
    public static final Logger logger= LoggerFactory.getLogger(TCPServer.class);
    public void main() throws IOException {
        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getSessionConfig().setReadBufferSize(2048);
        // 空闲时间10s
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

        // 编写过滤器
        acceptor.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),
                        LineDelimiter.DEFAULT.getValue(), LineDelimiter.DEFAULT.getValue()))
        );

        //设置handler
        acceptor.setHandler(new TCPServerHandler());

        //绑定端口
        acceptor.bind(new InetSocketAddress(9124));
        logger.info("mina server!");
    }
}
