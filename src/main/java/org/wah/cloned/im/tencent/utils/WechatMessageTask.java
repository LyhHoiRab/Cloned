package org.wah.cloned.im.tencent.utils;

import io.github.biezhi.wechat.api.model.WeChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.wah.cloned.bot.entity.WechatBot;
import org.wah.cloned.bot.service.WechatBotService;
import org.wah.cloned.commons.security.consts.CacheParamName;
import org.wah.cloned.commons.utils.CacheUtils;
import org.wah.cloned.core.wechat.entity.WechatFriend;
import org.wah.cloned.core.wechat.service.WechatFriendService;
import org.wah.doraemon.utils.RedisUtils;
import redis.clients.jedis.ShardedJedisPool;

@Component
public class WechatMessageTask{

    @Autowired
    private WechatFriendService wechatFriendService;

    @Autowired
    private WechatBotService wechatBotService;

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Transactional(readOnly = false)
    public void execute(){
        WeChatMessage message = RedisUtils.lpop(shardedJedisPool.getResource(), CacheParamName.WECHAT_MESSAGE_LIST, WeChatMessage.class);

        if(message != null){
            WechatBot bot = CacheUtils.getBot(message.getWechatId());
            WechatFriend friend;

            switch(message.getMsgType()){
                case TEXT:
                    friend = wechatFriendService.allot(bot, message.getFromUserName());
                    wechatBotService.sendText(friend, message);
                    break;
                case EMOTICONS:
                    friend = wechatFriendService.allot(bot, message.getFromUserName());
                    wechatBotService.sendEmoticons(friend, message);
                    break;
                case IMAGE:
                    friend = wechatFriendService.allot(bot, message.getFromUserName());
                    wechatBotService.sendImage(friend, message, bot);
                    break;
                case VOICE:
                    friend = wechatFriendService.allot(bot, message.getFromUserName());
                    wechatBotService.sendVoice(friend, message, bot);
                    break;
                case VIDEO:
                    friend = wechatFriendService.allot(bot, message.getFromUserName());
                    wechatBotService.sendVideo(friend, message, bot);
                case SYSTEM:
                    if("收到红包，请在手机上查看".equals(message.getText())){
                        friend = wechatFriendService.allot(bot, message.getFromUserName());
                        wechatBotService.sendRedPacket(friend, message);
                    }
                    break;
                case SHARE:
                    if("微信转账".equals(message.getRaw().getFileName())){
                        friend = wechatFriendService.allot(bot, message.getFromUserName());
                        wechatBotService.sendTransfer(friend, message);
                    }
                    break;
                case ADD_FRIEND:
                    wechatBotService.verify(bot, message);
                default:
                    break;
            }
        }
    }
}
