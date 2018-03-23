package org.wah.cloned.core.wechat.dao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.wah.cloned.core.wechat.consts.AppStatus;
import org.wah.cloned.core.wechat.consts.WechatStatus;
import org.wah.cloned.core.wechat.dao.mapper.WechatMapper;
import org.wah.cloned.core.wechat.entity.Wechat;
import org.wah.doraemon.mybatis.Criteria;
import org.wah.doraemon.mybatis.Restrictions;
import org.wah.doraemon.security.exception.DataAccessException;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;
import org.wah.doraemon.utils.IDGenerator;

import java.util.Date;
import java.util.List;

@Repository
public class WechatDao{

    private Logger logger = LoggerFactory.getLogger(WechatDao.class);

    @Autowired
    private WechatMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Wechat wechat){
        try{
            Assert.notNull(wechat, "微信信息不能为空");

            if(StringUtils.isBlank(wechat.getId())){
                Assert.hasText(wechat.getWxno(), "微信号不能为空");
                Assert.hasText(wechat.getOrganizationId(), "企业ID不能为空");

                wechat.setId(IDGenerator.uuid32());
                wechat.setStatus(WechatStatus.OFFLINE);
                wechat.setAppStatus(AppStatus.OFFLINE);
                wechat.setCreateTime(new Date());
                mapper.save(wechat);
            }else{
                wechat.setUpdateTime(new Date());
                mapper.update(wechat);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public Wechat getById(String id){
        try{
            Assert.hasText(id, "微信ID不能为空");

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
    public Wechat getByWxno(String wxno){
        try{
            Assert.hasText(wxno, "微信号不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("wxno", wxno));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询
     */
    public List<Wechat> find(String organizationId, String wxno){
        try{
            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("createTime"));

            if(!StringUtils.isBlank(organizationId)){
                criteria.and(Restrictions.eq("organizationId", organizationId));
            }
            if(!StringUtils.isBlank(wxno)){
                criteria.and(Restrictions.like("wxno", wxno));
            }

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<Wechat> page(PageRequest pageRequest, String organizationId, String wxno){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
            criteria.sort(Restrictions.asc("createTime"));

            if(!StringUtils.isBlank(organizationId)){
                criteria.and(Restrictions.eq("organizationId", organizationId));
            }
            if(!StringUtils.isBlank(wxno)){
                criteria.and(Restrictions.like("wxno", wxno));
            }

            List<Wechat> list = mapper.findByParams(criteria);
            Long count = mapper.countByParams(criteria);

            return new Page<Wechat>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 检查过时App
     */
    public void updateAppStatusByTimeout(){
        try{

            mapper.updateAppStatusByTimeout(AppStatus.ONLINE, AppStatus.UNUSUAL, 60L);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
