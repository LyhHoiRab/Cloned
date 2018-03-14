package org.wah.cloned.bot.service;

import io.github.biezhi.wechat.api.constant.Config;
import io.github.biezhi.wechat.api.model.WeChatMessage;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.cloned.bot.entity.WechatBot;
import org.wah.cloned.commons.utils.CacheUtils;
import org.wah.cloned.core.wechat.consts.MessageType;
import org.wah.cloned.core.wechat.dao.WechatDao;
import org.wah.cloned.core.wechat.entity.Message;
import org.wah.cloned.core.wechat.entity.Wechat;
import org.wah.cloned.core.wechat.entity.WechatFriend;
import org.wah.cloned.im.tencent.consts.IMMessageType;
import org.wah.cloned.im.tencent.dao.IMUserDao;
import org.wah.cloned.im.tencent.entity.IMMessage;
import org.wah.cloned.im.tencent.entity.IMMessageBody;
import org.wah.cloned.im.tencent.entity.IMUser;
import org.wah.cloned.im.tencent.utils.IMUtils;
import org.wah.doraemon.security.exception.ServiceException;
import org.wah.doraemon.utils.ObjectUtils;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class WechatBotServiceImpl implements WechatBotService{

    @Autowired
    private WechatDao wechatDao;

    @Autowired
    private IMUserDao imUserDao;

    /**
     * 微信登录
     */
    @Override
    @Transactional(readOnly = false)
    public void login(String id){
        Assert.hasText(id, "微信ID不能为空");
        //查找微信
        Wechat wechat = wechatDao.getById(id);
        //创建机器人
        WechatBot bot = new WechatBot(Config.me().autoLogin(false).showTerminal(false).autoAddFriend(true));
        WechatApi api = new WechatApi(bot);
        bot.setWechatApi(api);
        bot.setWechatId(wechat.getId());
        //登录
        api.login();
        //缓存
        CacheUtils.putBot(bot);
    }

    /**
     * 文本消息
     */
    @Override
    public void sendText(WechatFriend friend, WeChatMessage message){
        Assert.notNull(friend, "微信好友信息不能为空");
        Assert.notNull(message, "微信消息不能为空");

        //客服
        IMUser service = imUserDao.getByName(friend.getServiceId());
        //微信
        IMUser wechat = imUserDao.getByName(message.getWechatId());
        //管理员
        IMUser admin = imUserDao.getAdminByAppletId(service.getAppletId());
        //消息组
        List<IMMessage> messages = new ArrayList<IMMessage>();
        //消息
        Message target = new Message();
        target.setHeadImgUrl(friend.getHeadImgUrl());
        target.setNickname(friend.getNickname());
        target.setRemarkname(friend.getRemarkname());
        target.setText(message.getText());
        target.setType(MessageType.TEXT);
        target.setCreateTime(new Date());

        IMMessageBody body = new IMMessageBody();
        body.setFromAccount(wechat.getName());
        body.setToAccount(service.getName());
        body.setMessages(messages);

        Map<String, String> content = new HashMap<String, String>();
        content.put("Text", ObjectUtils.serialize(target));
        IMMessage imMessage = new IMMessage();
        imMessage.setType(IMMessageType.TEXT);
        imMessage.setContent(content);
        messages.add(imMessage);
        //转发
        IMUtils.sendMsg(admin.getSig(), admin.getAppId(), admin.getName(), body);
    }

    /**
     * 红包消息
     */
    @Override
    public void sendRedPacket(WechatFriend friend, WeChatMessage message){
        Assert.notNull(friend, "微信好友信息不能为空");
        Assert.notNull(message, "微信消息不能为空");

        //客服
        IMUser service = imUserDao.getByName(friend.getServiceId());
        //微信
        IMUser wechat = imUserDao.getByName(message.getWechatId());
        //管理员
        IMUser admin = imUserDao.getAdminByAppletId(service.getAppletId());
        //消息组
        List<IMMessage> messages = new ArrayList<IMMessage>();
        //消息
        Message target = new Message();
        target.setHeadImgUrl(friend.getHeadImgUrl());
        target.setNickname(friend.getNickname());
        target.setRemarkname(friend.getRemarkname());
        target.setText(message.getText());
        target.setType(MessageType.RED_PACKET);
        target.setCreateTime(new Date());

        IMMessageBody body = new IMMessageBody();
        body.setFromAccount(wechat.getName());
        body.setToAccount(service.getName());
        body.setMessages(messages);

        Map<String, String> content = new HashMap<String, String>();
        content.put("Text", ObjectUtils.serialize(target));
        IMMessage imMessage = new IMMessage();
        imMessage.setType(IMMessageType.TEXT);
        imMessage.setContent(content);
        messages.add(imMessage);
        //转发给销售
        IMUtils.sendMsg(admin.getSig(), admin.getAppId(), admin.getName(), body);
        //转发给微信
        body.setFromAccount(service.getName());
        body.setToAccount(wechat.getName());
        IMUtils.sendMsg(admin.getSig(), admin.getAppId(), admin.getName(), body);
    }

    /**
     * 转账消息
     */
    @Override
    public void sendTransfer(WechatFriend friend, WeChatMessage message){
        Assert.notNull(friend, "微信好友信息不能为空");
        Assert.notNull(message, "微信消息不能为空");

        //客服
        IMUser service = imUserDao.getByName(friend.getServiceId());
        //微信
        IMUser wechat = imUserDao.getByName(message.getWechatId());
        //管理员
        IMUser admin = imUserDao.getAdminByAppletId(service.getAppletId());
        //消息组
        List<IMMessage> messages = new ArrayList<IMMessage>();
        //消息
        Message target = new Message();
        target.setHeadImgUrl(friend.getHeadImgUrl());
        target.setNickname(friend.getNickname());
        target.setRemarkname(friend.getRemarkname());
        target.setText(message.getRaw().getFileName());
        target.setType(MessageType.TRANSFER);
        target.setCreateTime(new Date());

        IMMessageBody body = new IMMessageBody();
        body.setFromAccount(wechat.getName());
        body.setToAccount(service.getName());
        body.setMessages(messages);

        Map<String, String> content = new HashMap<String, String>();
        content.put("Text", ObjectUtils.serialize(target));
        IMMessage imMessage = new IMMessage();
        imMessage.setType(IMMessageType.TEXT);
        imMessage.setContent(content);
        messages.add(imMessage);
        //转发给销售
        IMUtils.sendMsg(admin.getSig(), admin.getAppId(), admin.getName(), body);
        //转发给微信
        body.setFromAccount(service.getName());
        body.setToAccount(wechat.getName());
        IMUtils.sendMsg(admin.getSig(), admin.getAppId(), admin.getName(), body);
    }

    /**
     * 表情消息
     */
    @Override
    public void sendEmoticons(WechatFriend friend, WeChatMessage message){
        Assert.notNull(friend, "微信好友信息不能为空");
        Assert.notNull(message, "微信消息不能为空");

        //解释xml
        String text = message.getText();

        if(!StringUtils.isBlank(text)){
            try{
                Document document = DocumentHelper.parseText(message.getText());
                Element root = document.getRootElement();
                Element emoji = root.element("emoji");
                text = emoji.attribute("cdnurl").getValue();
            }catch(Exception e){
                throw new ServiceException(e.getMessage(), e);
            }
        }

        //客服
        IMUser service = imUserDao.getByName(friend.getServiceId());
        //微信
        IMUser wechat = imUserDao.getByName(message.getWechatId());
        //管理员
        IMUser admin = imUserDao.getAdminByAppletId(service.getAppletId());
        //消息组
        List<IMMessage> messages = new ArrayList<IMMessage>();
        //消息
        Message target = new Message();
        target.setHeadImgUrl(friend.getHeadImgUrl());
        target.setNickname(friend.getNickname());
        target.setRemarkname(friend.getRemarkname());
        target.setText(text);
        target.setType(MessageType.EMOTICONS);
        target.setCreateTime(new Date());

        IMMessageBody body = new IMMessageBody();
        body.setFromAccount(wechat.getName());
        body.setToAccount(service.getName());
        body.setMessages(messages);

        Map<String, String> content = new HashMap<String, String>();
        content.put("Text", ObjectUtils.serialize(target));
        IMMessage imMessage = new IMMessage();
        imMessage.setType(IMMessageType.TEXT);
        imMessage.setContent(content);
        messages.add(imMessage);
        //转发
        IMUtils.sendMsg(admin.getSig(), admin.getAppId(), admin.getName(), body);
    }

    /**
     * 图片消息
     */
    @Override
    public void sendImage(WechatFriend friend, WeChatMessage message, WechatBot bot){
        Assert.notNull(friend, "微信好友信息不能为空");
        Assert.notNull(message, "微信消息不能为空");
        Assert.notNull(bot, "微信机器人不能为空");

        //客服
        IMUser service = imUserDao.getByName(friend.getServiceId());
        //微信
        IMUser wechat = imUserDao.getByName(message.getWechatId());
        //管理员
        IMUser admin = imUserDao.getAdminByAppletId(service.getAppletId());
        //消息组
        List<IMMessage> messages = new ArrayList<IMMessage>();
        //消息
        Message target = new Message();
        target.setHeadImgUrl(friend.getHeadImgUrl());
        target.setNickname(friend.getNickname());
        target.setRemarkname(friend.getRemarkname());
        target.setText(bot.getWechatApi().downloadImg(message.getId()));
        target.setType(MessageType.IMAGE);
        target.setCreateTime(new Date());

        IMMessageBody body = new IMMessageBody();
        body.setFromAccount(wechat.getName());
        body.setToAccount(service.getName());
        body.setMessages(messages);

        Map<String, String> content = new HashMap<String, String>();
        content.put("Text", ObjectUtils.serialize(target));
        IMMessage imMessage = new IMMessage();
        imMessage.setType(IMMessageType.TEXT);
        imMessage.setContent(content);
        messages.add(imMessage);
        //转发
        IMUtils.sendMsg(admin.getSig(), admin.getAppId(), admin.getName(), body);
    }

    /**
     * 语音消息
     */
    @Override
    public void sendVoice(WechatFriend friend, WeChatMessage message, WechatBot bot){
        Assert.notNull(friend, "微信好友信息不能为空");
        Assert.notNull(message, "微信消息不能为空");
        Assert.notNull(bot, "微信机器人不能为空");

        //客服
        IMUser service = imUserDao.getByName(friend.getServiceId());
        //微信
        IMUser wechat = imUserDao.getByName(message.getWechatId());
        //管理员
        IMUser admin = imUserDao.getAdminByAppletId(service.getAppletId());
        //消息组
        List<IMMessage> messages = new ArrayList<IMMessage>();
        //消息
        Message target = new Message();
        target.setHeadImgUrl(friend.getHeadImgUrl());
        target.setNickname(friend.getNickname());
        target.setRemarkname(friend.getRemarkname());
        target.setText(bot.getWechatApi().downloadVoice(message.getId()));
        target.setType(MessageType.VOICE);
        target.setCreateTime(new Date());

        IMMessageBody body = new IMMessageBody();
        body.setFromAccount(wechat.getName());
        body.setToAccount(service.getName());
        body.setMessages(messages);

        Map<String, String> content = new HashMap<String, String>();
        content.put("Text", ObjectUtils.serialize(target));
        IMMessage imMessage = new IMMessage();
        imMessage.setType(IMMessageType.TEXT);
        imMessage.setContent(content);
        messages.add(imMessage);
        //转发
        IMUtils.sendMsg(admin.getSig(), admin.getAppId(), admin.getName(), body);
    }

    /**
     * 视频消息
     */
    @Override
    public void sendVideo(WechatFriend friend, WeChatMessage message, WechatBot bot){
        Assert.notNull(friend, "微信好友信息不能为空");
        Assert.notNull(message, "微信消息不能为空");
        Assert.notNull(bot, "微信机器人不能为空");

        //客服
        IMUser service = imUserDao.getByName(friend.getServiceId());
        //微信
        IMUser wechat = imUserDao.getByName(message.getWechatId());
        //管理员
        IMUser admin = imUserDao.getAdminByAppletId(service.getAppletId());
        //消息组
        List<IMMessage> messages = new ArrayList<IMMessage>();
        //消息
        Message target = new Message();
        target.setHeadImgUrl(friend.getHeadImgUrl());
        target.setNickname(friend.getNickname());
        target.setRemarkname(friend.getRemarkname());
        target.setText(bot.getWechatApi().downloadVideo(message.getId()));
        target.setType(MessageType.VIDEO);
        target.setCreateTime(new Date());

        IMMessageBody body = new IMMessageBody();
        body.setFromAccount(wechat.getName());
        body.setToAccount(service.getName());
        body.setMessages(messages);

        Map<String, String> content = new HashMap<String, String>();
        content.put("Text", ObjectUtils.serialize(target));
        IMMessage imMessage = new IMMessage();
        imMessage.setType(IMMessageType.TEXT);
        imMessage.setContent(content);
        messages.add(imMessage);
        //转发
        IMUtils.sendMsg(admin.getSig(), admin.getAppId(), admin.getName(), body);
    }


    /**
     * 好友验证
     */
    @Override
    public void verify(WechatBot bot, WeChatMessage message){
        Assert.notNull(bot, "微信机器人不能为空");
        Assert.notNull(message, "微信信息不能为空");

        bot.getWechatApi().verify(message.getRaw().getRecommend());
    }
}
