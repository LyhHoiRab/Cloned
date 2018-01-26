package org.wah.cloned.rongcloud.message.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.cloned.rongcloud.message.dao.RongCloudMessageDao;
import org.wah.cloned.rongcloud.message.entity.RongCloudMessage;
import org.wah.doraemon.security.exception.ServiceException;

@Service
@Transactional(readOnly = true)
public class RongCloudMessageServiceImpl implements RongCloudMessageService{

    private Logger logger = LoggerFactory.getLogger(RongCloudMessageServiceImpl.class);

    @Autowired
    private RongCloudMessageDao rongCloudMessageDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public void save(RongCloudMessage message){
        try{
            Assert.notNull(message, "融云消息信息不能为空");
            Assert.hasText(message.getFromUserId(), "融云消息发送人ID不能为空");
            Assert.hasText(message.getToUserId(), "融云消息接收人ID不能为空");
            Assert.notNull(message.getContent(), "融云消息内容不能为空");
            Assert.notNull(message.getObjectName(), "融云消息类型不能为空");
            Assert.notNull(message.getSendTime(), "融云消息发送时间不能为空");
            Assert.notNull(message.getReceiveTime(), "融云消息接收时间不能为空");

            rongCloudMessageDao.saveOrUpdate(message);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 更新
     */
    @Override
    @Transactional(readOnly = false)
    public void update(RongCloudMessage message){
        try{
            Assert.notNull(message, "融云消息信息不能为空");
            Assert.hasText(message.getId(), "融云消息ID不能为空");

            rongCloudMessageDao.saveOrUpdate(message);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
