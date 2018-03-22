package org.wah.cloned.core.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.cloned.core.service.dao.AllocationDao;
import org.wah.cloned.core.service.dao.ServiceDao;
import org.wah.cloned.core.service.entity.Allocation;
import org.wah.cloned.im.tencent.consts.IMRole;
import org.wah.cloned.im.tencent.dao.IMAppletDao;
import org.wah.cloned.im.tencent.dao.IMUserDao;
import org.wah.cloned.im.tencent.entity.IMApplet;
import org.wah.cloned.im.tencent.entity.IMUser;
import org.wah.cloned.im.tencent.utils.IMUtils;
import org.wah.cloned.im.tencent.utils.SignCheckerUtils;
import org.wah.cloned.im.tencent.utils.SignatureUtils;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;
import org.wah.doraemon.utils.IDGenerator;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ServiceServiceImpl implements ServiceService{

    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private AllocationDao allocationDao;

    @Autowired
    private IMUserDao imUserDao;

    @Autowired
    private IMAppletDao imAppletDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public void save(org.wah.cloned.core.service.entity.Service service){
        Assert.notNull(service, "客服信息不能为空");
        //保存客服
        serviceDao.saveOrUpdate(service);
        //保存概率
        Allocation allocation = new Allocation();
        allocation.setService(service);
        allocation.setProbability(0D);
        allocation.setDefaultProbability(0D);
        allocation.setStep(1D);
        allocation.setIsOfflineAllot(true);
        allocationDao.save(allocation);
        //查询应用
        IMApplet applet = imAppletDao.getByWechatId(service.getWechatId());
        if(applet != null){
            //生成签名
            String sig = SignCheckerUtils.get(applet.getAppId(), service.getId(), applet.getPrivateKeyPath());
            //注册腾讯云
            IMUser user = new IMUser();
            user.setName(service.getId());
            user.setNickname(service.getName());
            user.setHeadImgUrl(service.getHeadImgUrl());
            user.setSig(sig);
            user.setAppletId(applet.getId());
            user.setAppId(applet.getAppId());
            user.setRole(IMRole.SERVICE);
            imUserDao.saveOrUpdate(user);

            //管理员
            IMUser admin = imUserDao.getAdminByAppletId(applet.getId());
            //注册到腾讯服务器
            IMUtils.openLogin(admin.getSig(), admin.getAppId(), admin.getName(), user);
            //微信号
            IMUser wechat = imUserDao.getByName(service.getWechatId());
            //创建关系链
            IMUtils.friendAdd(admin.getSig(), admin.getAppId(), admin.getName(), wechat, user);
        }
    }

    /**
     * 更新
     */
    @Override
    @Transactional(readOnly = false)
    public void update(org.wah.cloned.core.service.entity.Service service){
        Assert.notNull(service, "客服信息不能为空");
        Assert.hasText(service.getId(), "客服ID不能为空");

        serviceDao.saveOrUpdate(service);
    }

    /**
     * 根据ID查询
     */
    @Override
    public org.wah.cloned.core.service.entity.Service getById(String id){
        Assert.hasText(id, "客服ID不能为空");

        return serviceDao.getById(id);
    }

    /**
     * 根据微信ID查询
     */
    @Override
    public List<org.wah.cloned.core.service.entity.Service> findByWechatId(String wechatId){
        Assert.hasText(wechatId, "客服关联微信ID不能为空");

        return serviceDao.findByWechatId(wechatId);
    }

    /**
     * 分页查询
     */
    @Override
    public Page<org.wah.cloned.core.service.entity.Service> page(PageRequest pageRequest, String organizationId, String wxno, String wechatId, String name){
        Assert.notNull(pageRequest, "分页信息不能为空");

        return serviceDao.page(pageRequest, organizationId, wxno, wechatId, name);
    }
}
