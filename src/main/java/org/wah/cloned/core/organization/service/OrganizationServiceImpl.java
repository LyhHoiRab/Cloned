package org.wah.cloned.core.organization.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.cloned.core.organization.dao.OrganizationDao;
import org.wah.cloned.core.organization.entity.Organization;
import org.wah.doraemon.mybatis.Criteria;
import org.wah.doraemon.mybatis.Restrictions;
import org.wah.doraemon.security.exception.ServiceException;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrganizationServiceImpl implements OrganizationService{

    private Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    @Autowired
    private OrganizationDao organizationDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Organization organization){
        try{
            Assert.notNull(organization, "企业机构信息不能为空");

            organizationDao.saveOrUpdate(organization);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 更新
     */
    @Override
    @Transactional(readOnly = false)
    public void update(Organization organization){
        try{
            Assert.notNull(organization, "企业机构信息不能为空");
            Assert.hasText(organization.getId(), "企业机构ID不能为空");

            organizationDao.saveOrUpdate(organization);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @Override
    public Organization getById(String id){
        try{
            Assert.hasText(id, "企业机构ID不能为空");

            return organizationDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据条件查询
     */
    @Override
    public List<Organization> find(String token, String name, String companyName){
        try{
            return organizationDao.find(token, name, companyName);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Organization> page(PageRequest pageRequest, String token, String name, String companyName){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return organizationDao.page(pageRequest, token, name, companyName);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
