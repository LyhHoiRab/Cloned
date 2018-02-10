package org.wah.cloned.core.service.dao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.wah.cloned.core.service.dao.mapper.AllocationMapper;
import org.wah.cloned.core.service.entity.Allocation;
import org.wah.doraemon.mybatis.Criteria;
import org.wah.doraemon.mybatis.Restrictions;
import org.wah.doraemon.security.exception.DataAccessException;
import org.wah.doraemon.utils.IDGenerator;

import java.util.Date;
import java.util.List;

@Repository
public class AllocationDao{

    private Logger logger = LoggerFactory.getLogger(AllocationDao.class);

    @Autowired
    private AllocationMapper mapper;

    /**
     * 保存
     */
    public void save(Allocation allocation){
        try{
            Assert.notNull(allocation, "客服分配概率不能为空");
            Assert.notNull(allocation.getStep(), "概率步长不能为空");
            Assert.notNull(allocation.getService(), "概率关联客服不能为空");
            Assert.hasText(allocation.getService().getId(), "客服ID不能为空");

            allocation.setId(IDGenerator.uuid32());
            allocation.setCreateTime(new Date());
            mapper.save(allocation);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 更新
     */
    public void update(List<Allocation> list){
        try{
            Assert.notEmpty(list, "客服分配概率列表不能为空");

            Date now = new Date();

            for(Allocation allocation : list){
                Assert.notNull(allocation, "客服分配概率不能为空");
                Assert.hasText(allocation.getId(), "概率ID不能为空");
                Assert.notNull(allocation.getStep(), "概率步长不能为空");
                Assert.notNull(allocation.getService(), "概率关联客服不能为空");
                Assert.hasText(allocation.getService().getId(), "客服ID不能为空");

                allocation.setUpdateTime(now);
            }

            mapper.update(list);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 设置默认概率
     */
    public void setDefaultProbability(String wechatId){
        try{
            Assert.hasText(wechatId, "微信ID不能为空");

            mapper.setDefaultProbability(wechatId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据微信ID查询
     */
    public List<Allocation> find(String wechatId, String name){
        try{
            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("s.createTime"));

            if(!StringUtils.isBlank(wechatId)){
                criteria.and(Restrictions.eq("s.wechatId", wechatId));
            }
            if(!StringUtils.isBlank(name)){
                criteria.and(Restrictions.like("s.name", name));
            }

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
