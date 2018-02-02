package org.wah.cloned.core.wechat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.cloned.core.wechat.dao.DeviceDao;
import org.wah.cloned.core.wechat.entity.Device;
import org.wah.doraemon.security.exception.ServiceException;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;

@Service
@Transactional(readOnly = true)
public class DeviceServiceImpl implements DeviceService{

    private Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

    @Autowired
    private DeviceDao deviceDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Device device){
        try{
            Assert.notNull(device, "设备信息不能为空");

            deviceDao.saveOrUpdate(device);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 更新
     */
    @Override
    @Transactional(readOnly = false)
    public void update(Device device){
        try{
            Assert.notNull(device, "设备信息不能为空");
            Assert.hasText(device.getId(), "设备ID不能为空");

            deviceDao.saveOrUpdate(device);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @Override
    public Device getById(String id){
        try{
            Assert.hasText(id, "设备ID不能为空");

            return deviceDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Device> page(PageRequest pageRequest, String organizationId, String type, String phone, String imei){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return deviceDao.page(pageRequest, organizationId, type, phone, imei);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
