package org.wah.cloned.core.service.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wah.cloned.core.service.entity.Service;
import org.wah.doraemon.mybatis.Criteria;

import java.util.List;

@Repository
public interface ServiceMapper{

    /**
     * 保存
     */
    void save(Service service);

    /**
     * 更新
     */
    void update(Service service);

    /**
     * 根据条件查询
     */
    Service getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询
     */
    List<Service> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
