package org.wah.cloned.rongcloud.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.cloned.commons.security.consts.RongCloudApp;
import org.wah.cloned.core.account.dao.AccountDao;
import org.wah.cloned.core.account.dao.UserDao;
import org.wah.cloned.rongcloud.commons.response.TokenResponse;
import org.wah.cloned.rongcloud.commons.utils.UserUtils;
import org.wah.cloned.rongcloud.token.dao.RongCloudTokenDao;
import org.wah.cloned.rongcloud.token.entity.RongCloudToken;
import org.wah.doraemon.entity.Account;
import org.wah.doraemon.entity.User;
import org.wah.doraemon.security.exception.AccountNotFoundException;
import org.wah.doraemon.security.exception.ServiceException;

import java.util.Date;

@Service
@Transactional(readOnly = true)
public class RongCloudLoginServiceImpl implements RongCloudLoginService{

    private Logger logger = LoggerFactory.getLogger(RongCloudLoginServiceImpl.class);

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private RongCloudTokenDao tokenDao;

    @Autowired
    private UserDao userDao;

    /**
     * 登录
     */
    @Override
    @Transactional(readOnly = false)
    public RongCloudToken login(String keyword, String password){
        try{
            Assert.hasText(keyword, "融云登录名不能为空");
            Assert.hasText(password, "融云登录密码不能为空");

            //校验账户
            Account account = accountDao.getByKeywordAndPassword(keyword, password);
            if(account == null){
                throw new AccountNotFoundException("登录名[{0}]密码不正确", keyword);
            }

            //查询用户信息
            User user = userDao.getByAccountId(account.getId());
            //查询Token
            RongCloudToken token = tokenDao.getByAccountId(account.getId());
            if(token == null){
                token = new RongCloudToken();
                token.setAccountId(account.getId());
                token.setUser(user);
            }
            //获取Token
            TokenResponse response = UserUtils.getToken(RongCloudApp.appKey, RongCloudApp.appSecret, account.getId(), user.getNickname(), user.getHeadImgUrl());
            token.setToken(response.getToken());
            token.setAppKey(RongCloudApp.appKey);
            token.setLoginTime(new Date());
            tokenDao.saveOrUpdate(token);

            return token;
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
