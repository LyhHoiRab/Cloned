package org.wah.cloned.core.service.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wah.cloned.core.service.entity.Allocation;
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
    void update(List<Allocation> list);

    /**
     * 设置默认概率
     */
    void setDefaultProbability(String wechatId);

    /**
     * 根据条件查询
     */
    List<Allocation> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
