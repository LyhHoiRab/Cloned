package org.wah.cloned.core.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.cloned.core.service.dao.AllocationDao;
import org.wah.cloned.core.service.dao.ServiceDao;
import org.wah.cloned.core.service.entity.Allocation;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ServiceServiceImpl implements ServiceService{

    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private AllocationDao allocationDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public void save(org.wah.cloned.core.service.entity.Service service){
        Assert.notNull(service, "客服信息不能为空");
        //保存客服
        serviceDao.saveOrUpdate(service);
        //保存概率
        Allocation allocation = new Allocation();
        allocation.setService(service);
        allocation.setProbability(0D);
        allocation.setDefaultProbability(0D);
        allocation.setStep(1D);
        allocationDao.save(allocation);
    }

    /**
     * 更新
     */
    @Override
    @Transactional(readOnly = false)
    public void update(org.wah.cloned.core.service.entity.Service service){
        Assert.notNull(service, "客服信息不能为空");
        Assert.hasText(service.getId(), "客服ID不能为空");

        serviceDao.saveOrUpdate(service);
    }

    /**
     * 根据ID查询
     */
    @Override
    public org.wah.cloned.core.service.entity.Service getById(String id){
        Assert.hasText(id, "客服ID不能为空");

        return serviceDao.getById(id);
    }

    /**
     * 根据微信ID和账户ID查询
     */
    @Override
    public org.wah.cloned.core.service.entity.Service getByWechatIdAndAccountId(String wechatId, String accountId){
        Assert.hasText(wechatId, "客服关联微信ID不能为空");
        Assert.hasText(accountId, "客服关联账户ID不能为空");

        return serviceDao.getByWechatIdAndAccountId(wechatId, accountId);
    }

    /**
     * 根据微信ID查询
     */
    @Override
    public List<org.wah.cloned.core.service.entity.Service> findByWechatId(String wechatId){
        Assert.hasText(wechatId, "客服关联微信ID不能为空");

        return serviceDao.findByWechatId(wechatId);
    }

    /**
     * 根据账户ID查询
     */
    @Override
    public List<org.wah.cloned.core.service.entity.Service> findByAccountId(String accountId){
        Assert.hasText(accountId, "客服关联账户ID不能为空");

        return serviceDao.findByAccountId(accountId);
    }

    /**
     * 分页查询
     */
    @Override
    public Page<org.wah.cloned.core.service.entity.Service> page(PageRequest pageRequest, String wechatId, String accountId, String name){
        Assert.notNull(pageRequest, "分页信息不能为空");

        return serviceDao.page(pageRequest, wechatId, accountId, name);
    }
}
