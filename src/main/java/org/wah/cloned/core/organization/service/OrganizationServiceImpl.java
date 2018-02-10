package org.wah.cloned.core.organization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.cloned.core.organization.dao.OrganizationDao;
import org.wah.cloned.core.organization.entity.Organization;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrganizationServiceImpl implements OrganizationService{

    @Autowired
    private OrganizationDao organizationDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Organization organization){
        Assert.notNull(organization, "企业机构信息不能为空");

        organizationDao.saveOrUpdate(organization);
    }

    /**
     * 更新
     */
    @Override
    @Transactional(readOnly = false)
    public void update(Organization organization){
        Assert.notNull(organization, "企业机构信息不能为空");
        Assert.hasText(organization.getId(), "企业机构ID不能为空");

        organizationDao.saveOrUpdate(organization);
    }

    /**
     * 根据ID查询
     */
    @Override
    public Organization getById(String id){
        Assert.hasText(id, "企业机构ID不能为空");

        return organizationDao.getById(id);
    }

    /**
     * 根据条件查询
     */
    @Override
    public List<Organization> find(String token, String name, String companyName, String accountId){
        return organizationDao.find(token, name, companyName, accountId);
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Organization> page(PageRequest pageRequest, String token, String name, String companyName, String accountId){
        Assert.notNull(pageRequest, "分页信息不能为空");

        return organizationDao.page(pageRequest, token, name, companyName, accountId);
    }
}
