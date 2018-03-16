package org.wah.cloned.core.wechat.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wah.cloned.core.wechat.entity.Message;
import org.wah.doraemon.mybatis.Criteria;

import java.util.List;

@Repository
public interface MessageMapper{

    void save(Message message);

    void update(Message message);

    Message getByParams(@Param("params") Criteria criteria);

    List<Message> findByParams(@Param("params") Criteria criteria);

    Long countByParams(@Param("params") Criteria criteria);
}
