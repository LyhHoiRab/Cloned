package org.wah.cloned.core.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.cloned.core.account.dao.AccountDao;
import org.wah.doraemon.entity.Account;
import org.wah.doraemon.security.exception.AccountNotFoundException;

@Service
@Transactional(readOnly = true)
public class LoginServiceImpl implements LoginService{

    @Autowired
    private AccountDao accountDao;

    /**
     * 登录
     */
    @Override
    public String login(String username, String password){
        Assert.hasText(username, "登录名不能为空");
        Assert.hasText(password, "登录密码不能为空");

        Account account = accountDao.getByUsernameAndPassword(username, password);

        if(account == null){
            throw new AccountNotFoundException("账户[{0}]密码不正确", username);
        }

        return account.getId();
    }
}
