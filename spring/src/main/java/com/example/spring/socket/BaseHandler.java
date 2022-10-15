package com.example.spring.socket;

import com.example.spring.base.CacheDao;
import com.example.spring.mina.SessionManager;
import com.example.spring.schedule.Invoker;
import com.example.spring.schedule.Request;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cglib.proxy.Dispatcher;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**请求逻辑处理基类
 * @author wanjun
 * @create 2022-10-05 14:13
 */
public abstract class BaseHandler implements Handler{
    private static final Logger LOGGER= (Logger) LoggerFactory.getLogger(BaseHandler.class);
    @Qualifier("cacheDao")
    @Autowired(required = true)
    protected CacheDao cacheDao;

    @Autowired
    protected Dispatcher dispatcher;
    @Autowired
    protected SessionManager sessionManager;
    @Autowired
    protected PlayerManager playerManager;

    protected final Map<Short, Invoker> CMD_INVOKERS=new HashMap<>(1);
    private static final Map<Integer,MsgCache>msgCacheMap=new HashMap<>();
    public static ConcurrentHashMap<Integer, ReentrantLock> playerLockGroup=new ConcurrentHashMap<>();

    /**
     * 获取模块标识
     * @return
     */
    protected abstract short getModule();

    protected abstract void inititialize();

    @PostConstruct
    public void init(){
        dispatcher.put(getModule(),this);
        inititialize();
    }

    /**
     * 注册命令处理器
     * @param cmd
     * @param invoker
     */
    public void putInvoker(short cmd,Invoker invoker){
        if(CMD_INVOKERS.containsKey(cmd)){
            LOGGER.error(String.format("ERROR:duplicated key [%d]",cmd));
        }
        CMD_INVOKERS.put(cmd,invoker);
    }

    @Override
    public void dispatch(IoSession session, Request request) {
        if (request != null) {
            short cmd = request.getCmd();
            short module = request.getModule();
            Response response = Response.valueOf(module, cmd, request.getPlayerId(), request.getHeaderId());
            Invoker invoker = CMD_INVOKERS.get(cmd);
            if (module == Module.CHAT_TO_GAME) {
                doInvoke(session, request, cmd, module, response, invoker);
            } else {
                int playerUid = request.getPlayerId();
                ReentrantLock playerLock = getPlayerLock(request.getPlayerId());
                try {
                    if (playerLock.tryLock()) {
                        doInvoke(session, request, cmd, module, response, invoker);
                    } else {
                        LOGGER.info("playerLock trylock error playerId[" + request.getPlayerId() + "]module:" + module + "],cmd:" + cmd + "]");
                    }
                } catch (Exception e) {
                    LOGGER.error("meet error playerlock.unlock", e);
                    e.printStackTrace();
                } finally {
                    try {
                        playerLock.unlock();
                    } catch (Exception ex) {
                        LOGGER.error("BaseHandler playerLock.unlock error playerUid:" + playerUid + "]", ex);
                        playerLockGroup.remove(playerUid);
                    }
                }
            }
        }
    }

    public void doInvoke(IoSession session,Request request,short cmd,short module,Reponse reponse,Invoker invoker){
        if(invoker!=null){
            if(request.getModule()==Module.CHAT_TO_GAME){
                // dohandler
                invoker.invoke(session,request,reponse);
                return;
            }
            MsgCache msgCache=msgCacheMap.get(request.getPlayerId());
            if(null==msgCache){//第一次客户端收到的消息
                //dohandler
                LOGGER.info("playerId[" + request.getPlayerId() + "]module:" + module + "],cmd:" + cmd + "],timestamp:"+request.getTimestamp()+"首次处理");
                MsgCache cache=new MsgCache(request.getTimestamp(),reponse);
                msgCacheMap.put(request.getPlayerId(),cache);
                invoker.invoke(session,request,reponse);
            }else if(msgCache.getRequestId==request.getTimestamp()&&!(request.getModule()==Module.CHAT_TO_GAME)){
                LOGGER.info("playerId[" + request.getPlayerId() + "]module:" + module + "],cmd:" + cmd + "],timestamp:"+request.getTimestamp()+"进入重试");
                session.write(msgCache.getReponse());
            }else{//新消息
                ////dohandler
                LOGGER.info("playerId[" + request.getPlayerId() + "]module:" + module + "],cmd:" + cmd + "],timestamp:"+request.getTimestamp()+"新消息");
                MsgCache cache=new MsgCache(request.getTimestamp(),reponse);
                msgCacheMap.put(request.getPlayerId(),cache);
                invoker.invoke(session,request,reponse);
            }
        }else{
            LOGGER.error(String.format("no invoker for module: [%d],cmd:[%d]",module,cmd));
            if(session.isConnected()){
                session.write(reponse);
            }
        }
    }

    public static ReentrantLock getPlayerLock(int playerUid){
        ReentrantLock playerLock=playerLockGroup.get(playerUid);
        if(null==playerLock){
            playerLock=new ReentrantLock();
            ReentrantLock l=playerLockGroup.putIfAbsent(playerUid,playerLock);
            if(l!=null){
                playerLock=l;
            }
            return playerLock;
        }
    }

}
