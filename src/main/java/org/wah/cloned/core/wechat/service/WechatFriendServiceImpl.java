package org.wah.cloned.core.wechat.service;

import io.github.biezhi.wechat.api.model.Account;
import io.github.biezhi.wechat.utils.WeChatUtils;
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
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;
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

        friend.setRemarkname(IDGenerator.uuid32());
        wechatFriendDao.saveOrUpdate(friend);
    }

    /**
     * 更新
     */
    @Override
    @Transactional(readOnly = false)
    public void update(WechatFriend friend){
        Assert.notNull(friend, "微信好友信息不能为空");
        Assert.hasText(friend.getId(), "微信好友ID不能为空");

        wechatFriendDao.saveOrUpdate(friend);
    }

    /**
     * 根据ID查询
     */
    @Override
    public WechatFriend getById(String id){
        Assert.hasText(id, "微信好友ID不能为空");

        return wechatFriendDao.getById(id);
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
            friend.setNickname(WeChatUtils.emojiParse(account.getNickName()));
            friend.setSex(Sex.getById(account.getSex()));
            friend.setHeadImgUrl(bot.getWechatApi().downHeadImg(fromUserName));
            friend.setWechatId(bot.getWechatId());
            friend.setServiceId(serviceId);
            friend.setRemarkname(IDGenerator.uuid32());
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
            friend.setNickname(WeChatUtils.emojiParse(account.getNickName()));
            friend.setSex(Sex.getById(account.getSex()));
            friend.setHeadImgUrl(bot.getWechatApi().downHeadImg(fromUserName));
            friend.setWechatId(bot.getWechatId());
            friend.setServiceId(serviceId);
            friend.setRemarkname(account.getRemarkName());
            wechatFriendDao.saveOrUpdate(friend);
        }

        return friend;
    }

    /**
     * 分页查询
     */
    @Override
    public Page<WechatFriend> page(PageRequest pageRequest, String organizationId, String wechatId, String wxno, String serviceName, String nickname, String remarkname, Sex sex){
        return wechatFriendDao.page(pageRequest, organizationId, wechatId, wxno, serviceName, nickname, remarkname, sex);
    }
}
