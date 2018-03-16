package org.wah.cloned.core.wechat.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wah.cloned.core.wechat.service.WechatService;

@Component
public class UpdateAppStatusByTimeoutTask{

    @Autowired
    private WechatService wechatService;

    public void execute(){
        wechatService.updateAppStatusByTimeout();
    }
}
