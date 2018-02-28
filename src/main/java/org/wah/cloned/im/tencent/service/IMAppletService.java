package org.wah.cloned.im.tencent.service;

import org.wah.cloned.im.tencent.entity.IMApplet;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;

import java.util.List;

public interface IMAppletService{

    /**
     * 保存
     */
    void save(IMApplet applet);

    /**
     * 更新
     */
    void update(IMApplet applet);

    /**
     * 根据ID查询
     */
    IMApplet getById(String id);

    /**
     * 根据条件查询
     */
    List<IMApplet> find(String id, String organizationId, String appId, String privateKeyPath, String publicKeyPath);

    /**
     * 分页查询
     */
    Page<IMApplet> page(PageRequest pageRequest, String organizationId, String appId, String privateKeyPath, String publicKeyPath);

    /**
     * 微信绑定IM
     */
    void binding(String wechatId, String appletId);
}
