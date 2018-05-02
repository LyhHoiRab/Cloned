package org.wah.cloned.core.wechat.dao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.wah.cloned.core.wechat.dao.mapper.MessageMapper;
import org.wah.cloned.core.wechat.entity.Message;
import org.wah.doraemon.mybatis.Criteria;
import org.wah.doraemon.mybatis.Restrictions;
import org.wah.doraemon.security.exception.DataAccessException;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;
import org.wah.doraemon.utils.IDGenerator;

import java.util.Date;
import java.util.List;

@Repository
public class MessageDao{

    private Logger logger = LoggerFactory.getLogger(MessageDao.class);

    @Autowired
    private MessageMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Message message){
        try{
            Assert.notNull(message, "消息不能为空");

            if(StringUtils.isBlank(message.getId())){
                Assert.hasText(message.getWechatId(), "微信ID不能为空");
                Assert.notNull(message.getFriend(), "微信好友信息不能为空");
                Assert.hasText(message.getFriend().getRemarkname(), "微信好友备注名称不能为空");
                Assert.notNull(message.getType(), "微信消息类型不能为空");

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
     * 分页
     */
    public Page<Message> page(PageRequest pageRequest, String remarkname, String serviceName, String nickname){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");


            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("m.createTime"));
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            if(!StringUtils.isBlank(remarkname)){
                criteria.and(Restrictions.eq("f.remarkname", remarkname));
            }
            if(!StringUtils.isBlank(serviceName)){
                criteria.and(Restrictions.like("s.name", serviceName));
            }
            if(!StringUtils.isBlank(nickname)){
                criteria.and(Restrictions.like("f.nickname", nickname));
            }

            List<Message> list = mapper.findByParams(criteria);
            Long count = mapper.countByParams(criteria);

            return new Page<Message>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
