package org.wah.cloned.core.wechat.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wah.cloned.core.wechat.consts.AppStatus;
import org.wah.cloned.core.wechat.entity.Wechat;
import org.wah.doraemon.mybatis.Criteria;

import java.util.List;

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

    /**
     * 根据条件查询
     */
    List<Wechat> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);

    /**
     * 检查过时App
     */
    void updateAppStatusByTimeout(@Param("checkStatus") AppStatus checkStatus, @Param("timeoutStatus") AppStatus timeoutStatus, @Param("timeout") Long timeout);
}
