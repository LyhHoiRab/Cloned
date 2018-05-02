package org.wah.cloned.core.wechat.service;

import org.wah.cloned.bot.entity.WechatBot;
import org.wah.cloned.core.wechat.entity.WechatFriend;
import org.wah.doraemon.consts.Sex;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;

public interface WechatFriendService{

    /**
     * 保存
     */
    void save(WechatFriend friend);

    /**
     * 更新
     */
    void update(WechatFriend friend);

    /**
     * 根据ID查询
     */
    WechatFriend getById(String id);

    /**
     * 根据微信ID和备注名查询
     */
    WechatFriend getByWechatIdAndRemarkname(String wechatId, String remarkname);

    /**
     * 分配客服
     */
    WechatFriend allot(WechatBot bot, String fromUserName);

    /**
     * 分页查询
     */
    Page<WechatFriend> page(PageRequest pageRequest, String organizationId, String wechatId, String wxno, String serviceName, String nickname, String remarkname, Sex sex);
}
