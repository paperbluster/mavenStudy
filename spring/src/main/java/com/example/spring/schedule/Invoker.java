package com.example.spring.schedule;

import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;

/**调度接口
 * @author wanjun
 * @create 2022-09-15 7:55
 */
public interface Invoker {
    WriteFuture invoke(final IoSession session,Request request,Response response);
}
