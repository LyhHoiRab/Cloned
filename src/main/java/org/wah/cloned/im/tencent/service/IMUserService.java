package org.wah.cloned.im.tencent.service;

import org.wah.cloned.im.tencent.entity.IMUser;

public interface IMUserService{

    /**
     * 添加管理员
     */
    IMUser saveAdmin(String identifier, String appletId);

    /**
     * 根据ID查询
     */
    IMUser getById(String id);

    /**
     * 根据名称查询
     */
    IMUser getByName(String name);

    /**
     * 根据应用ID查询管理员
     */
    IMUser getAdminByAppletId(String appletId);
}
