package org.wah.cloned.core.wechat.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wah.cloned.core.wechat.entity.WechatFriend;
import org.wah.doraemon.mybatis.Criteria;

import java.util.List;

@Repository
public interface WechatFriendMapper{

    /**
     * 保存
     */
    void save(WechatFriend friend);

    /**
     * 更新
     */
    void update(WechatFriend friend);

    /**
     * 根据条件查询
     */
    WechatFriend getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询
     */
    List<WechatFriend> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据数量查询
     */
    Long countByParams(@Param("params") Criteria criteria);
}
