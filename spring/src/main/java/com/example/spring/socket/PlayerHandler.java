package com.example.spring.socket;

import com.example.spring.schedule.Invoker;
import com.example.spring.schedule.MsgBody;
import com.example.spring.schedule.Request;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wanjun
 * @create 2022-10-05 15:26
 */
@Component
public class PlayerHandler extends BaseHandler {
    @Override
    protected short getModule() {
        return Module.PLAYER;
    }

    @Override
    protected void inititialize() {
          putInvoker(PlayerCmd.login, new Invoker() {
              @Override
              public WriteFuture invoke(IoSession session, Request request, Response response) {
                  int playerUid=request.getPlayerId();
                  List<String> rewardList=new ArrayList<>();
                  int propId=request.getInts.get(0);
                  int result=playerManager.login(playerUid,propId);
                  MsgBody msgBody=new MsgBody();
                  msgBody.addInt(result);
                  msgBody.finish();
                  response.setMsgBody(msgBody);
                  return session.write(response);
              }
          });
    }
}
