package org.wah.cloned.im.tencent.dao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.wah.cloned.im.tencent.consts.IMRole;
import org.wah.cloned.im.tencent.dao.mapper.IMAppletMapper;
import org.wah.cloned.im.tencent.entity.IMApplet;
import org.wah.doraemon.mybatis.Criteria;
import org.wah.doraemon.mybatis.Restrictions;
import org.wah.doraemon.security.exception.DataAccessException;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;
import org.wah.doraemon.utils.IDGenerator;

import java.util.Date;
import java.util.List;

@Repository
public class IMAppletDao{

    private Logger logger = LoggerFactory.getLogger(IMAppletDao.class);

    @Autowired
    private IMAppletMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(IMApplet applet){
        try{
            Assert.notNull(applet, "腾讯云通讯应用信息不能为空");

            if(StringUtils.isBlank(applet.getId())){
                Assert.hasText(applet.getOrganizationId(), "腾讯云通讯应用企业ID不能为空");
                Assert.hasText(applet.getAppId(), "腾讯云通讯应用AppId不能为空");
                Assert.hasText(applet.getPrivateKeyPath(), "腾讯云通讯应用密钥路径不能为空");
                Assert.hasText(applet.getPublicKeyPath(), "腾讯云通讯应用公钥路径不能为空");

                applet.setId(IDGenerator.uuid32());
                applet.setCreateTime(new Date());
                mapper.save(applet);
            }else{
                applet.setUpdateTime(new Date());
                mapper.update(applet);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public IMApplet getById(String id){
        try{
            Assert.hasText(id, "腾讯云通讯应用ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据微信号查询
     */
    public IMApplet getByWechatId(String wechatId){
        try{
            Assert.hasText(wechatId, "微信号ID不能为空");

            return mapper.getByWechatId(wechatId, IMRole.WECHAT);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据条件查询
     */
    public List<IMApplet> find(String id, String organizationId, String appId, String privateKeyPath, String publicKeyPath){
        try{
            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("createTime"));

            if(!StringUtils.isBlank(id)){
                criteria.and(Restrictions.eq("id", id));
            }
            if(!StringUtils.isBlank(organizationId)){
                criteria.and(Restrictions.eq("organizationId", organizationId));
            }
            if(!StringUtils.isBlank(appId)){
                criteria.and(Restrictions.eq("appId", appId));
            }
            if(!StringUtils.isBlank(privateKeyPath)){
                criteria.and(Restrictions.like("privateKeyPath", privateKeyPath));
            }
            if(!StringUtils.isBlank(publicKeyPath)){
                criteria.and(Restrictions.like("publicKeyPath", publicKeyPath));
            }

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public Page<IMApplet> page(PageRequest pageRequest, String organizationId, String appId, String privateKeyPath, String publicKeyPath){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("createTime"));
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            if(!StringUtils.isBlank(organizationId)){
                criteria.and(Restrictions.eq("organizationId", organizationId));
            }
            if(!StringUtils.isBlank(appId)){
                criteria.and(Restrictions.eq("appId", appId));
            }
            if(!StringUtils.isBlank(privateKeyPath)){
                criteria.and(Restrictions.like("privateKeyPath", privateKeyPath));
            }
            if(!StringUtils.isBlank(publicKeyPath)){
                criteria.and(Restrictions.like("publicKeyPath", publicKeyPath));
            }

            List<IMApplet> list = mapper.findByParams(criteria);
            Long count = mapper.countByParams(criteria);

            return new Page<IMApplet>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
