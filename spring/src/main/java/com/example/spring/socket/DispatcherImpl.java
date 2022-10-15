package com.example.spring.socket;

import com.example.spring.schedule.Request;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author wanjun
 * @create 2022-10-05 14:58
 */
public class DispatcherImpl implements Dispatcher {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(DispatcherImpl.class);


    @Autowired
    private WorldMessageManager worldMessageManager;

    /**
     * 模块及所对应的处理器
     **/
    private static final Map<Short, Handler> MODULE_HANDLERS = new HashMap<>(5);

    /**
     * 需要异步队列处理的消息
     **/
    private static final Map<Short, Set<Short>> ASYNC_MESSAGE = new HashMap<>();
    /**
     * 需要异步处理的消息模块
     **/
    private static final Set<Short> ASYNC_MODULE = new HashSet<>();
    /**
     * 消息队列
     **/
    private ConcurrentLinkedQueue<KeyValuePair<Handler, KeyValuePair<IoSession, Request>>> messageQueue = new ConcurrentLinkedQueue<KeyValuePair<Handler, KeyValuePair<IoSession, Request>>>();


    @Override
    public void put(short module, Handler handler) {
        if (handler != null) {
            if (MODULE_HANDLERS.containsKey(module)) {
                throw new RuntimeException(String.format("ERROR:DUPLICATED KEY [%d]", module));
            }
            MODULE_HANDLERS.put(module, handler);
        }
    }

    @Override
    public void dispatch(IoSession session, Request request) {
        if (session == null || request == null) {
            return;
        }
        short module = request.getModule();
        final Handler handler = MODULE_HANDLERS.get(module);
        if (handler == null) {
            LOGGER.error(String.format("NO HANDLER FOR MODULE [%d]", module));
            return;
        }
        if (isAsyncModule(module)) {
            addMessage(handler, session, request);
            return;
        }
        short cmd = request.getCmd();
        if (isAsyncMessage(module, cmd)) {
            addMessage(handler, session, request);
            return;
        }
        if (module == Module.CHAT_TO_GAME) {
            //转发消息到世界消息队列
            worldMessageManager.addMessage(handler, session, request);
        } else {
            //同步执行
            handler.dispatch(session, request);
        }
    }

    @Override
    public void registerAsyncMessage(short module, short cmd) {
        Set<Short> cmds = ASYNC_MESSAGE.get(module);
        if (null == cmds) {
            cmds = new HashSet<>();
            ASYNC_MESSAGE.put(module, cmds);
        }
        cmds.add(cmd);
    }

    @Override
    public void registerAsyncModule(short module) {
        ASYNC_MODULE.add(module);
    }

    @Override
    public void handlerAsyncMessage() {
         for(int i=0;i<100;i++){
             KeyValuePair<Handler,KeyValuePair<IoSession,Request>> pair=messageQueue.poll();
             if(null==pair){
                 break;
             }
             Handler handler=pair.getKey();
             KeyValuePair<IoSession,Request> pair1=pair.getValue();
             IoSession session=pair1.getKey();
             Request request=pair1.getValue();
             try{
                 handler.dispatch(session,request);
             }catch (Exception e){
                 LOGGER.error("dispatcherimpl handlemessage error:",e);
             }
         }
    }

    private boolean isAsyncMessage(short module, short cmd) {
        Set<Short> cmds = ASYNC_MESSAGE.get(module);
        if (null == cmds) {
            return false;
        }
        return cmds.contains(cmd);
    }

    private boolean isAsyncModule(short module) {
        return ASYNC_MODULE.contains(module);
    }

    private void addMessage(Handler handler,IoSession session,Request request){
        KeyValuePair<IoSession,Request> pair=new KeyValuePair<>(session,request);
        messageQueue.offer(new KeyValuePair<>(handler,pair));
    }
}
