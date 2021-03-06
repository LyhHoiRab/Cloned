package org.wah.cloned.core.wechat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.cloned.core.wechat.consts.AppStatus;
import org.wah.cloned.core.wechat.dao.WechatDao;
import org.wah.cloned.core.wechat.entity.Wechat;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class WechatServiceImpl implements WechatService{

    @Autowired
    private WechatDao wechatDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Wechat wechat){
        Assert.notNull(wechat, "微信信息不能为空");
        Assert.hasText(wechat.getWxno(), "微信号不能为空");
        Assert.hasText(wechat.getOrganizationId(), "企业ID不能为空");

        wechatDao.saveOrUpdate(wechat);
    }

    /**
     * 更新
     */
    @Override
    @Transactional(readOnly = false)
    public void update(Wechat wechat){
        Assert.notNull(wechat, "微信信息不能为空");
        Assert.hasText(wechat.getId(), "微信ID不能为空");

        wechatDao.saveOrUpdate(wechat);
    }

    /**
     * 根据ID查询
     */
    @Override
    public Wechat getById(String id){
        Assert.hasText(id, "微信ID不能为空");

        return wechatDao.getById(id);
    }

    /**
     * 查询
     */
    @Override
    public List<Wechat> find(String organizationId, String wxno){
        return wechatDao.find(organizationId, wxno);
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Wechat> page(PageRequest pageRequest, String organizationId, String wxno){
        Assert.notNull(pageRequest, "分页信息不能为空");

        return wechatDao.page(pageRequest, organizationId, wxno);
    }

    /**
     * 更新App状态
     */
    @Override
    @Transactional(readOnly = false)
    public void updateAppStatusByWxno(String wxno, AppStatus status){
        Assert.hasText(wxno, "微信号不能为空");
        Assert.notNull(status, "App状态不能为空");

        //查询微信
        Wechat wechat = wechatDao.getByWxno(wxno);
        //更新状态
        wechat.setAppStatus(status);
        wechatDao.saveOrUpdate(wechat);
    }

    /**
     * 检查过时App
     */
    @Override
    @Transactional(readOnly = false)
    public void updateAppStatusByTimeout(){
        wechatDao.updateAppStatusByTimeout();
    }
}
