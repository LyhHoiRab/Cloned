package service.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wah.cloned.commons.security.consts.CacheParamName;
import org.wah.cloned.core.service.service.AllocationService;
import org.wah.doraemon.utils.RedisUtils;
import redis.clients.jedis.ShardedJedisPool;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:springbean.xml"})
@ActiveProfiles(value = "test")
public class AllocationServiceTest{

    @Autowired
    private AllocationService allocationService;

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    /**
     * 创建或更新客服分配池
     */
    @Test
    public void saveOrUpdatePool(){
        String wechatId = "bc4d7d2a5fb54f678b3e7f0201f36e30";

        allocationService.saveOrUpdatePool(wechatId);

        List<String> services = RedisUtils.lall(shardedJedisPool.getResource(), CacheParamName.SERVICE_ALLOCATION + wechatId, String.class);
        for(String id : services){
            System.out.println(id);
        }
    }
}
