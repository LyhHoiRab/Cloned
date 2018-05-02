package org.wah.cloned.bot.service;

import io.github.biezhi.wechat.api.model.WeChatMessage;
import org.wah.cloned.bot.entity.WechatBot;
import org.wah.cloned.core.wechat.entity.Message;
import org.wah.cloned.core.wechat.entity.WechatFriend;

public interface WechatBotService{

    /**
     * 微信登录
     */
    void login(String id);

    /**
     *
     * 微信机器人重置
     */
    void reset(String id);

    /**
     * 关闭socket
     */
    void closeSocket(String id);

    /**
     * 文本消息
     */
    void sendText(WechatFriend friend, WeChatMessage message);

    /**
     * 红包消息
     */
    void sendRedPacket(WechatFriend friend, WeChatMessage message);

    /**
     * 转账消息
     */
    void sendTransfer(WechatFriend friend, WeChatMessage message);

    /**
     * 表情消息
     */
    void sendEmoticons(WechatFriend friend, WeChatMessage message);

    /**
     * 图片消息
     */
    void sendImage(WechatFriend friend, WeChatMessage message, WechatBot bot);

    /**
     * 语音消息
     */
    void sendVoice(WechatFriend friend, WeChatMessage message, WechatBot bot);

    /**
     * 视频消息
     */
    void sendVideo(WechatFriend friend, WeChatMessage message, WechatBot bot);

    /**
     * 自己发送的消息
     */
    void sendBySelf(WechatFriend friend, WeChatMessage message);

    /**
     * 非好友消息
     */
    void sendNotFriend(WechatFriend friend, WeChatMessage message);

    /**
     * 查询微信号
     */
    void sendGetWxno(String remarkname);

    /**
     * 金额消息回调
     */
    void amountCallback(Message message);

    /**
     * 获取微信号消息回调
     */
    void getWxnoCallback(Message message);

    /**
     * 好友验证
     */
    void verify(WechatBot bot, WeChatMessage message);

    /**
     * 好友验证通过消息通知
     */
    void friendAdded(WechatFriend friend);
}
