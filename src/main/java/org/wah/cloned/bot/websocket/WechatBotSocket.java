package org.wah.cloned.bot.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.wah.cloned.bot.service.WechatBotService;
import org.wah.cloned.commons.utils.CacheUtils;

public class WechatBotSocket extends TextWebSocketHandler {

    @Autowired
    private WechatBotService wechatBotService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        String wechatId = (String) session.getAttributes().get("wechatId");

        CacheUtils.putSession(wechatId, session);
        wechatBotService.login(wechatId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{

    }
}
