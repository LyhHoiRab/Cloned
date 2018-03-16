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

    /**
     * 根据微信号查询微信IM账号
     */
    IMUser getWechatByWxno(String wxno);

    /**
     * 根据账号密码和微信号查询IM账号
     */
    IMUser getServiceByAccountAndWxno(String username, String password, String wxno);

    /**
     * 微信号登录
     */
    IMUser loginByWechat(String wxno, String phone, String imei);
}
