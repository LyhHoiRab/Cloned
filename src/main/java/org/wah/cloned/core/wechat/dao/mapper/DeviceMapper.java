package org.wah.cloned.core.wechat.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wah.cloned.core.wechat.entity.Device;
import org.wah.doraemon.mybatis.Criteria;

import java.util.List;

@Repository
public interface DeviceMapper{

    /**
     * 保存
     */
    void save(Device device);

    /**
     * 更新
     */
    void update(Device device);

    /**
     * 根据条件查询
     */
    Device getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询
     */
    List<Device> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
