package org.wah.cloned.core.organization.service;

import org.wah.cloned.core.organization.entity.Organization;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;

import java.util.List;

public interface OrganizationService{

    /**
     * 保存
     */
    void save(Organization organization);

    /**
     * 更新
     */
    void update(Organization organization);

    /**
     * 根据ID查询
     */
    Organization getById(String id);

    /**
     * 根据条件查询
     */
    List<Organization> find(String token, String name, String companyName);

    /**
     * 分页查询
     */
    Page<Organization> page(PageRequest pageRequest, String token, String name, String companyName);
}
