package org.wah.cloned.im.tencent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.cloned.core.service.dao.ServiceDao;
import org.wah.cloned.core.wechat.dao.WechatDao;
import org.wah.cloned.core.wechat.entity.Wechat;
import org.wah.cloned.im.tencent.consts.IMRole;
import org.wah.cloned.im.tencent.dao.IMAppletDao;
import org.wah.cloned.im.tencent.dao.IMUserDao;
import org.wah.cloned.im.tencent.entity.IMApplet;
import org.wah.cloned.im.tencent.entity.IMUser;
import org.wah.cloned.im.tencent.utils.IMUtils;
import org.wah.cloned.im.tencent.utils.SignCheckerUtils;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;
import org.wah.doraemon.utils.EntityUtils;
import org.wah.doraemon.utils.IDGenerator;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class IMAppletServiceImpl implements IMAppletService{

    @Autowired
    private IMAppletDao imAppletDao;

    @Autowired
    private IMUserDao imUserDao;

    @Autowired
    private WechatDao wechatDao;

    @Autowired
    private ServiceDao serviceDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public void save(IMApplet applet){
        Assert.notNull(applet, "腾讯云通讯应用信息不能为空");
        Assert.hasText(applet.getOrganizationId(), "腾讯云通讯应用企业ID不能为空");
        Assert.hasText(applet.getAppId(), "腾讯云通讯应用AppId不能为空");
        Assert.hasText(applet.getPrivateKeyPath(), "腾讯云通讯应用密钥路径不能为空");
        Assert.hasText(applet.getPublicKeyPath(), "腾讯云通讯应用公钥路径不能为空");

        imAppletDao.saveOrUpdate(applet);
    }

    /**
     * 更新
     */
    @Override
    @Transactional(readOnly = false)
    public void update(IMApplet applet){
        Assert.notNull(applet, "腾讯云通讯应用信息不能为空");
        Assert.hasText(applet.getId(), "腾讯云通讯应用ID不能为空");

        imAppletDao.saveOrUpdate(applet);
    }

    /**
     * 根据ID查询
     */
    @Override
    public IMApplet getById(String id){
        Assert.hasText(id, "腾讯云通讯应用ID不能为空");

        return imAppletDao.getById(id);
    }

    /**
     * 根据条件查询
     */
    @Override
    public List<IMApplet> find(String id, String organizationId, String appId, String privateKeyPath, String publicKeyPath){
        return imAppletDao.find(id, organizationId, appId, privateKeyPath, publicKeyPath);
    }

    /**
     * 分页查询
     */
    @Override
    public Page<IMApplet> page(PageRequest pageRequest, String organizationId, String appId, String privateKeyPath, String publicKeyPath){
        Assert.notNull(pageRequest, "分页信息不能为空");

        return imAppletDao.page(pageRequest, organizationId, appId, privateKeyPath, publicKeyPath);
    }

    /**
     * 微信绑定IM
     */
    @Override
    @Transactional(readOnly = false)
    public void binding(String wechatId, String appletId){
        Assert.hasText(wechatId, "微信ID不能为空");
        Assert.hasText(appletId, "腾讯云通讯应用ID不能为空");

        //应用
        IMApplet applet = imAppletDao.getById(appletId);
        //管理员
        IMUser admin = imUserDao.getAdminByAppletId(appletId);
        //微信
        Wechat wechat = wechatDao.getById(wechatId);
        //客服
        List<org.wah.cloned.core.service.entity.Service> services = serviceDao.findByWechatId(wechatId);
        //IDs
        List<String> ids = EntityUtils.getIds(services);
        ids.add(wechatId);

        //删除微信和客服
        imUserDao.deleteByIds(ids);

        //绑定的IM用户组
        List<IMUser> users = new ArrayList<IMUser>();
        List<String> userNames = new ArrayList<String>();

        //创建微信对应的IM用户
        IMUser wechatUser = new IMUser();
        wechatUser.setName(wechat.getId());
        wechatUser.setNickname(wechat.getWxno());
        wechatUser.setAppletId(appletId);
        wechatUser.setAppId(applet.getAppId());
        wechatUser.setRole(IMRole.WECHAT);
        wechatUser.setSig(SignCheckerUtils.get(applet.getAppId(), wechat.getId(), applet.getPrivateKeyPath()));
        users.add(wechatUser);
        userNames.add(wechatUser.getName());

        //创建客服对应的IM用户
        List<IMUser> friends = new ArrayList<IMUser>();
        for(org.wah.cloned.core.service.entity.Service service : services){
            IMUser serviceUser = new IMUser();
            serviceUser.setName(service.getId());
            serviceUser.setNickname(service.getName());
            serviceUser.setHeadImgUrl(service.getHeadImgUrl());
            serviceUser.setAppletId(appletId);
            serviceUser.setAppId(applet.getAppId());
            serviceUser.setRole(IMRole.SERVICE);
            serviceUser.setSig(SignCheckerUtils.get(applet.getAppId(), service.getId(), applet.getPrivateKeyPath()));
            users.add(serviceUser);
            userNames.add(serviceUser.getName());

            //添加到好友列表
            friends.add(serviceUser);
        }
        //保存IM用户
        imUserDao.saveBatch(users);
        //注册到腾讯云
        IMUtils.multiOpenLogin(admin.getSig(), admin.getAppId(), admin.getName(), userNames);
        //创建关系链
        IMUtils.friendAdd(admin.getSig(), admin.getAppId(), admin.getName(), wechatUser, friends);
    }
}
