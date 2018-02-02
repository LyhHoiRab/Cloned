package org.wah.cloned.rongcloud.user.dao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.wah.cloned.rongcloud.user.entity.Allocation;
import org.wah.doraemon.security.exception.DataAccessException;
import org.wah.doraemon.utils.IDGenerator;

import java.util.Date;
import java.util.List;

@Repository
public class AllocationDao{

    private Logger logger = LoggerFactory.getLogger(AllocationDao.class);

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Allocation allocation){
        try{
            Assert.notNull(allocation, "客服分配概率信息不能为空");

            if(StringUtils.isBlank(allocation.getId())){
                Assert.hasText(allocation.getRongCloudUserId(), "融云客服ID不能为空");
                Assert.notNull(allocation.getProbability(), "客服分配概率不能为空");

                allocation.setId(IDGenerator.uuid32());
                allocation.setCreateTime(new Date());
            }else{
                allocation.setUpdateTime(new Date());
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据微信ID查询
     */
    public List<Allocation> findByWechatId(String wechatId){
        try{
            Assert.hasText(wechatId, "微信ID不能为空");

            return null;
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
