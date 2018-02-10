package org.wah.cloned.core.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.cloned.core.service.dao.AllocationDao;
import org.wah.cloned.core.service.entity.Allocation;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AllocationServiceImpl implements AllocationService{

    @Autowired
    private AllocationDao allocationDao;

    /**
     * 更新
     */
    @Override
    @Transactional(readOnly = false)
    public void update(List<Allocation> list){
        Assert.notEmpty(list, "概率列表不能为空");

        allocationDao.update(list);
    }

    /**
     * 设置默认概率
     */
    @Override
    @Transactional(readOnly = false)
    public void setDefaultProbability(String wechatId){
        Assert.hasText(wechatId, "微信ID不能为空");

        allocationDao.setDefaultProbability(wechatId);
    }

    /**
     * 根据条件查询
     */
    @Override
    public List<Allocation> find(String wechatId, String name){
        return allocationDao.find(wechatId, name);
    }
}
