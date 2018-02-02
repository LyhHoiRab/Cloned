package org.wah.cloned.core.account.dao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.wah.cloned.core.account.dao.mapper.AccountMapper;
import org.wah.doraemon.consts.AccountState;
import org.wah.doraemon.entity.Account;
import org.wah.doraemon.mybatis.Criteria;
import org.wah.doraemon.mybatis.Restrictions;
import org.wah.doraemon.security.exception.DataAccessException;
import org.wah.doraemon.utils.IDGenerator;

import java.util.Date;

@Repository
public class AccountDao{

    private Logger logger = LoggerFactory.getLogger(AccountDao.class);

    @Autowired
    private AccountMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Account account){
        try{
            Assert.notNull(account, "账户信息不能为空");

            if(StringUtils.isBlank(account.getId())){
                Assert.hasText(account.getPassword(), "账户密码不能为空");
                Assert.hasText(account.getUsername(), "账户登录名不能为空");

                account.setId(IDGenerator.uuid32());
                account.setState(AccountState.NORMAL);
                account.setIsDelete(false);
                account.setCreateTime(new Date());
                mapper.save(account);
            }else{
                account.setUpdateTime(new Date());
                mapper.update(account);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public Account getById(String id){
        try{
            Assert.hasText(id, "账户ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("id", id));
            criteria.and(Restrictions.eq("isDelete", false));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据账户登录名称和密码查询
     */
    public Account getByUsernameAndPassword(String username, String password){
        try{
            Assert.hasText(username, "账户登录名称不能为空");
            Assert.hasText(password, "账户登录密码不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("username", username));
            criteria.and(Restrictions.eq("password", password));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据账户登录关键字和密码查询
     */
    public Account getByKeywordAndPassword(String keyword, String password){
        try{
            Assert.hasText(keyword, "账户登录关键字不能为空");
            Assert.hasText(password, "账户登录密码不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("isDelete", false));
            criteria.and(Restrictions.eq("password", password));
            criteria.and(Restrictions.or(Restrictions.eq("username", keyword),
                                            Restrictions.eq("email", keyword),
                                            Restrictions.eq("phone", keyword)));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 判断账户是否注册
     */
    public Boolean exists(String keyword){
        try{
            Assert.hasText(keyword, "账户登录关键字不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("isDelete", false));
            criteria.and(Restrictions.or(Restrictions.eq("username", keyword),
                    Restrictions.eq("email", keyword),
                    Restrictions.eq("phone", keyword)));

            Long count = mapper.countByParams(criteria);

            return (count != null && count > 0);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
