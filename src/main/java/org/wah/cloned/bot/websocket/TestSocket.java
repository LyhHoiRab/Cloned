package org.wah.cloned.bot.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class TestSocket implements WebSocketHandler{

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        System.out.println("connection started");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception{
        System.out.println("got message");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception{

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception{
        System.out.println("connection closed");
    }

    @Override
    public boolean supportsPartialMessages(){
        return false;
    }
}
