package com.example.spring.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**sayhello 客户端
 * @author wanjun
 * @create 2022-09-15 17:44
 */
public class SayHelloClient {
    private int port;
    private String host = "127.0.0.1";
    private Channel channel;

    public SayHelloClient(int port){
        this.port = port;
    }

    /**
     * 客户端运行方法
     * @throws InterruptedException
     */
    public void run() throws InterruptedException {
        /**
         * 负责装客户端的事件处理线程池
         */
        EventLoopGroup clientWorker = new NioEventLoopGroup();
        try {
            /**
             * netty客户端引导启动器
             */
            Bootstrap bootstrap = new Bootstrap();
            bootstrap
                    .group(clientWorker)//把事件处理线程池添加进启动引导器
                    .channel(NioSocketChannel.class)//设置通道的建立方式,这里采用Nio的通道方式来建立请求连接
                    //.option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            /**
                             * 此处添加客户端的通道处理器
                             */
                            socketChannel.pipeline().addLast(new SayHelloClientHandler());
                        }
                    });
            /**
             * 客户端绑定端口并且开始发起连接请求
             */
            ChannelFuture future = bootstrap.connect(host, port).sync();
            if (future.isSuccess()){
                System.out.println("客户端连接服务器成功!");
            }
            /**
             * 将通道设置好, 以便外面获取
             */
            this.channel = future.channel();
            /**
             * 关闭客户端
             */
            future.channel().closeFuture().sync();
            System.out.println("客户端即将关闭!");
        } finally {
            /**
             * 关闭事件处理组
             */
            clientWorker.shutdownGracefully();
            System.out.println("客户端已关闭!");
        }
    }

    public Channel getChannel(){
        return this.channel;
    }
}
