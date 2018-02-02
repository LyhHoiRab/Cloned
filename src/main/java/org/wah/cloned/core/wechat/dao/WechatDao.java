package org.wah.cloned.core.wechat.dao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.wah.cloned.core.wechat.dao.mapper.WechatMapper;
import org.wah.cloned.core.wechat.entity.Wechat;
import org.wah.doraemon.mybatis.Criteria;
import org.wah.doraemon.mybatis.Restrictions;
import org.wah.doraemon.security.exception.DataAccessException;
import org.wah.doraemon.utils.IDGenerator;

import java.util.Date;

@Repository
public class WechatDao{

    private Logger logger = LoggerFactory.getLogger(WechatDao.class);

    @Autowired
    private WechatMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Wechat wechat){
        try{
            Assert.notNull(wechat, "微信信息不能为空");

            if(StringUtils.isBlank(wechat.getId())){
                wechat.setId(IDGenerator.uuid32());
                wechat.setCreateTime(new Date());
                mapper.save(wechat);
            }else{
                wechat.setUpdateTime(new Date());
                mapper.update(wechat);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public Wechat getById(String id){
        try{
            Assert.hasText(id, "微信ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
