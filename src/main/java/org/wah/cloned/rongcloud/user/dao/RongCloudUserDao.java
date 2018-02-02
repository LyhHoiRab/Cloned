package org.wah.cloned.rongcloud.user.dao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.wah.cloned.rongcloud.user.dao.mapper.RongCloudUserMapper;
import org.wah.cloned.rongcloud.user.entity.RongCloudUser;
import org.wah.doraemon.mybatis.Criteria;
import org.wah.doraemon.mybatis.Restrictions;
import org.wah.doraemon.security.exception.DataAccessException;
import org.wah.doraemon.utils.IDGenerator;

import java.util.Date;

@Repository
public class RongCloudUserDao{

    private Logger logger = LoggerFactory.getLogger(RongCloudUser.class);

    @Autowired
    private RongCloudUserMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(RongCloudUser user){
        try{
            Assert.notNull(user, "融云用户信息不能为空");

            if(StringUtils.isBlank(user.getId())){
                Assert.hasText(user.getUserId(), "用户ID不能为空");
                Assert.hasText(user.getPortraitUri(), "用户头像不能为空");
                Assert.hasText(user.getName(), "用户名不能为空");
                Assert.notNull(user.getRoleName(), "用户角色不能为空");

                user.setId(IDGenerator.uuid32());
                user.setCreateTime(new Date());
                mapper.save(user);
            }else{
                user.setUpdateTime(new Date());
                mapper.update(user);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据用户ID查询
     */
    public RongCloudUser getByUserId(String userId){
        try{
            Assert.hasText(userId, "用户ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("userId", userId));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
