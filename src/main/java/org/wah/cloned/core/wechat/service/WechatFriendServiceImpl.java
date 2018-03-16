package org.wah.cloned.core.wechat.service;

import io.github.biezhi.wechat.api.model.Account;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.cloned.bot.entity.WechatBot;
import org.wah.cloned.core.service.service.AllocationService;
import org.wah.cloned.core.wechat.dao.WechatFriendDao;
import org.wah.cloned.core.wechat.entity.WechatFriend;
import org.wah.doraemon.consts.Sex;
import org.wah.doraemon.utils.IDGenerator;

@Service
@Transactional(readOnly = true)
public class WechatFriendServiceImpl implements WechatFriendService{

    @Autowired
    private AllocationService allocationService;

    @Autowired
    private WechatFriendDao wechatFriendDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public void save(WechatFriend friend){
        Assert.hasText(friend.getWechatId(), "微信ID不能为空");
        Assert.hasText(friend.getNickname(), "微信好友昵称不能为空");

        friend.setRemarkname(IDGenerator.uuid16());
        wechatFriendDao.saveOrUpdate(friend);
    }

    /**
     * 根据微信ID和备注名查询
     */
    @Override
    public WechatFriend getByWechatIdAndRemarkname(String wechatId, String remarkname){
        Assert.hasText(wechatId, "微信ID不能为空");
        Assert.hasText(remarkname, "微信好友备注名称不能为空");

        return wechatFriendDao.getByWechatIdAndRemarkname(wechatId, remarkname);
    }

    /**
     * 分配客服
     */
    @Override
    @Transactional(readOnly = false)
    public WechatFriend allot(WechatBot bot, String fromUserName){
        //查询消息好友ID
        Account account = bot.getWechatApi().getAccountById(fromUserName);

        if(StringUtils.isBlank(account.getRemarkName())){
            //分配客服
            String serviceId = allocationService.getServiceByPool(bot.getWechatId());
            //创建好友
            WechatFriend friend = new WechatFriend();
            friend.setNickname(account.getNickName());
            friend.setSex(Sex.getById(account.getSex()));
            friend.setHeadImgUrl(bot.getSession().getUrl() + account.getHeadImgUrl());
            friend.setWechatId(bot.getWechatId());
            friend.setServiceId(serviceId);
            wechatFriendDao.saveOrUpdate(friend);
            //备注好友
            bot.getWechatApi().remark(fromUserName, friend.getRemarkname());
            account.setRemarkName(friend.getRemarkname());
        }
        //查询好友
        WechatFriend friend = wechatFriendDao.getByWechatIdAndRemarkname(bot.getWechatId(), account.getRemarkName());
        if(friend == null){
            //分配客服
            String serviceId = allocationService.getServiceByPool(bot.getWechatId());
            //创建好友
            friend = new WechatFriend();
            friend.setNickname(account.getNickName());
            friend.setSex(Sex.getById(account.getSex()));
            friend.setHeadImgUrl(bot.getSession().getUrl() + account.getHeadImgUrl());
            friend.setWechatId(bot.getWechatId());
            friend.setServiceId(serviceId);
            wechatFriendDao.saveOrUpdate(friend);
        }

        return friend;
    }
}
