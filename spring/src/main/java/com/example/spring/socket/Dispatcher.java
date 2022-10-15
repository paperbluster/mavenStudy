package com.example.spring.socket;

import com.beust.ah.A;
import com.example.spring.schedule.Request;
import org.apache.mina.core.session.IoSession;

/**模块分发器接口
 * @author wanjun
 * @create 2022-10-05 14:53
 */
public interface Dispatcher {

    /**
     * 设置模块处理器
     * @param module
     * @param handler
     */
    void put(short module,Handler handler);

    /**
     * 派发请求
     * @param session
     * @param request
     */
    void dispatch(IoSession session, Request request);

    /**
     * 注册异步消息
     * @param module
     * @param cmd
     */
    void registerAsyncMessage(short module,short cmd);

    void registerAsyncModule(short module);

    /**
     * 处理队列消息
     */
    void handlerAsyncMessage();


}
