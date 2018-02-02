package org.wah.cloned.core.wechat.dao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.wah.cloned.core.wechat.dao.mapper.DeviceMapper;
import org.wah.cloned.core.wechat.entity.Device;
import org.wah.doraemon.mybatis.Criteria;
import org.wah.doraemon.mybatis.Restrictions;
import org.wah.doraemon.security.exception.DataAccessException;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;
import org.wah.doraemon.utils.IDGenerator;

import java.util.Date;
import java.util.List;

@Repository
public class DeviceDao{

    private Logger logger = LoggerFactory.getLogger(DeviceDao.class);

    @Autowired
    private DeviceMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Device device){
        try{
            Assert.notNull(device, "设备信息不能为空");

            if(StringUtils.isBlank(device.getId())){
                Assert.hasText(device.getOrganizationId(), "企业ID不能为空");

                device.setId(IDGenerator.uuid32());
                device.setCreateTime(new Date());
                mapper.save(device);
            }else{
                device.setUpdateTime(new Date());
                mapper.update(device);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public Device getById(String id){
        try{
            Assert.hasText(id, "设备ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<Device> page(PageRequest pageRequest, String organizationId, String type, String phone, String imei){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
            criteria.sort(Restrictions.asc("createTime"));

            if(!StringUtils.isBlank(organizationId)){
                criteria.and(Restrictions.eq("organization", organizationId));
            }
            if(!StringUtils.isBlank(type)){
                criteria.and(Restrictions.like("type", type));
            }
            if(!StringUtils.isBlank(phone)){
                criteria.and(Restrictions.like("phone", phone));
            }
            if(!StringUtils.isBlank(imei)){
                criteria.and(Restrictions.like("imei", imei));
            }

            List<Device> list = mapper.findByParams(criteria);
            Long count = mapper.countByParams(criteria);

            return new Page<Device>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
