package org.wah.cloned.rongcloud.message.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wah.cloned.rongcloud.message.entity.RongCloudMessage;
import org.wah.doraemon.mybatis.Criteria;

import java.util.List;

@Repository
public interface RongCloudMessageMapper{

    /**
     * 保存
     */
    void save(RongCloudMessage message);

    /**
     * 更新
     */
    void update(RongCloudMessage message);

    /**
     * 根据条件查询
     */
    RongCloudMessage getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询
     */
    List<RongCloudMessage> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
