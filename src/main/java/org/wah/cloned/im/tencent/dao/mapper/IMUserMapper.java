package org.wah.cloned.im.tencent.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wah.cloned.im.tencent.consts.IMRole;
import org.wah.cloned.im.tencent.entity.IMUser;
import org.wah.doraemon.mybatis.Criteria;

import java.util.List;

@Repository
public interface IMUserMapper{

    /**
     * 保存
     */
    void save(IMUser user);

    /**
     * 批量保存
     */
    void saveBatch(@Param("users") List<IMUser> users);

    /**
     * 更新
     */
    void update(IMUser user);

    /**
     * 删除
     */
    void delete(@Param("params") Criteria criteria);

    /**
     * 根据条件查询
     */
    IMUser getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询
     */
    List<IMUser> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
