package org.wah.cloned.im.tencent.dao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.wah.cloned.core.service.entity.Service;
import org.wah.cloned.core.wechat.entity.WechatFriend;
import org.wah.cloned.im.tencent.consts.IMRole;
import org.wah.cloned.im.tencent.consts.IMType;
import org.wah.cloned.im.tencent.dao.mapper.IMUserMapper;
import org.wah.cloned.im.tencent.entity.IMUser;
import org.wah.doraemon.mybatis.Criteria;
import org.wah.doraemon.mybatis.Restrictions;
import org.wah.doraemon.security.exception.DataAccessException;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;
import org.wah.doraemon.utils.IDGenerator;

import java.util.Date;
import java.util.List;

@Repository
public class IMUserDao{

    private Logger logger = LoggerFactory.getLogger(IMUserDao.class);

    @Autowired
    private IMUserMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(IMUser user){
        try{
            Assert.notNull(user, "腾讯云通讯用户信息不能为空");

            if(StringUtils.isBlank(user.getId())){
                Assert.hasText(user.getName(), "腾讯云通讯用户名称不能为空");
                Assert.hasText(user.getNickname(), "腾讯云通讯用户昵称不能为空");
                Assert.hasText(user.getSig(), "腾讯云通讯用户签名不能为空");
                Assert.hasText(user.getAppletId(), "腾讯云通讯用户应用ID不能为空");
                Assert.notNull(user.getRole(), "腾讯云通讯用户角色类型不能为空");

                user.setId(IDGenerator.uuid32());
                user.setCreateTime(new Date());
                mapper.save(user);
            }else{
                user.setUpdateTime(new Date());
                mapper.update(user);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 批量保存
     */
    public void saveBatch(List<IMUser> users){
        try{
            Assert.notEmpty(users, "腾讯云通讯用户组不能为空");

            final Date now = new Date();

            for(IMUser user : users){
                Assert.hasText(user.getName(), "腾讯云通讯用户名称不能为空");
                Assert.hasText(user.getNickname(), "腾讯云通讯用户昵称不能为空");
                Assert.hasText(user.getSig(), "腾讯云通讯用户签名不能为空");
                Assert.hasText(user.getAppletId(), "腾讯云通讯用户应用ID不能为空");
                Assert.notNull(user.getRole(), "腾讯云通讯用户角色类型不能为空");

                user.setId(IDGenerator.uuid32());
                user.setCreateTime(now);
            }

            mapper.saveBatch(users);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID组删除
     */
    public void deleteByIds(List<String> ids){
        try{
            Assert.notEmpty(ids, "腾讯云通讯用户ID组不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.in("name", ids));

            mapper.delete(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public IMUser getById(String id){
        try{
            Assert.hasText(id, "腾讯云通讯用户ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据名称查询
     */
    public IMUser getByName(String name){
        try{
            Assert.hasText(name, "腾讯云通讯用户名称不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("name", name));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据应用ID查询管理员
     */
    public IMUser getAdminByAppletId(String appletId){
        try{
            Assert.hasText(appletId, "腾讯云通讯应用ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("appletId", appletId));
            criteria.and(Restrictions.eq("role", IMRole.ADMIN.getId()));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据条件查询
     */
    public List<IMUser> find(String id, String appletId, String name, String nickname, IMType type, IMRole role){
        try{
            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("createTime"));

            if(!StringUtils.isBlank(appletId)){
                criteria.and(Restrictions.eq("appletId", appletId));
            }
            if(!StringUtils.isBlank(id)){
                criteria.and(Restrictions.eq("id", id));
            }
            if(!StringUtils.isBlank(name)){
                criteria.and(Restrictions.like("name", name));
            }
            if(!StringUtils.isBlank(nickname)){
                criteria.and(Restrictions.like("nickname", nickname));
            }
            if(type != null){
                criteria.and(Restrictions.eq("type", type.getId()));
            }
            if(role != null){
                criteria.and(Restrictions.eq("role", role.getId()));
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
    public Page<IMUser> page(PageRequest pageRequest, String appletId, String name, String nickname, IMType type, IMRole role){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("createTime"));
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            if(!StringUtils.isBlank(appletId)){
                criteria.and(Restrictions.eq("appletId", appletId));
            }
            if(!StringUtils.isBlank(name)){
                criteria.and(Restrictions.like("name", name));
            }
            if(!StringUtils.isBlank(nickname)){
                criteria.and(Restrictions.like("nickname", nickname));
            }
            if(type != null){
                criteria.and(Restrictions.eq("type", type.getId()));
            }
            if(role != null){
                criteria.and(Restrictions.eq("role", role.getId()));
            }

            List<IMUser> list = mapper.findByParams(criteria);
            Long count = mapper.countByParams(criteria);

            return new Page<IMUser>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
