package com.example.spring.socket;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.SimpleIoProcessorPool;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.transport.socket.DefaultSocketSessionConfig;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioProcessor;
import org.apache.mina.transport.socket.nio.NioSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author wanjun
 * @create 2022-10-04 20:35
 */
public class SocketServer {
    private static final Logger LOGGER= (Logger) LoggerFactory.getLogger(SocketServer.class);
    private static final int SERVER_PORT=ServerConfig.getSocketPort();
    private static final boolean IS_TCP_NODELAY=ServerConfig.isTcpNodelay();
    private static final int WORKER_POOL_MAX=ServerConfig.getWorkerPoolMax();
    private static final int SOCKET_BOTH_IDLE=ServerConfig.getSocketBothIdle();
    private static final int WORKER_POOL_MIN=ServerConfig.getWorkerPoolMin();
    private static final int WORKER_POOL_IDLE=ServerConfig.getWorkerPoolIdle();
    private static final int READ_BUFFER_SIZE=ServerConfig.getReadBufferSize();
    private static final int WRITE_BUFFER_SIZE=ServerConfig.getWriteBufferSize();
    private static final int RECEIVE_BUFFER_SIZE=ServerConfig.getReadBufferSize();
    private static final int SERVER_MAX_BACKLOG=ServerConfig.getServerMaxBackLog();
    private static final int SOCKET_WRITE_TIMEOUT=ServerConfig.getSocketWriteTimeout();
    private static final SimpleIoProcessorPool<NioSession> pool=new SimpleIoProcessorPool<NioSession>(NioProcessor.class,Runtime.getRuntime().availableProcessors()+1);//主线程是CPU核心数+1

    //业务处理器
    private IoHandler ioHandler;
    //监听的地址
    private InetSocketAddress address;
    //编解码
    private ProtocolCodecFactory codecFactory;
    //Socket接收器
    private SocketAcceptor acceptor;
    //防止数据包大小洪水攻击
    private IoFilter byteAttackFilter;
    //防止命令执行数量洪水攻击
    private IoFilter cmdAttackFilter;
    // tocken验证过滤器
    private IoFilter tockenValidatorFilter;
    // 线程名
    private static final String THREAD_NAME="Socket线程池";
    //线程组
    private static final ThreadGroup THREAD_GROUP=new ThreadGroup(THREAD_NAME);
    //线程组工厂
    private static final NamedThreadFactory THREAD_FACTORY=new NamedThreadFactory(THREAD_GROUP,THREAD_NAME);
    //线程池对象
    private static final OrderedThreadPoolExecutor FILTER_EXECUTOR=new OrderedThreadPoolExecutor(WORKER_POOL_MIN,WORKER_POOL_MAX,WORKER_POOL_IDLE, TimeUnit.SECONDS,THREAD_FACTORY);

    public SocketServer(ProtocolCodecFactory protocolCodecFactory,IoHandler ioHandler){
        this.ioHandler=ioHandler;
        this.codecFactory=protocolCodecFactory;
    }

    public SocketServer(ProtocolCodecFactory protocolCodecFactory,IoHandler ioHandler,IoFilter byteAttackFilter,IoFilter cmdAttackFilter,IoFilter tockenValidatorFilter){
        this(protocolCodecFactory, ioHandler);
        this.cmdAttackFilter=cmdAttackFilter;
        this.byteAttackFilter=byteAttackFilter;
        this.tockenValidatorFilter=tockenValidatorFilter;
    }

    public void start()throws Exception{
        if (codecFactory==null){
            throw new NullPointerException("ProtocolCodecFactory is null");
        }
        if(ioHandler==null){
            throw new NullPointerException("ioHandler is null");
        }
        IoBuffer.setUseDirectBuffer(false);//不使用默认的buffer对象
        IoBuffer.setAllocator(new SimpleBufferAllocator());//设置IOBuffer分配对象
        acceptor=new NioSocketAcceptor(pool);
        acceptor.setReuseAddress(true);//可以重用端口
        acceptor.setBacklog(SERVER_MAX_BACKLOG);//设置最大可以连接的连接数，超过该i队列数值则直接丢弃
        acceptor.getSessionConfig().setAll(this.getSessionConfig());//更新自定义的会话设置
        MdcInjectionFilter mdcInjectionFilter=new MdcInjectionFilter();
        DefaultIoFilterChainBuilder filterChain=acceptor.getFilterChain();
        filterChain.addLast("mdcInjectionFilter",mdcInjectionFilter);
        if(byteAttackFilter!=null){
            filterChain.addLast("byteAttackFilter",byteAttackFilter);
        }
        if(cmdAttackFilter!=null){
            filterChain.addLast("cmdAttackFilter",cmdAttackFilter);
        }
        filterChain.addLast("codecFactory",new ProtocolCodecFilter(codecFactory));
        filterChain.addLast("tockenValidatorFilter",tockenValidatorFilter);
        filterChain.addLast("threadPool",new ExecutorFilter(FILTER_EXECUTOR));
        acceptor.setHandler(ioHandler);
        LOGGER.info("listening on "+address.getHostName()+":"+address.getPort());
    }

    public SocketSessionConfig getSessionConfig() {
        SocketSessionConfig sessionConfig = new DefaultSocketSessionConfig();
        sessionConfig.setSoLinger(0);//
        sessionConfig.setKeepAlive(true);
        sessionConfig.setReuseAddress(true);
        sessionConfig.setTcpNoDelay(IS_TCP_NODELAY);
        sessionConfig.setBothIdleTime(SOCKET_BOTH_IDLE);
        sessionConfig.setReadBufferSize(READ_BUFFER_SIZE);
        sessionConfig.setSendBufferSize(WRITE_BUFFER_SIZE);
        sessionConfig.setWriteTimeout(SOCKET_WRITE_TIMEOUT);
        sessionConfig.setReceiveBufferSize(RECEIVE_BUFFER_SIZE);
        return sessionConfig;
    }

    public void stop(){
        if(acceptor!=null){
            acceptor.unbind();
            acceptor.dispose();
            acceptor=null;
        }
        if(FILTER_EXECUTOR!=null){
            FILTER_EXECUTOR.shutdown();
            try{
                FILTER_EXECUTOR.awaitTermination(5000,TimeUnit.SECONDS);
            }catch (InterruptedException e){
                LOGGER.error("停服抛出异常",e);
            }finally {

            }
        }
    }

    public ProtocolCodecFactory getProtocolCodecFactory(){
        return codecFactory;
    }

    public void setProtocolCodecFactory(ProtocolCodecFactory protocolCodecFactory){
        this.codecFactory=protocolCodecFactory;
    }

    public IoFilter getTockenValidatorFilter(){
        return tockenValidatorFilter;
    }

    public void setTockenValidatorFilter(IoFilter tockenValidatorFilter){
        this.tockenValidatorFilter=tockenValidatorFilter;
    }

    public InetSocketAddress getAdress(){
        return address;
    }

    public void setAdress(InetSocketAddress adress) {
        this.address = adress;
    }
}
