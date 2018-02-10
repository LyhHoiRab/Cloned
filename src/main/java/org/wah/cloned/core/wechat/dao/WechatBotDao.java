package org.wah.cloned.core.wechat.dao;

import io.github.biezhi.wechat.api.constant.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.wah.cloned.commons.security.consts.CacheParamName;
import org.wah.cloned.core.wechat.entity.WechatBot;
import org.wah.doraemon.security.exception.DataAccessException;

@Repository
public class WechatBotDao{

    private Logger logger = LoggerFactory.getLogger(WechatBotDao.class);

    /**
     * 保存
     */
    public void save(String wechatId){
        try{
            Assert.notNull(wechatId, "微信ID不能为空");
            //配置
            Config config = Config.me().autoLogin(false).showTerminal(false).autoAddFriend(true);
            //创建机器人
            WechatBot bot = new WechatBot(config);
            bot.setWechatId(wechatId);
            //缓存机器人
            CacheParamName.bots.put(wechatId, bot);
            //启动
            bot.start();
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public WechatBot getById(String id){
        try{
            Assert.hasText(id, "微信机器人ID不能为空");

            return CacheParamName.bots.get(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
