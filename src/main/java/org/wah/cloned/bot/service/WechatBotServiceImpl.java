package org.wah.cloned.bot.service;

import io.github.biezhi.wechat.api.constant.Config;
import io.github.biezhi.wechat.api.enums.MsgType;
import io.github.biezhi.wechat.api.model.WeChatMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.socket.WebSocketSession;
import org.wah.cloned.bot.entity.WechatBot;
import org.wah.cloned.commons.security.exception.handler.ExceptionHandler;
import org.wah.cloned.commons.utils.CacheUtils;
import org.wah.cloned.core.service.dao.ServiceDao;
import org.wah.cloned.core.wechat.consts.MessageType;
import org.wah.cloned.core.wechat.consts.WechatStatus;
import org.wah.cloned.core.wechat.dao.MessageDao;
import org.wah.cloned.core.wechat.dao.WechatDao;
import org.wah.cloned.core.wechat.dao.WechatFriendDao;
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

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private WechatFriendDao wechatFriendDao;

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
        //缓存
        CacheUtils.putBot(bot);
        //登录
        api.login();
    }

    /**
     * 微信机器人重置
     */
    @Override
    @Transactional(readOnly = false)
    public void reset(String id){
        Assert.hasText(id, "微信ID不能为空");

        //查找微信
        Wechat wechat = wechatDao.getById(id);
        //查询缓存
        WechatBot bot = CacheUtils.getBot(wechat.getId());

        if(bot != null){
            if(bot.isRunning() || bot.getWechatApi().isLogging()){
                //登出
                bot.getWechatApi().logout();
                //停止线程
                Thread thread = bot.getWechatApi().getThread();
                if(thread != null && thread.isAlive()){
                    thread.stop();
                }

                bot.getWechatApi().setBot(null);
                bot.getWechatApi().setClient(null);
                bot.setWechatApi(null);
                //删除缓存
                CacheUtils.deleteBot(id);
            }
        }

        //更新状态
        wechat.setStatus(WechatStatus.OFFLINE);
        wechatDao.saveOrUpdate(wechat);

        //删除session缓存
        CacheUtils.deleteSession(id);
    }

    /**
     * 关闭socket
     */
    @Override
    @Transactional(readOnly = false)
    public void closeSocket(String id){
        Assert.hasText(id, "微信ID不能为空");

        //查找微信
        Wechat wechat = wechatDao.getById(id);
        //查询缓存
        WechatBot bot = CacheUtils.getBot(wechat.getId());

        if(bot != null){
            if(bot.getWechatApi().isLogging()){
                //登出
                bot.getWechatApi().logout();
                //停止线程
                Thread thread = bot.getWechatApi().getThread();
                if(thread != null && thread.isAlive()){
                    thread.stop();
                }
                bot.getWechatApi().setBot(null);
                bot.getWechatApi().setClient(null);
                bot.setWechatApi(null);

                //删除缓存
                CacheUtils.deleteBot(id);
                //更新状态
                wechat.setStatus(WechatStatus.OFFLINE);
                wechatDao.saveOrUpdate(wechat);
            }
        }

        //删除session缓存
        CacheUtils.deleteSession(id);
    }

    /**
     * 文本消息
     */
    @Override
    @Transactional(readOnly = false)
    public void sendText(WechatFriend friend, WeChatMessage message){
        Assert.notNull(friend, "微信好友信息不能为空");
        Assert.notNull(message, "微信消息不能为空");

        //消息
        Message target = new Message();
        target.setWechatId(message.getWechatId());
        target.setFriend(friend);
        target.setText(message.getText());
        target.setType(MessageType.TEXT);
        target.setSendByService(message.getIsSelf());
        target.setCreateTime(new Date());

        if(!StringUtils.isBlank(friend.getServiceId()) && !message.getIsSelf()){
            //设置销售
            org.wah.cloned.core.service.entity.Service servicer = serviceDao.getById(friend.getServiceId());
            target.setService(servicer);
            //旧版设计属性
            target.setRemarkname(friend.getRemarkname());
            target.setHeadImgUrl(friend.getHeadImgUrl());
            target.setNickname(friend.getNickname());
            //客服
            IMUser service = imUserDao.getByName(friend.getServiceId());
            //微信
            IMUser wechat = imUserDao.getByName(message.getWechatId());
            //管理员
            IMUser admin = imUserDao.getAdminByAppletId(service.getAppletId());
            //消息组
            List<IMMessage> messages = new ArrayList<IMMessage>();

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
        //保存消息
        messageDao.saveOrUpdate(target);
    }

    /**
     * 红包消息
     */
    @Override
    @Transactional(readOnly = false)
    public void sendRedPacket(WechatFriend friend, WeChatMessage message){
        Assert.notNull(friend, "微信好友信息不能为空");
        Assert.notNull(message, "微信消息不能为空");

        //消息
        Message target = new Message();
        target.setWechatId(message.getWechatId());
        target.setFriend(friend);
        target.setText("收到一个红包，等待确认");
        target.setType(MessageType.RED_PACKET);
        target.setSendByService(!message.getIsSelf());
        target.setCreateTime(new Date());

        if(!StringUtils.isBlank(friend.getServiceId()) && !message.getIsSelf()){
            //设置销售
            org.wah.cloned.core.service.entity.Service servicer = serviceDao.getById(friend.getServiceId());
            target.setService(servicer);
            //旧版设计属性
            target.setRemarkname(friend.getRemarkname());
            target.setHeadImgUrl(friend.getHeadImgUrl());
            target.setNickname(friend.getNickname());
            //客服
            IMUser service = imUserDao.getByName(friend.getServiceId());
            //微信
            IMUser wechat = imUserDao.getByName(message.getWechatId());
            //管理员
            IMUser admin = imUserDao.getAdminByAppletId(service.getAppletId());
            //消息组
            List<IMMessage> messages = new ArrayList<IMMessage>();

            IMMessageBody body = new IMMessageBody();
            body.setFromAccount(service.getName());
            body.setToAccount(wechat.getName());
            body.setMessages(messages);

            Map<String, String> content = new HashMap<String, String>();
            content.put("Text", ObjectUtils.serialize(target));
            IMMessage imMessage = new IMMessage();
            imMessage.setType(IMMessageType.TEXT);
            imMessage.setContent(content);
            messages.add(imMessage);

            IMUtils.sendMsg(admin.getSig(), admin.getAppId(), admin.getName(), body);
        }
        //保存消息
        messageDao.saveOrUpdate(target);
    }

    /**
     * 转账消息
     */
    @Override
    @Transactional(readOnly = false)
    public void sendTransfer(WechatFriend friend, WeChatMessage message){
        Assert.notNull(friend, "微信好友信息不能为空");
        Assert.notNull(message, "微信消息不能为空");

        //消息
        Message target = new Message();
        target.setWechatId(message.getWechatId());
        target.setFriend(friend);
        target.setText("收到一笔转账，等待确认");
        target.setType(MessageType.TRANSFER);
        target.setSendByService(!message.getIsSelf());
        target.setCreateTime(new Date());

        if(!StringUtils.isBlank(friend.getServiceId()) && !message.getIsSelf()){
            //设置销售
            org.wah.cloned.core.service.entity.Service servicer = serviceDao.getById(friend.getServiceId());
            target.setService(servicer);
            //旧版设计属性
            target.setRemarkname(friend.getRemarkname());
            target.setHeadImgUrl(friend.getHeadImgUrl());
            target.setNickname(friend.getNickname());
            //客服
            IMUser service = imUserDao.getByName(friend.getServiceId());
            //微信
            IMUser wechat = imUserDao.getByName(message.getWechatId());
            //管理员
            IMUser admin = imUserDao.getAdminByAppletId(service.getAppletId());
            //消息组
            List<IMMessage> messages = new ArrayList<IMMessage>();

            IMMessageBody body = new IMMessageBody();
            body.setFromAccount(service.getName());
            body.setToAccount(wechat.getName());
            body.setMessages(messages);

            Map<String, String> content = new HashMap<String, String>();
            content.put("Text", ObjectUtils.serialize(target));
            IMMessage imMessage = new IMMessage();
            imMessage.setType(IMMessageType.TEXT);
            imMessage.setContent(content);
            messages.add(imMessage);
            //转发给销售
            IMUtils.sendMsg(admin.getSig(), admin.getAppId(), admin.getName(), body);
        }
        //保存消息
        messageDao.saveOrUpdate(target);
    }

    /**
     * 表情消息
     */
    @Override
    @Transactional(readOnly = false)
    public void sendEmoticons(WechatFriend friend, WeChatMessage message){
        Assert.notNull(friend, "微信好友信息不能为空");
        Assert.notNull(message, "微信消息不能为空");

        //消息
        Message target = new Message();
        target.setWechatId(message.getWechatId());
        target.setFriend(friend);
        target.setText(message.getImagePath());
        target.setType(MessageType.EMOTICONS);
        target.setSendByService(message.getIsSelf());
        target.setCreateTime(new Date());

        if(!StringUtils.isBlank(friend.getServiceId()) && !message.getIsSelf()){
            //设置销售
            org.wah.cloned.core.service.entity.Service servicer = serviceDao.getById(friend.getServiceId());
            target.setService(servicer);
            //旧版设计属性
            target.setRemarkname(friend.getRemarkname());
            target.setHeadImgUrl(friend.getHeadImgUrl());
            target.setNickname(friend.getNickname());
            //客服
            IMUser service = imUserDao.getByName(friend.getServiceId());
            //微信
            IMUser wechat = imUserDao.getByName(message.getWechatId());
            //管理员
            IMUser admin = imUserDao.getAdminByAppletId(service.getAppletId());
            //消息组
            List<IMMessage> messages = new ArrayList<IMMessage>();

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
        //保存消息
        messageDao.saveOrUpdate(target);
    }

    /**
     * 图片消息
     */
    @Override
    @Transactional(readOnly = false)
    public void sendImage(WechatFriend friend, WeChatMessage message, WechatBot bot){
        Assert.notNull(friend, "微信好友信息不能为空");
        Assert.notNull(message, "微信消息不能为空");
        Assert.notNull(bot, "微信机器人不能为空");

        //消息
        Message target = new Message();
        target.setWechatId(message.getWechatId());
        target.setFriend(friend);
        target.setText(message.getImagePath());
        target.setType(MessageType.IMAGE);
        target.setSendByService(message.getIsSelf());
        target.setCreateTime(new Date());

        if(!StringUtils.isBlank(friend.getServiceId()) && !message.getIsSelf()){
            //设置销售
            org.wah.cloned.core.service.entity.Service servicer = serviceDao.getById(friend.getServiceId());
            target.setService(servicer);
            //旧版设计属性
            target.setRemarkname(friend.getRemarkname());
            target.setHeadImgUrl(friend.getHeadImgUrl());
            target.setNickname(friend.getNickname());
            //客服
            IMUser service = imUserDao.getByName(friend.getServiceId());
            //微信
            IMUser wechat = imUserDao.getByName(message.getWechatId());
            //管理员
            IMUser admin = imUserDao.getAdminByAppletId(service.getAppletId());
            //消息组
            List<IMMessage> messages = new ArrayList<IMMessage>();

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
        //保存消息
        messageDao.saveOrUpdate(target);
    }

    /**
     * 语音消息
     */
    @Override
    @Transactional(readOnly = false)
    public void sendVoice(WechatFriend friend, WeChatMessage message, WechatBot bot){
        Assert.notNull(friend, "微信好友信息不能为空");
        Assert.notNull(message, "微信消息不能为空");
        Assert.notNull(bot, "微信机器人不能为空");

        //消息
        Message target = new Message();
        target.setWechatId(message.getWechatId());
        target.setFriend(friend);
        target.setText(message.getVoicePath());
        target.setType(MessageType.VOICE);
        target.setSendByService(message.getIsSelf());
        target.setCreateTime(new Date());

        if(!StringUtils.isBlank(friend.getServiceId()) && !message.getIsSelf()){
            //设置销售
            org.wah.cloned.core.service.entity.Service servicer = serviceDao.getById(friend.getServiceId());
            target.setService(servicer);
            //旧版设计属性
            target.setRemarkname(friend.getRemarkname());
            target.setHeadImgUrl(friend.getHeadImgUrl());
            target.setNickname(friend.getNickname());
            //客服
            IMUser service = imUserDao.getByName(friend.getServiceId());
            //微信
            IMUser wechat = imUserDao.getByName(message.getWechatId());
            //管理员
            IMUser admin = imUserDao.getAdminByAppletId(service.getAppletId());
            //消息组
            List<IMMessage> messages = new ArrayList<IMMessage>();

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
        //保存消息
        messageDao.saveOrUpdate(target);
    }

    /**
     * 视频消息
     */
    @Override
    @Transactional(readOnly = false)
    public void sendVideo(WechatFriend friend, WeChatMessage message, WechatBot bot){
        Assert.notNull(friend, "微信好友信息不能为空");
        Assert.notNull(message, "微信消息不能为空");
        Assert.notNull(bot, "微信机器人不能为空");

        //消息
        Message target = new Message();
        target.setWechatId(message.getWechatId());
        target.setFriend(friend);
        target.setText(message.getVideoPath());
        target.setType(MessageType.VIDEO);
        target.setSendByService(message.getIsSelf());
        target.setCreateTime(new Date());

        if(!StringUtils.isBlank(friend.getServiceId()) && !message.getIsSelf()){
            //设置销售
            org.wah.cloned.core.service.entity.Service servicer = serviceDao.getById(friend.getServiceId());
            target.setService(servicer);
            //旧版设计属性
            target.setRemarkname(friend.getRemarkname());
            target.setHeadImgUrl(friend.getHeadImgUrl());
            target.setNickname(friend.getNickname());
            //客服
            IMUser service = imUserDao.getByName(friend.getServiceId());
            //微信
            IMUser wechat = imUserDao.getByName(message.getWechatId());
            //管理员
            IMUser admin = imUserDao.getAdminByAppletId(service.getAppletId());
            //消息组
            List<IMMessage> messages = new ArrayList<IMMessage>();

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
        //保存消息
        messageDao.saveOrUpdate(target);
    }

    /**
     * 自己发送的消息
     */
    @Override
    @Transactional(readOnly = false)
    public void sendBySelf(WechatFriend friend, WeChatMessage message){
        Assert.notNull(friend, "微信好友信息不能为空");
        Assert.notNull(message, "微信消息不能为空");

        //消息
        Message target = new Message();
        target.setWechatId(message.getWechatId());
        target.setFriend(friend);
        target.setSendByService(message.getIsSelf());
        //设置销售
        org.wah.cloned.core.service.entity.Service servicer = serviceDao.getById(friend.getServiceId());
        target.setService(servicer);

        MsgType type = message.getMsgType();

        switch(type){
            case TEXT:
                target.setText(message.getText());
                target.setType(MessageType.TEXT);
                break;
            case EMOTICONS:
                target.setText(message.getImagePath());
                target.setType(MessageType.EMOTICONS);
                break;
            case IMAGE:
                target.setText(message.getImagePath());
                target.setType(MessageType.IMAGE);
                break;
            case VOICE:
                target.setText(message.getVoicePath());
                target.setType(MessageType.VOICE);
                break;
            case VIDEO:
                target.setText(message.getVideoPath());
                target.setType(MessageType.VIDEO);
                break;
            case SYSTEM:
                target.setType(MessageType.RED_PACKET);
                break;
            case SHARE:
                target.setType(MessageType.TRANSFER);
            default:
                break;
        }

        //保存消息
        messageDao.saveOrUpdate(target);
    }

    /**
     * 非好友消息
     */
    @Override
    @Transactional(readOnly = false)
    public void sendNotFriend(WechatFriend friend, WeChatMessage message){
        Assert.notNull(friend, "微信好友信息不能为空");
        Assert.notNull(message, "微信消息不能为空");

        //消息
        Message target = new Message();
        target.setWechatId(message.getWechatId());
        target.setFriend(friend);
        target.setText(message.getText());
        target.setType(MessageType.NOT_FRIEND);
        target.setSendByService(message.getIsSelf());
        target.setCreateTime(new Date());

        if(!StringUtils.isBlank(friend.getServiceId()) && !message.getIsSelf()){
            //设置销售
            org.wah.cloned.core.service.entity.Service servicer = serviceDao.getById(friend.getServiceId());
            target.setService(servicer);
            //旧版设计属性
            target.setRemarkname(friend.getRemarkname());
            target.setHeadImgUrl(friend.getHeadImgUrl());
            target.setNickname(friend.getNickname());
            //客服
            IMUser service = imUserDao.getByName(friend.getServiceId());
            //微信
            IMUser wechat = imUserDao.getByName(message.getWechatId());
            //管理员
            IMUser admin = imUserDao.getAdminByAppletId(service.getAppletId());
            //消息组
            List<IMMessage> messages = new ArrayList<IMMessage>();

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
        //保存消息
        messageDao.saveOrUpdate(target);
    }

    /**
     * 查询微信号
     */
    @Override
    public void sendGetWxno(String remarkname){
        Assert.notNull(remarkname, "微信好友备注名不能为空");

        WechatFriend friend = wechatFriendDao.getByRemarkname(remarkname);

        //消息
        Message target = new Message();
        target.setWechatId(friend.getWechatId());
        target.setFriend(friend);
        target.setText("获取微信号");
        target.setType(MessageType.GET_WXNO);
        target.setCreateTime(new Date());

        //客服
        IMUser service = imUserDao.getByName(friend.getServiceId());
        //微信
        IMUser wechat = imUserDao.getByName(friend.getWechatId());
        //管理员
        IMUser admin = imUserDao.getAdminByAppletId(service.getAppletId());
        //消息组
        List<IMMessage> messages = new ArrayList<IMMessage>();

        IMMessageBody body = new IMMessageBody();
        body.setToAccount(wechat.getName());
        body.setMessages(messages);

        Map<String, String> content = new HashMap<String, String>();
        content.put("Text", ObjectUtils.serialize(target));
        IMMessage imMessage = new IMMessage();
        imMessage.setType(IMMessageType.TEXT);
        imMessage.setContent(content);
        messages.add(imMessage);
        //转发给微信
        IMUtils.sendMsg(admin.getSig(), admin.getAppId(), admin.getName(), body);
    }

    /**
     * 金额消息回调
     */
    @Override
    @Transactional(readOnly = false)
    public void amountCallback(Message message){
        Assert.notNull(message, "回调消息不能为空");

        message.setSendByService(false);
        message.setCreateTime(new Date());

        WechatFriend friend = wechatFriendDao.getByRemarkname(message.getRemarkname());

        if(!StringUtils.isBlank(friend.getServiceId())){
            //设置销售
            org.wah.cloned.core.service.entity.Service servicer = serviceDao.getById(friend.getServiceId());
            message.setService(servicer);
            //旧版设计属性
            message.setFriend(friend);
            message.setRemarkname(friend.getRemarkname());
            message.setHeadImgUrl(friend.getHeadImgUrl());
            message.setNickname(friend.getNickname());
            message.setText((MessageType.RED_PACKET.equals(message.getType()) ? "已被收取红包" : "已被收取转账") + message.getText() + "元");
            message.setWechatId(friend.getWechatId());

            //客服
            IMUser service = imUserDao.getByName(friend.getServiceId());
            //微信
            IMUser wechat = imUserDao.getByName(friend.getWechatId());
            //管理员
            IMUser admin = imUserDao.getAdminByAppletId(service.getAppletId());
            //消息组
            List<IMMessage> messages = new ArrayList<IMMessage>();

            IMMessageBody body = new IMMessageBody();
            body.setFromAccount(wechat.getName());
            body.setToAccount(service.getName());
            body.setMessages(messages);

            Map<String, String> content = new HashMap<String, String>();
            content.put("Text", ObjectUtils.serialize(message));
            IMMessage imMessage = new IMMessage();
            imMessage.setType(IMMessageType.TEXT);
            imMessage.setContent(content);
            messages.add(imMessage);
            //转发
            IMUtils.sendMsg(admin.getSig(), admin.getAppId(), admin.getName(), body);
        }
        //保存消息
        messageDao.saveOrUpdate(message);
    }

    /**
     * 获取微信号消息回调
     */
    @Override
    @Transactional(readOnly = false)
    public void getWxnoCallback(Message message){
        Assert.notNull(message, "回调消息不能为空");

        if(MessageType.GET_WXNO.equals(message.getType())){
            WechatFriend friend = wechatFriendDao.getByRemarkname(message.getRemarkname());
            friend.setWxno(message.getText());
            wechatFriendDao.saveOrUpdate(friend);
        }
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

    /**
     * 好友验证通过消息通知
     */
    @Override
    @Transactional(readOnly = false)
    public void friendAdded(WechatFriend friend){
        Assert.notNull(friend, "微信好友信息不能为空");

        //消息
        Message target = new Message();
        target.setWechatId(friend.getWechatId());
        target.setFriend(friend);
        target.setText(friend.getNickname() + "已加您为好友。");
        target.setType(MessageType.NEW_FRIEND);
        target.setSendByService(false);
        target.setCreateTime(new Date());

        if(!StringUtils.isBlank(friend.getServiceId())){
            //设置销售
            org.wah.cloned.core.service.entity.Service servicer = serviceDao.getById(friend.getServiceId());
            target.setService(servicer);
            //旧版设计属性
            target.setRemarkname(friend.getRemarkname());
            target.setHeadImgUrl(friend.getHeadImgUrl());
            target.setNickname(friend.getNickname());
            //客服
            IMUser service = imUserDao.getByName(friend.getServiceId());
            //微信
            IMUser wechat = imUserDao.getByName(friend.getWechatId());
            //管理员
            IMUser admin = imUserDao.getAdminByAppletId(service.getAppletId());
            //消息组
            List<IMMessage> messages = new ArrayList<IMMessage>();
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

            IMUtils.sendMsg(admin.getSig(), admin.getAppId(), admin.getName(), body);
        }
        //保存消息
        messageDao.saveOrUpdate(target);
    }
}
