package org.wah.cloned.rongcloud.token.dao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.wah.cloned.rongcloud.token.dao.mapper.RongCloudTokenMapper;
import org.wah.cloned.rongcloud.token.entity.RongCloudToken;
import org.wah.doraemon.mybatis.Criteria;
import org.wah.doraemon.mybatis.Restrictions;
import org.wah.doraemon.security.exception.DataAccessException;
import org.wah.doraemon.utils.IDGenerator;

import java.util.Date;

@Repository
public class RongCloudTokenDao{

    private Logger logger = LoggerFactory.getLogger(RongCloudTokenDao.class);

    @Autowired
    private RongCloudTokenMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(RongCloudToken token){
        try{
            Assert.notNull(token, "融云登录Token信息不能为空");
            Assert.hasText(token.getAccountId(), "融云登录Token账户ID不能为空");
            Assert.hasText(token.getAppKey(), "融云AppKey不能为空");

            if(StringUtils.isBlank(token.getId())){
                token.setId(IDGenerator.uuid32());
                token.setCreateTime(new Date());
                mapper.save(token);
            }else{
                token.setUpdateTime(new Date());
                mapper.update(token);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据账户ID查询
     */
    public RongCloudToken getByAccountId(String accountId){
        try{
            Assert.hasText(accountId, "融云登录Token账户ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("t.accountId", accountId));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
