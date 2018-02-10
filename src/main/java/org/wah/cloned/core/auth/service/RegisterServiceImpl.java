package org.wah.cloned.core.auth.service;

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

    @Autowired
    private AccountDao accountDao;

    /**
     * 注册
     */
    @Override
    @Transactional(readOnly = false)
    public Account register(String username, String password, String organizationId){
        Assert.hasText(username, "注册的账户登录名不能为空");
        Assert.hasText(password, "注册的账户密码不能为空");
        Assert.hasText(organizationId, "注册的账户企业机构ID不能为空");

        if(accountDao.exists(username)){
            throw new ServiceException("账号[{0}]已注册", username);
        }

        //保存账户信息
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        accountDao.saveOrUpdate(account);

        //保存账户 - 企业机构关联信息
        accountDao.saveToOrganization(account.getId(), organizationId);

        return account;
    }
}
