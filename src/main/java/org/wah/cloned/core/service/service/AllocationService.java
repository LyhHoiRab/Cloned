package org.wah.cloned.core.service.service;

import org.wah.cloned.core.service.entity.Allocation;

import java.util.List;

public interface AllocationService{

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
    List<Allocation> find(String wechatId, String name);

    /**
     * 创建或更新客服分配池
     */
    void saveOrUpdatePool(String wechatId);
}
