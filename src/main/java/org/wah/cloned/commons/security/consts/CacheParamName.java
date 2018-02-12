package org.wah.cloned.commons.security.consts;

import org.springframework.web.socket.WebSocketSession;
import org.wah.cloned.core.wechat.entity.WechatBot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheParamName{

    //微信机器人
    public static Map<String, WechatBot> bots = new HashMap<String, WechatBot>();

    //socket客户端
    public static Map<String, WebSocketSession> socketSessions = new HashMap<String, WebSocketSession>();

    //客服分配池
    public static Map<String, List<String>> allocations = new HashMap<String, List<String>>();
}
