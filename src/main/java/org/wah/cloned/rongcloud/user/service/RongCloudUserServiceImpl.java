package org.wah.cloned.rongcloud.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.cloned.core.account.dao.AccountDao;
import org.wah.cloned.core.account.dao.UserDao;
import org.wah.cloned.core.wechat.dao.WechatDao;
import org.wah.cloned.core.wechat.entity.Wechat;
import org.wah.cloned.rongcloud.commons.security.consts.RoleName;
import org.wah.cloned.rongcloud.user.dao.RongCloudUserDao;
import org.wah.cloned.rongcloud.user.entity.RongCloudUser;
import org.wah.doraemon.entity.Account;
import org.wah.doraemon.entity.User;
import org.wah.doraemon.security.exception.AccountNotFoundException;
import org.wah.doraemon.security.exception.ResourceNotFoundException;
import org.wah.doraemon.security.exception.ServiceException;
import org.wah.doraemon.security.exception.UserNotFoundException;

@Service
@Transactional(readOnly = true)
public class RongCloudUserServiceImpl implements RongCloudUserService{

    private Logger logger = LoggerFactory.getLogger(RongCloudUserServiceImpl.class);

    @Autowired
    private RongCloudUserDao rongCloudUserDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private WechatDao wechatDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public void save(RongCloudUser user){
        try{
            Assert.notNull(user, "融云用户信息不能为空");
            Assert.hasText(user.getUserId(), "用户ID不能为空");
            Assert.hasText(user.getPortraitUri(), "用户头像不能为空");
            Assert.hasText(user.getName(), "用户名不能为空");

            rongCloudUserDao.saveOrUpdate(user);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 更新
     */
    @Override
    @Transactional(readOnly = false)
    public void update(RongCloudUser user){
        try{
            Assert.notNull(user, "融云用户信息不能为空");
            Assert.hasText(user.getId(), "融云用户ID不能为空");

            rongCloudUserDao.saveOrUpdate(user);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据用户ID查询
     */
    @Override
    public RongCloudUser getByUserId(String userId){
        try{
            Assert.hasText(userId, "用户ID不能为空");

            return rongCloudUserDao.getByUserId(userId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 注册客服用户
     */
    @Override
    @Transactional(readOnly = false)
    public RongCloudUser registerService(String username, String password, String wechatId){
        try{
            Assert.hasText(username, "账户登录名称不能为空");
            Assert.hasText(password, "账户登录密码不能为空");

            Account account = accountDao.getByUsernameAndPassword(username, password);
            if(account == null){
                throw new AccountNotFoundException("账户[{0}]密码不正确", username);
            }

            User user = userDao.getByAccountId(account.getId());
            if(user == null){
                throw new UserNotFoundException("未获用户信息[{0}]", account.getId());
            }

            Wechat wechat = wechatDao.getById(wechatId);
            if(wechat == null){
                throw new ResourceNotFoundException("未获资源[{0}:{1}]", Wechat.class, wechatId);
            }

            RongCloudUser rongCloudUser = new RongCloudUser();
            rongCloudUser.setUserId(account.getId());
            rongCloudUser.setName(user.getNickname());
            rongCloudUser.setPortraitUri(user.getHeadImgUrl());
            rongCloudUser.setRoleName(RoleName.SERVICE);
            rongCloudUser.setWechatId(wechatId);
            rongCloudUserDao.saveOrUpdate(rongCloudUser);

            return rongCloudUser;
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
