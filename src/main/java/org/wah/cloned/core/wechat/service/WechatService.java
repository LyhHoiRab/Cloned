package org.wah.cloned.core.wechat.service;

import org.wah.cloned.core.wechat.consts.AppStatus;
import org.wah.cloned.core.wechat.entity.Wechat;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;

import java.util.List;

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
     * 查询
     */
    List<Wechat> find(String organizationId, String wxno);

    /**
     * 分页查询
     */
    Page<Wechat> page(PageRequest pageRequest, String organizationId, String wxno);

    /**
     * 更新App状态
     */
    void updateAppStatusByWxno(String wxno, AppStatus status);

    /**
     * 检查过时App
     */
    void updateAppStatusByTimeout();
}
