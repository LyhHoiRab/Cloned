package org.wah.cloned.core.wechat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.cloned.core.wechat.dao.DeviceDao;
import org.wah.cloned.core.wechat.entity.Device;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DeviceServiceImpl implements DeviceService{

    @Autowired
    private DeviceDao deviceDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Device device){
        Assert.notNull(device, "设备信息不能为空");
        Assert.hasText(device.getOrganizationId(), "企业ID不能为空");

        deviceDao.saveOrUpdate(device);
    }

    /**
     * 更新
     */
    @Override
    @Transactional(readOnly = false)
    public void update(Device device){
        Assert.notNull(device, "设备信息不能为空");
        Assert.hasText(device.getId(), "设备ID不能为空");

        deviceDao.saveOrUpdate(device);
    }

    /**
     * 根据ID查询
     */
    @Override
    public Device getById(String id){
        Assert.hasText(id, "设备ID不能为空");

        return deviceDao.getById(id);
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Device> page(PageRequest pageRequest, String organizationId, String type, String phone, String imei){
        Assert.notNull(pageRequest, "分页信息不能为空");

        return deviceDao.page(pageRequest, organizationId, type, phone, imei);
    }

    /**
     * 查询
     */
    @Override
    public List<Device> find(String organizationId, String type, String phone, String imei){
        return deviceDao.find(organizationId, type, phone, imei);
    }
}
