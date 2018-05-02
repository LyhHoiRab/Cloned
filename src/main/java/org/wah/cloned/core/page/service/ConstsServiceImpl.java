package org.wah.cloned.core.page.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wah.cloned.core.wechat.consts.AppStatus;
import org.wah.cloned.core.wechat.consts.MessageType;
import org.wah.cloned.core.wechat.consts.WechatStatus;
import org.wah.doraemon.consts.Sex;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ConstsServiceImpl implements ConstsService{

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

    @Override
    public Map<Object, Object> sex(){
        Map<Object, Object> result = new HashMap<Object, Object>();

        for(Sex sex : Sex.values()){
            result.put(sex.getId(), sex.getDescription());
        }

        return result;
    }

    @Override
    public Map<Object, Object> messageType(){
        Map<Object, Object> result = new HashMap<Object, Object>();

        for(MessageType type : MessageType.values()){
            result.put(type.getId(), type.getDescription());
        }

        return result;
    }
}
