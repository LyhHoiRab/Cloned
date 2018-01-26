package org.wah.cloned.rongcloud.message.service;

import org.wah.cloned.rongcloud.message.entity.RongCloudMessage;

public interface RongCloudMessageService{

    /**
     * 保存
     */
    void save(RongCloudMessage message);

    /**
     * 更新
     */
    void update(RongCloudMessage message);
}
