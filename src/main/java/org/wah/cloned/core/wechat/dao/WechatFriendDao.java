package org.wah.cloned.core.wechat.dao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.wah.cloned.core.wechat.dao.mapper.WechatFriendMapper;
import org.wah.cloned.core.wechat.entity.WechatFriend;
import org.wah.doraemon.consts.Sex;
import org.wah.doraemon.mybatis.Criteria;
import org.wah.doraemon.mybatis.Restrictions;
import org.wah.doraemon.security.exception.DataAccessException;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;
import org.wah.doraemon.utils.IDGenerator;

import java.util.Date;
import java.util.List;

@Repository
public class WechatFriendDao{

    private Logger logger = LoggerFactory.getLogger(WechatFriendDao.class);

    @Autowired
    private WechatFriendMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(WechatFriend friend){
        try{
            Assert.notNull(friend, "微信好友信息不能为空");

            if(StringUtils.isBlank(friend.getId())){
                Assert.hasText(friend.getNickname(), "微信好友昵称不能为空");

                friend.setId(IDGenerator.uuid32());
                friend.setRemarkname(IDGenerator.uuidTo36Ary(friend.getId()));
                friend.setCreateTime(new Date());
                mapper.save(friend);
            }else{
                friend.setUpdateTime(new Date());
                mapper.update(friend);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public WechatFriend getById(String id){
        try{
            Assert.hasText(id, "微信好友ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据备注名称查询
     */
    public WechatFriend getByRemarkname(String remarkname){
        try{
            Assert.hasText(remarkname, "微信好友备注名称不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("remarkname", remarkname));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据微信ID和备注名称查询
     */
    public WechatFriend getByWechatIdAndRemarkname(String wechatId, String remarkname){
        try{
            Assert.hasText(wechatId, "微信ID不能为空");
            Assert.hasText(remarkname, "微信好友备注名称不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("wechatId", wechatId));
            criteria.and(Restrictions.eq("remarkname", remarkname));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据微信ID查询
     */
    public List<WechatFriend> findByWechatId(String wechatId){
        try{
            Assert.hasText(wechatId, "微信ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("wechatId", wechatId));

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<WechatFriend> page(PageRequest pageRequest, String wechatId, String nickname, String remarkname, Sex sex){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
            criteria.sort(Restrictions.asc("createTime"));

            if(!StringUtils.isBlank(wechatId)){
                criteria.and(Restrictions.eq("wechatId", wechatId));
            }
            if(!StringUtils.isBlank(nickname)){
                criteria.and(Restrictions.like("nickname", nickname));
            }
            if(!StringUtils.isBlank(remarkname)){
                criteria.and(Restrictions.like("remarkname", remarkname));
            }
            if(sex != null){
                criteria.and(Restrictions.eq("sex", sex.getId()));
            }

            List<WechatFriend> list = mapper.findByParams(criteria);
            Long count = mapper.countByParams(criteria);

            return new Page<WechatFriend>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
