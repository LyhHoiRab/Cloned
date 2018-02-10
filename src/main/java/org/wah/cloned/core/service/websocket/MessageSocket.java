package org.wah.cloned.core.service.websocket;

import io.github.biezhi.wechat.api.model.Account;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.wah.cloned.commons.security.consts.CacheParamName;
import org.wah.cloned.core.wechat.entity.WechatBot;

import java.text.MessageFormat;

@Component
public class MessageSocket extends TextWebSocketHandler{

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
//        System.out.println((String) session.getAttributes().get(SessionParamName.ACCOUNT_ID));
//        System.out.println(message.getPayload());
        session.sendMessage(new TextMessage(MessageFormat.format("[{0}]{1}", "客服", message.getPayload())));

        WechatBot bot = CacheParamName.bots.get("9fe59d3ac70843fbbf7379fb9b07696a");
        if(bot != null){
            Account account = bot.api().getAccountByName("吴彦祖");
            bot.api().sendText(account.getUserName(), message.getPayload());
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
//        String accountId = (String) session.getAttributes().get(SessionParamName.ACCOUNT_ID);
        CacheParamName.socketSessions.put("9fe59d3ac70843fbbf7379fb9b07696a", session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
//        String accountId = (String) session.getAttributes().get(SessionParamName.ACCOUNT_ID);
        CacheParamName.socketSessions.remove("9fe59d3ac70843fbbf7379fb9b07696a");
    }
}
