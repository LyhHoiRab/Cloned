package org.wah.cloned.rongcloud.token.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wah.cloned.rongcloud.token.entity.RongCloudToken;
import org.wah.doraemon.mybatis.Criteria;

import java.util.List;

@Repository
public interface RongCloudTokenMapper{

    /**
     * 保存
     */
    void save(RongCloudToken token);

    /**
     * 更新
     */
    void update(RongCloudToken token);

    /**
     * 根据条件查询
     */
    RongCloudToken getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询
     */
    List<RongCloudToken> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
