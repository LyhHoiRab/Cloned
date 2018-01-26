package org.wah.cloned.rongcloud.message.dao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.wah.cloned.rongcloud.message.dao.mapper.RongCloudMessageMapper;
import org.wah.cloned.rongcloud.message.entity.RongCloudMessage;
import org.wah.doraemon.mybatis.Criteria;
import org.wah.doraemon.mybatis.Restrictions;
import org.wah.doraemon.security.exception.DataAccessException;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;
import org.wah.doraemon.utils.IDGenerator;

import java.util.Date;
import java.util.List;

@Repository
public class RongCloudMessageDao{

    private Logger logger = LoggerFactory.getLogger(RongCloudMessageDao.class);

    @Autowired
    private RongCloudMessageMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(RongCloudMessage message){
        try{
            Assert.notNull(message, "融云消息信息不能为空");

            if(StringUtils.isBlank(message.getId())){
                Assert.hasText(message.getFromUserId(), "融云消息发送人ID不能为空");
                Assert.hasText(message.getToUserId(), "融云消息接收人ID不能为空");
                Assert.notNull(message.getContent(), "融云消息内容不能为空");
                Assert.notNull(message.getObjectName(), "融云消息类型不能为空");
                Assert.notNull(message.getSendTime(), "融云消息发送时间不能为空");
                Assert.notNull(message.getReceiveTime(), "融云消息接收时间不能为空");

                message.setId(IDGenerator.uuid32());
                message.setCreateTime(new Date());
                mapper.save(message);
            }else{
                message.setUpdateTime(new Date());
                mapper.update(message);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据接收人ID查询
     */
    public Page<RongCloudMessage> pageByToUserId(PageRequest pageRequest, String toUserId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");
            Assert.hasText(toUserId, "融云消息接收人ID不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
            criteria.sort(Restrictions.asc("receiveTime"));
            criteria.and(Restrictions.eq("toUserId", toUserId));

            List<RongCloudMessage> list = mapper.findByParams(criteria);
            Long count = mapper.countByParams(criteria);

            return new Page<RongCloudMessage>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据发送人ID查询
     */
    public Page<RongCloudMessage> pageByFromUserId(PageRequest pageRequest, String fromUserId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");
            Assert.hasText(fromUserId, "融云消息发送人ID不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
            criteria.sort(Restrictions.asc("receiveTime"));
            criteria.and(Restrictions.eq("fromUserId", fromUserId));

            List<RongCloudMessage> list = mapper.findByParams(criteria);
            Long count = mapper.countByParams(criteria);

            return new Page<RongCloudMessage>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
