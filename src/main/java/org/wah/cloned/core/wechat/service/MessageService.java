package org.wah.cloned.core.wechat.service;

import org.wah.cloned.core.wechat.entity.Message;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;

public interface MessageService{

    /**
     * 分页查询
     */
    Page<Message> page(PageRequest pageRequest, String remarkname, String serviceName, String nickname);
}
