package org.wah.cloned.rongcloud.user.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wah.cloned.rongcloud.user.entity.RongCloudUser;
import org.wah.doraemon.mybatis.Criteria;

@Repository
public interface RongCloudUserMapper{

    void save(RongCloudUser user);

    void update(RongCloudUser user);

    RongCloudUser getByParams(@Param("params") Criteria criteria);
}
