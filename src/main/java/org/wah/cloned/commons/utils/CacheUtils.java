package org.wah.cloned.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.socket.WebSocketSession;
import org.wah.cloned.bot.entity.WechatBot;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CacheUtils{

    private static Map<String, WechatBot> bots = new HashMap<String, WechatBot>();
    private static Map<String, WebSocketSession> sessions = new HashMap<String, WebSocketSession>();

    public static WechatBot getBot(String wechatId){
        if(StringUtils.isBlank(wechatId)){
            throw new RuntimeException("微信ID不能为空");
        }

        return bots.get(wechatId);
    }

    public static void putBot(WechatBot bot){
        if(bot != null && !StringUtils.isBlank(bot.getWechatId())){
            bots.put(bot.getWechatId(), bot);
        }
    }

    public static WebSocketSession getSession(String wechatId){
        if(StringUtils.isBlank(wechatId)){
            throw new RuntimeException("微信ID不能为空");
        }

        return sessions.get(wechatId);
    }

    public static void putSession(String wechatId, WebSocketSession session){
        if(StringUtils.isBlank(wechatId)){
            throw new RuntimeException("微信ID不能为空");
        }
        if(session == null){
            throw new RuntimeException("Web Socket Session不能为空");
        }

        WebSocketSession source = getSession(wechatId);

        if(source == null || !source.isOpen()){
            sessions.put(wechatId, session);
        }
    }

    public static void deleteSession(String wechatId){
        if(StringUtils.isBlank(wechatId)){
            throw new RuntimeException("微信ID不能为空");
        }

        WebSocketSession session = getSession(wechatId);

        if(session != null && session.isOpen()){
            try{
                session.close();
                sessions.remove(wechatId);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
