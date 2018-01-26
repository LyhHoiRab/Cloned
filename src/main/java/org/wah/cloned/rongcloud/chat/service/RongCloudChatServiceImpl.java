package org.wah.cloned.rongcloud.chat.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wah.cloned.rongcloud.token.dao.RongCloudTokenDao;
import org.wah.cloned.rongcloud.token.entity.RongCloudToken;
import org.wah.doraemon.security.exception.NotLoginStateException;
import org.wah.doraemon.security.exception.ServiceException;

@Service
@Transactional(readOnly = true)
public class RongCloudChatServiceImpl implements RongCloudChatService{

    private Logger logger = LoggerFactory.getLogger(RongCloudChatServiceImpl.class);

    @Autowired
    private RongCloudTokenDao rongCloudTokenDao;

    /**
     * 查询登录用户Token
     */
    @Override
    public RongCloudToken getToken(String accountId){
        try{
            if(StringUtils.isBlank(accountId)){
                throw new NotLoginStateException("未登录用户");
            }

            return rongCloudTokenDao.getByAccountId(accountId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
