package org.wah.cloned.core.service.dao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.wah.cloned.core.service.dao.mapper.ServiceMapper;
import org.wah.cloned.core.service.entity.Service;
import org.wah.doraemon.mybatis.Criteria;
import org.wah.doraemon.mybatis.Restrictions;
import org.wah.doraemon.security.exception.DataAccessException;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;
import org.wah.doraemon.utils.IDGenerator;

import java.util.Date;
import java.util.List;

@Repository
public class ServiceDao{

    private Logger logger = LoggerFactory.getLogger(ServiceDao.class);

    @Autowired
    private ServiceMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Service service){
        try{
            Assert.notNull(service, "客服信息不能为空");

            if(StringUtils.isBlank(service.getId())){
                Assert.hasText(service.getWechatId(), "客服关联微信ID不能为空");
                Assert.hasText(service.getName(), "客服名称不能为空");
                Assert.hasText(service.getUsername(), "客服登录名称不能为空");
                Assert.hasText(service.getPassword(), "客服登录密码不能为空");

                service.setId(IDGenerator.uuid32());
                service.setCreateTime(new Date());
                mapper.save(service);
            }else{
                service.setUpdateTime(new Date());
                mapper.update(service);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public Service getById(String id){
        try{
            Assert.hasText(id, "客服ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据微信ID查询
     */
    public List<Service> findByWechatId(String wechatId){
        try{
            Assert.hasText(wechatId, "微信ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("w.wechatId", wechatId));

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<Service> page(PageRequest pageRequest, String organizationId, String wxno, String wechatId, String name){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
            criteria.sort(Restrictions.asc("s.createTime"));

            if(!StringUtils.isBlank(name)){
                criteria.and(Restrictions.like("s.name", name));
            }
            if(!StringUtils.isBlank(organizationId)){
                criteria.and(Restrictions.eq("w.organizationId", organizationId));
            }
            if(!StringUtils.isBlank(wxno)){
                criteria.and(Restrictions.like("w.wxno", wxno));
            }
            if(!StringUtils.isBlank(wechatId)){
                criteria.and(Restrictions.eq("w.wechatId", wechatId));
            }

            List<Service> list = mapper.findByParams(criteria);
            Long count = mapper.countByParams(criteria);

            return new Page<Service>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
