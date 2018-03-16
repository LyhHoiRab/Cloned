package org.wah.cloned.core.service.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.cloned.commons.security.consts.CacheParamName;
import org.wah.cloned.core.service.dao.AllocationDao;
import org.wah.cloned.core.service.dao.ServiceDao;
import org.wah.cloned.core.service.entity.Allocation;
import org.wah.doraemon.utils.RedisUtils;
import redis.clients.jedis.ShardedJedisPool;

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
    private ShardedJedisPool shardedJedisPool;

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
        List<String> pool = RedisUtils.lall(shardedJedisPool.getResource(), CacheParamName.SERVICE_ALLOCATION + wechatId, String.class);
        //清空
        if(pool == null){
            pool = new ArrayList<String>();
        }else{
            pool.clear();
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
                    pool.add(allocation.getService().getId());
                    services.put(allocation.getService().getId(), service - 1);

                    isContinue = false;
                }
            }
        }

        //缓存
        RedisUtils.rpush(shardedJedisPool.getResource(), CacheParamName.SERVICE_ALLOCATION + wechatId, pool);
    }

    /**
     * 在分配池中查询客服
     */
    @Override
    public String getServiceByPool(String wechatId){
        //查询
        String serviceId = RedisUtils.lpop(shardedJedisPool.getResource(), CacheParamName.SERVICE_ALLOCATION + wechatId, String.class);

        if(StringUtils.isBlank(serviceId)){
            List<Allocation> allocations = allocationDao.findAllotByWechatId(wechatId);
            //分配池
            List<String> pool = new ArrayList<String>();

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
                        pool.add(allocation.getService().getId());
                        services.put(allocation.getService().getId(), service - 1);

                        isContinue = false;
                    }
                }
            }

            //缓存
            RedisUtils.lpush(shardedJedisPool.getResource(), CacheParamName.SERVICE_ALLOCATION + wechatId, pool);
            //查询
            serviceId = RedisUtils.lpop(shardedJedisPool.getResource(), CacheParamName.SERVICE_ALLOCATION + wechatId, String.class);
        }

        return serviceId;
    }
}
