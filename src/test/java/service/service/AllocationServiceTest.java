package service.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wah.cloned.commons.security.consts.CacheParamName;
import org.wah.cloned.core.service.service.AllocationService;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
@ActiveProfiles(value = "test")
public class AllocationServiceTest{

    @Autowired
    private AllocationService allocationService;

    /**
     * 创建或更新客服分配池
     */
    @Test
    public void saveOrUpdatePool(){
        allocationService.saveOrUpdatePool("9fe59d3ac70843fbbf7379fb9b07696a");

        List<String> services = CacheParamName.allocations.get("9fe59d3ac70843fbbf7379fb9b07696a");
        for(String id : services){
            System.out.println(id);
        }
    }
}
