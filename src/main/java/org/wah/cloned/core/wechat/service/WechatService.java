package org.wah.cloned.core.wechat.service;

import org.wah.cloned.core.wechat.entity.Wechat;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;

public interface WechatService{

    /**
     * 保存
     */
    void save(Wechat wechat);

    /**
     * 更新
     */
    void update(Wechat wechat);

    /**
     * 根据ID查询
     */
    Wechat getById(String id);

    /**
     * 分页查询
     */
    Page<Wechat> page(PageRequest pageRequest, String organizationId, String wxno);

    /**
     * 微信登录机器人
     */
    void login(String wechatId);
}
