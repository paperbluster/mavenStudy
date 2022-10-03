package com.example.spring.netty;

/**Netty server 使用main类
 * @author wanjun
 * @create 2022-09-15 17:46
 */
public class MainNettyServer {
    /**
     * 端口
     */
    private static int port = 8686;

    public static void main(String[] args) throws Exception {
        /**
         * 启动netty服务器
         */
        SayHelloServer sayHelloServer = new SayHelloServer(port);
        sayHelloServer.run();
    }
}
