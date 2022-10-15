package com.example.spring.socket;

import com.example.spring.schedule.Request;
import org.apache.mina.core.session.IoSession;

/**请求业务处理接口
 * @author wanjun
 * @create 2022-10-05 14:14
 */
public interface Handler {
    /**
     * 分发
     * @param session
     * @param request
     */
    void dispatch(IoSession session, Request request);
}

