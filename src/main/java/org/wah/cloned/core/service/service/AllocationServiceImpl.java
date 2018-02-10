package org.wah.cloned.core.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wah.cloned.core.service.dao.AllocationDao;
import org.wah.cloned.core.service.entity.Allocation;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AllocationServiceImpl implements AllocationService{

    @Autowired
    private AllocationDao allocationDao;

    /**
     * 根据条件查询
     */
    @Override
    public List<Allocation> find(String wechatId, String name){
        return allocationDao.find(wechatId, name);
    }
}
