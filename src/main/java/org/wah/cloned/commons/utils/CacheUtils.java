package org.wah.cloned.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.wah.cloned.bot.entity.WechatBot;

import java.util.HashMap;
import java.util.Map;

public class CacheUtils{

    private static Map<String, WechatBot> bots = new HashMap<String, WechatBot>();

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
