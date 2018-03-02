package org.wah.cloned.im.tencent.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wah.cloned.im.tencent.consts.IMRole;
import org.wah.cloned.im.tencent.entity.IMApplet;
import org.wah.doraemon.mybatis.Criteria;

import java.util.List;

@Repository
public interface IMAppletMapper{

    /**
     * 保存
     */
    void save(IMApplet applet);

    /**
     * 更新
     */
    void update(IMApplet applet);

    /**
     * 根据条件查询
     */
    IMApplet getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询
     */
    List<IMApplet> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);

    /**
     * 根据微信ID查询
     */
    IMApplet getByWechatId(@Param("wechatId") String wechatId, @Param("role") IMRole role);
}
