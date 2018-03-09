package org.wah.cloned.core.wechat.service;

import org.wah.cloned.core.wechat.entity.WechatFriend;
import org.wah.doraemon.consts.Sex;

public interface WechatFriendService{

    /**
     * 保存
     */
    void save(WechatFriend friend);

    /**
     * 根据微信ID和备注名查询
     */
    WechatFriend getByWechatIdAndRemarkname(String wechatId, String remarkname);
}
