package org.wah.cloned.im.tencent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.cloned.im.tencent.consts.IMRole;
import org.wah.cloned.im.tencent.dao.IMAppletDao;
import org.wah.cloned.im.tencent.dao.IMUserDao;
import org.wah.cloned.im.tencent.entity.IMApplet;
import org.wah.cloned.im.tencent.entity.IMUser;
import org.wah.cloned.im.tencent.utils.SignCheckerUtils;

@Service
@Transactional(readOnly = true)
public class IMUserServiceImpl implements IMUserService{

    @Autowired
    private IMUserDao imUserDao;

    @Autowired
    private IMAppletDao imAppletDao;

    /**
     * 添加管理员
     */
    @Override
    @Transactional(readOnly = false)
    public IMUser saveAdmin(String identifier, String appletId){
        Assert.hasText(identifier, "腾讯云通讯管理员账号不能为空");
        Assert.hasText(appletId, "腾讯云通讯应用ID不能为空");

        //应用
        IMApplet applet = imAppletDao.getById(appletId);

        //创建管理员账号
        IMUser user = new IMUser();
        user.setName(identifier);
        user.setNickname(identifier);
        user.setAppletId(appletId);
        user.setAppId(applet.getAppId());
        user.setRole(IMRole.ADMIN);
        user.setSig(SignCheckerUtils.get(applet.getAppId(), identifier, applet.getPrivateKeyPath()));
        imUserDao.saveOrUpdate(user);

        return user;
    }

    /**
     * 根据ID查询
     */
    @Override
    public IMUser getById(String id){
        Assert.hasText(id, "腾讯云通讯用户ID不能为空");

        return imUserDao.getById(id);
    }

    /**
     * 根据名称查询
     */
    @Override
    public IMUser getByName(String name){
        Assert.hasText(name, "腾讯云通讯用户名称不能为空");

        return imUserDao.getByName(name);
    }

    /**
     * 根据应用ID查询管理员
     */
    @Override
    public IMUser getAdminByAppletId(String appletId){
        Assert.hasText(appletId, "腾讯云通讯应用ID不能为空");

        return imUserDao.getAdminByAppletId(appletId);
    }

    /**
     * 根据微信号查询微信IM账号
     */
    @Override
    public IMUser getWechatByWxno(String wxno){
        Assert.hasText(wxno, "微信号不能为空");

        return imUserDao.getWechatByWxno(wxno);
    }
}
