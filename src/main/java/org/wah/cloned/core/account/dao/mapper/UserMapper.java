package org.wah.cloned.core.account.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wah.doraemon.entity.User;
import org.wah.doraemon.mybatis.Criteria;

import java.util.List;

@Repository
public interface UserMapper{

    /**
     * 保存
     */
    void save(User user);

    /**
     * 更新
     */
    void update(User user);

    /**
     * 根据条件查询
     */
    User getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询
     */
    List<User> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);

    /**
     * 根据微信ID查询为客服的用户信息
     */
    List<User> findIsServiceByWechatId(String wechatId);

    /**
     * 根据微信ID查询不为客服的用户信息
     */
    List<User> findIsNotServiceByWechatId(@Param("params") Criteria criteria);
}
