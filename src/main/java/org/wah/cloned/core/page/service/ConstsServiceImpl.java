package org.wah.cloned.core.page.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wah.cloned.core.wechat.consts.AppStatus;
import org.wah.cloned.core.wechat.consts.WechatStatus;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ConstsServiceImpl implements ConstsService{

    /**
     * 微信状态常量
     */
    @Override
    public Map<Object, Object> wechatStatus(){
        Map<Object, Object> result = new HashMap<Object, Object>();

        for(WechatStatus status : WechatStatus.values()){
            result.put(status.getId(), status.getDescription());
        }

        return result;
    }

    @Override
    public Map<Object, Object> appStatus(){
        Map<Object, Object> result = new HashMap<Object, Object>();

        for(AppStatus status : AppStatus.values()){
            result.put(status.getId(), status.getDescription());
        }

        return result;
    }
}