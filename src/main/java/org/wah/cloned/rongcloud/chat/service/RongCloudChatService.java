package org.wah.cloned.rongcloud.chat.service;

import org.wah.cloned.rongcloud.token.entity.RongCloudToken;

public interface RongCloudChatService{

    /**
     * 查询登录用户Token
     */
    RongCloudToken getToken(String accountId);
}
