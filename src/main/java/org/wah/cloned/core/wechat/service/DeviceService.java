package org.wah.cloned.core.wechat.service;

import org.wah.cloned.core.wechat.entity.Device;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;

import java.util.List;

public interface DeviceService{

    /**
     * 保存
     */
    void save(Device device);

    /**
     * 更新
     */
    void update(Device device);

    /**
     * 根据ID查询
     */
    Device getById(String id);

    /**
     * 分页查询
     */
    Page<Device> page(PageRequest pageRequest, String organizationId, String type, String phone, String imei);

    /**
     * 查询
     */
    List<Device> find(String organizationId, String type, String phone, String imei);
}
