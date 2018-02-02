package org.wah.cloned.rongcloud.user.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wah.cloned.rongcloud.user.entity.Allocation;
import org.wah.doraemon.mybatis.Criteria;

import java.util.List;

@Repository
public interface AllocationMapper{

    /**
     * 保存
     */
    void save(Allocation allocation);

    /**
     * 更新
     */
    void update(Allocation allocation);

    /**
     * 根据条件查询
     */
    Allocation getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询
     */
    List<Allocation> findByParams(@Param("params") Criteria criteria);
}
