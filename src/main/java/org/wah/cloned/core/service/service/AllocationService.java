package org.wah.cloned.core.service.service;

import org.wah.cloned.core.service.entity.Allocation;

import java.util.List;

public interface AllocationService{

    /**
     * 根据条件查询
     */
    List<Allocation> find(String wechatId, String name);
}
