package org.wah.cloned.rongcloud.user.service;

import org.wah.cloned.rongcloud.user.entity.RongCloudUser;

public interface RongCloudUserService{

    /**
     * 保存
     */
    void save(RongCloudUser user);

    /**
     * 更新
     */
    void update(RongCloudUser user);

    /**
     * 根据用户ID查询
     */
    RongCloudUser getByUserId(String userId);

    /**
     * 注册客服用户
     */
    RongCloudUser registerService(String username, String password, String wechatId);
}
