package org.wah.cloned.rongcloud.auth.service;

import org.wah.cloned.rongcloud.token.entity.RongCloudToken;

public interface RongCloudLoginService{

    /**
     * 登录
     */
    RongCloudToken login(String keyword, String password);
}
