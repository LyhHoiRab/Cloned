package org.wah.cloned.commons.utils;

import io.github.biezhi.wechat.api.model.WeChatMessage;
import org.apache.commons.lang3.StringUtils;
import org.wah.cloned.bot.entity.WechatBot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CacheUtils{

    private static BlockingQueue<WeChatMessage> messages = new LinkedBlockingQueue<WeChatMessage>();
    private static Map<String, WechatBot> bots = new HashMap<String, WechatBot>();

    public static synchronized void addMessages(List<WeChatMessage> list){
        try{
            if(null == list || list.size() == 0){
                return;
            }
            for(WeChatMessage message : list){
                messages.put(message);
            }
        }catch(InterruptedException e){
            System.out.println("向队列添加 Message 出错");
        }
    }

    public static synchronized WeChatMessage nextMessage(){
        try{
            return messages.take();
        }catch(InterruptedException e) {
            System.out.println("从队列获取 Message 出错");
            return null;
        }
    }

    public static synchronized boolean hasMessage(){
        return messages.size() > 0;
    }

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
}
