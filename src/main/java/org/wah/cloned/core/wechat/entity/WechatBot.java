package org.wah.cloned.core.wechat.entity;

import io.github.biezhi.wechat.WeChatBot;
import io.github.biezhi.wechat.api.annotation.Bind;
import io.github.biezhi.wechat.api.constant.Config;
import io.github.biezhi.wechat.api.enums.AccountType;
import io.github.biezhi.wechat.api.enums.MsgType;
import io.github.biezhi.wechat.api.model.Account;
import io.github.biezhi.wechat.api.model.WeChatMessage;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.wah.cloned.commons.security.consts.CacheParamName;
import org.wah.cloned.commons.security.context.ApplicationContextUtils;
import org.wah.cloned.core.wechat.service.WechatFriendService;
import org.wah.cloned.core.wechat.service.WechatFriendServiceImpl;
import org.wah.doraemon.consts.Sex;

import java.io.IOException;
import java.text.MessageFormat;

public class WechatBot extends WeChatBot{

    private WechatFriendService wechatFriendService = (WechatFriendService) ApplicationContextUtils.getByClass(WechatFriendService.class);

    @Getter
    @Setter
    private String wechatId;

    public WechatBot(Config config){
        super(config);
    }

    @Bind(msgType = {MsgType.TEXT, MsgType.VIDEO, MsgType.IMAGE, MsgType.EMOTICONS}, accountType = AccountType.TYPE_FRIEND)
    public void friendMessage(WeChatMessage message){
        String text = message.getText();
        String fromUserName = message.getFromUserName();

        Account account = api().getAccountById(fromUserName);
        String remarkName = account.getRemarkName();

        if("吴彦祖".equals(remarkName)){
            WebSocketSession session = CacheParamName.socketSessions.get("9fe59d3ac70843fbbf7379fb9b07696a");
            if(session != null){
                try{
                    session.sendMessage(new TextMessage(MessageFormat.format("[{0}]{1}", remarkName, text)));
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

//        String remarkname = account.getRemarkName();
//        WechatFriend friend = null;

        //已备注
//        if(!StringUtils.isBlank(remarkname)){
//            friend = wechatFriendService.getByWechatIdAndRemarkname(getWechatId(), remarkname);
//        }
//        if(friend == null){
//            friend = wechatFriendService.save(getWechatId(), account.getNickName(), null);
//            account.setRemarkName(friend.getRemarkname());
//        }

//        api().sendText(fromUserName, nickname);
    }
}
