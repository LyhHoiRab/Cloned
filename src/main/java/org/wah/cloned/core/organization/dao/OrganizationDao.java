package org.wah.cloned.core.organization.dao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.wah.cloned.core.organization.dao.mapper.OrganizationMapper;
import org.wah.cloned.core.organization.entity.Organization;
import org.wah.doraemon.mybatis.Criteria;
import org.wah.doraemon.mybatis.Restrictions;
import org.wah.doraemon.security.exception.DataAccessException;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;
import org.wah.doraemon.utils.IDGenerator;

import java.util.Date;
import java.util.List;

@Repository
public class OrganizationDao{

    private Logger logger = LoggerFactory.getLogger(OrganizationDao.class);

    @Autowired
    private OrganizationMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Organization organization){
        try{
            Assert.notNull(organization, "企业机构信息不能为空");

            if(StringUtils.isBlank(organization.getId())){
                organization.setId(IDGenerator.uuid32());
                organization.setCreateTime(new Date());
                mapper.save(organization);
            }else{
                organization.setUpdateTime(new Date());
                mapper.update(organization);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public Organization getById(String id){
        try{
            Assert.hasText(id, "企业机构ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据条件查询
     */
    public List<Organization> find(String token, String name, String companyName){
        try{
            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("createTime"));

            if(!StringUtils.isBlank(token)){
                criteria.and(Restrictions.like("token", token));
            }
            if(!StringUtils.isBlank(name)){
                criteria.and(Restrictions.like("name", name));
            }
            if(!StringUtils.isBlank(companyName)){
                criteria.and(Restrictions.like("companyName", companyName));
            }

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<Organization> page(PageRequest pageRequest, String token, String name, String companyName){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("createTime"));
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            if(!StringUtils.isBlank(token)){
                criteria.and(Restrictions.like("token", token));
            }
            if(!StringUtils.isBlank(name)){
                criteria.and(Restrictions.like("name", name));
            }
            if(!StringUtils.isBlank(companyName)){
                criteria.and(Restrictions.like("companyName", companyName));
            }

            List<Organization> list = mapper.findByParams(criteria);
            Long count = mapper.countByParams(criteria);

            return new Page<Organization>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
