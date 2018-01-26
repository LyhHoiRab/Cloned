package org.wah.cloned.core.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.cloned.core.account.dao.AccountDao;
import org.wah.doraemon.entity.Account;
import org.wah.doraemon.security.exception.ServiceException;

@Service
@Transactional(readOnly = true)
public class RegisterServiceImpl implements RegisterService{

    private Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Autowired
    private AccountDao accountDao;

    /**
     * 注册
     */
    @Override
    @Transactional(readOnly = false)
    public Account register(Account account){
        try{
            Assert.notNull(account, "注册的账户信息不能为空");
            Assert.hasText(account.getUsername(), "注册的账户登录名不能为空");
            Assert.hasText(account.getPassword(), "注册的账户密码不能为空");

            if(accountDao.exists(account.getUsername())){
                throw new ServiceException("账号[{0}]已注册", account.getUsername());
            }

            accountDao.saveOrUpdate(account);

            return account;
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
