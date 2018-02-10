package org.wah.cloned.core.account.dao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.wah.cloned.core.account.dao.mapper.UserMapper;
import org.wah.doraemon.entity.User;
import org.wah.doraemon.mybatis.Criteria;
import org.wah.doraemon.mybatis.Restrictions;
import org.wah.doraemon.security.exception.DataAccessException;
import org.wah.doraemon.utils.EntityUtils;
import org.wah.doraemon.utils.IDGenerator;

import java.util.Date;
import java.util.List;

@Repository
public class UserDao{

    private Logger logger = LoggerFactory.getLogger(UserDao.class);

    @Autowired
    private UserMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(User user){
        try{
            Assert.notNull(user, "用户信息不能为空");

            if(StringUtils.isBlank(user.getId())){
                Assert.hasText(user.getAccountId(), "账户ID不能为空");
                Assert.hasText(user.getNickname(), "用户昵称不能为空");
                Assert.hasText(user.getHeadImgUrl(), "用户头像不能为空");

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
     * 根据ID查询
     */
    public User getById(String id){
        try{
            Assert.hasText(id, "用户ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("u.id", id));
            criteria.and(Restrictions.eq("a.isDelete", false));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据账户ID查询
     */
    public User getByAccountId(String accountId){
        try{
            Assert.hasText(accountId, "用户账户ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("u.accountId", accountId));
            criteria.and(Restrictions.eq("a.isDelete", false));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据微信ID查询为客服的用户信息
     */
    public List<User> findIsServiceByWechatId(String wechatId){
        try{
            Assert.hasText(wechatId, "微信ID不能为空");

            return mapper.findIsServiceByWechatId(wechatId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据微信ID查询不为客服的用户信息
     */
    public List<User> findIsNotServiceByWechatId(String wechatId){
        try{
            Assert.hasText(wechatId, "微信ID不能为空");

            List<User> isService = mapper.findIsServiceByWechatId(wechatId);
            List<String> ids = EntityUtils.getIds(isService);

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("w.id", wechatId));

            if(ids != null && !ids.isEmpty()){
                criteria.and(Restrictions.notIn("u.id", ids));
            }

            return mapper.findIsNotServiceByWechatId(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
