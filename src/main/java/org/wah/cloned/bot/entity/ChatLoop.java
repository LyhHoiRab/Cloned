package org.wah.cloned.bot.entity;

import io.github.biezhi.wechat.api.model.SyncCheckRet;
import io.github.biezhi.wechat.api.response.WebSyncResponse;
import io.github.biezhi.wechat.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.wah.cloned.bot.service.WechatApi;
import org.wah.cloned.commons.security.context.ApplicationContextUtils;
import org.wah.cloned.core.wechat.consts.WechatStatus;
import org.wah.cloned.core.wechat.dao.WechatDao;
import org.wah.cloned.core.wechat.entity.Wechat;

import static io.github.biezhi.wechat.api.enums.RetCode.*;

@Slf4j
public class ChatLoop implements Runnable{

    private WechatBot bot;
    private WechatApi api;
    private int retryCount = 0;

    public ChatLoop(WechatBot bot){
        this.bot = bot;
        this.api = bot.getWechatApi();
    }

    @Override
    public void run(){
        while(bot.isRunning()){
            try{
                WechatDao wechatDao = (WechatDao) ApplicationContextUtils.getByClass(WechatDao.class);
                Wechat wechat = wechatDao.getById(bot.getWechatId());

                SyncCheckRet syncCheckRet = api.syncCheck();

                if(syncCheckRet.getRetCode() == UNKNOWN){
                    continue;

                }else if(syncCheckRet.getRetCode() == MOBILE_LOGIN_OUT){
                    api.logout();
                    wechat.setStatus(WechatStatus.OFFLINE);
                    wechatDao.saveOrUpdate(wechat);
                    break;

                }else if(syncCheckRet.getRetCode() == LOGIN_OTHERWISE){
                    api.logout();
                    wechat.setStatus(WechatStatus.OFFLINE);
                    wechatDao.saveOrUpdate(wechat);
                    break;

                }else if(syncCheckRet.getRetCode() == NORMAL){
                    //更新状态
                    wechat.setStatus(WechatStatus.ONLINE);
                    wechatDao.saveOrUpdate(wechat);
                    //更新最后一次正常检查时间
                    bot.updateLastCheck();
                    WebSyncResponse webSyncResponse = api.webSync();

                    switch(syncCheckRet.getSelector()){
                        case 2:
                            if(webSyncResponse == null){
                                break;
                            }
                            bot.addMessages(api.handleMsg(webSyncResponse.getAddMessageList()));
                            break;
                        case 6:
                            api.loadContact(0);
                            break;
                        default:
                            break;
                    }
                }

                if(System.currentTimeMillis() - bot.getLastCheckTs() <= 30){
                    DateUtils.sleep(System.currentTimeMillis() - bot.getLastCheckTs());
                }
            }catch(Exception e){
                retryCount += 1;

                if(bot.getReceiveRetryCount() < retryCount){
                    bot.setRunning(false);
                }else{
                    DateUtils.sleep(5000);
                }
            }
        }
    }
}
