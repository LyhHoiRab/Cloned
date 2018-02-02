package org.wah.cloned.core.wechat.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wah.cloned.core.wechat.entity.Wechat;
import org.wah.doraemon.mybatis.Criteria;

@Repository
public interface WechatMapper{

    /**
     * 保存
     */
    void save(Wechat wechat);

    /**
     * 更新
     */
    void update(Wechat wechat);

    /**
     * 根据条件查询
     */
    Wechat getByParams(@Param("params") Criteria criteria);
}
