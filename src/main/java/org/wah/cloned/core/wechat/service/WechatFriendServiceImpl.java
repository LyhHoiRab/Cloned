package org.wah.cloned.core.wechat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.cloned.core.wechat.dao.WechatFriendDao;
import org.wah.cloned.core.wechat.entity.WechatFriend;
import org.wah.doraemon.consts.Sex;

@Service
@Transactional(readOnly = true)
public class WechatFriendServiceImpl implements WechatFriendService{

    @Autowired
    private WechatFriendDao wechatFriendDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public WechatFriend save(String wechatId, String nickname, Sex sex){
        Assert.hasText(wechatId, "微信ID不能为空");
        Assert.hasText(nickname, "微信好友昵称不能为空");

        WechatFriend friend = new WechatFriend();
        friend.setWechatId(wechatId);
        friend.setNickname(nickname);
        friend.setSex(sex);
        wechatFriendDao.saveOrUpdate(friend);

        return friend;
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
}
