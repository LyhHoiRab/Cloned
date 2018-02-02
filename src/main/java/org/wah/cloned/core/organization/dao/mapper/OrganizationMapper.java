package org.wah.cloned.core.organization.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wah.cloned.core.organization.entity.Organization;
import org.wah.doraemon.mybatis.Criteria;

import java.util.List;

@Repository
public interface OrganizationMapper{

    /**
     * 保存
     */
    void save(Organization organization);

    /**
     * 更新
     */
    void update(Organization organization);

    /**
     * 根据条件查询
     */
    Organization getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询
     */
    List<Organization> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
