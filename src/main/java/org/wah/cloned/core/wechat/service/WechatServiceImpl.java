package org.wah.cloned.core.wechat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.cloned.core.wechat.dao.WechatDao;
import org.wah.cloned.core.wechat.entity.Wechat;
import org.wah.doraemon.security.exception.ServiceException;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;

@Service
@Transactional(readOnly = true)
public class WechatServiceImpl implements WechatService{

    private Logger logger = LoggerFactory.getLogger(WechatServiceImpl.class);

    @Autowired
    private WechatDao wechatDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Wechat wechat){
        try{
            Assert.notNull(wechat, "微信信息不能为空");
            Assert.hasText(wechat.getWxno(), "微信号不能为空");
            Assert.hasText(wechat.getOrganizationId(), "企业ID不能为空");

            wechatDao.saveOrUpdate(wechat);
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
    public void update(Wechat wechat){
        try{
            Assert.notNull(wechat, "微信信息不能为空");
            Assert.hasText(wechat.getId(), "微信ID不能为空");

            wechatDao.saveOrUpdate(wechat);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @Override
    public Wechat getById(String id){
        try{
            Assert.hasText(id, "微信ID不能为空");

            return wechatDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Wechat> page(PageRequest pageRequest, String organizationId, String wxno){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return wechatDao.page(pageRequest, organizationId, wxno);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}