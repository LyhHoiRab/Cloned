package org.wah.cloned.core.wechat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.cloned.core.wechat.dao.MessageDao;
import org.wah.cloned.core.wechat.entity.Message;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;

@Service
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageDao messageDao;

    /**
     * 分页查询
     */
    @Override
    public Page<Message> page(PageRequest pageRequest, String remarkname, String serviceName, String nickname){
        Assert.notNull(pageRequest, "分页信息不能为空");

        return messageDao.page(pageRequest, remarkname, serviceName, nickname);
    }
}
