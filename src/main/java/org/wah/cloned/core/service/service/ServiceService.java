package org.wah.cloned.core.service.service;

import org.wah.cloned.core.service.entity.Service;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;

import java.util.List;

public interface ServiceService{

    /**
     * 保存
     */
    void save(Service service);

    /**
     * 更新
     */
    void update(Service service);

    /**
     * 根据ID查询
     */
    Service getById(String id);

    /**
     * 根据微信ID查询
     */
    List<Service> findByWechatId(String wechatId);

    /**
     * 分页查询
     */
    Page<Service> page(PageRequest pageRequest, String organizationId, String wxno, String wechatId, String name);
}
