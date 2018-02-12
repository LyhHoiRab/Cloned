package org.wah.cloned.core.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.cloned.commons.security.consts.CacheParamName;
import org.wah.cloned.core.service.dao.AllocationDao;
import org.wah.cloned.core.service.dao.ServiceDao;
import org.wah.cloned.core.service.entity.Allocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class AllocationServiceImpl implements AllocationService{

    @Autowired
    private AllocationDao allocationDao;

    @Autowired
    private ServiceDao serviceDao;

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

    /**
     * 创建或更新客服分配池
     */
    @Override
    public void saveOrUpdatePool(String wechatId){
        Assert.hasText(wechatId, "微信ID不能为空");

        List<Allocation> allocations = allocationDao.findAllotByWechatId(wechatId);
        //分配池
        Map<String, List<String>> pool = CacheParamName.allocations;
        List<String> list = pool.get(wechatId);
        //清空
        if(list == null){
            list = new ArrayList<String>();
            pool.put(wechatId, list);
        }else{
            list.clear();
        }
        //客服概率
        Map<String, Integer> services = new HashMap<String, Integer>();
        for(Allocation allocation : allocations){
            int service = new Double(allocation.getProbability() / allocation.getStep()).intValue();
            services.put(allocation.getService().getId(), service);
        }
        //flag
        boolean isContinue = false;
        //填充分配池
        while(!isContinue){
            isContinue = true;

            for(Allocation allocation : allocations){
                Integer service = services.get(allocation.getService().getId());
                if(service > 0){
                    list.add(allocation.getService().getId());
                    services.put(allocation.getService().getId(), service - 1);

                    isContinue = false;
                }
            }
        }
    }
}
