package org.wah.cloned.bot.entity;

import io.github.biezhi.wechat.api.enums.ApiURL;
import lombok.Data;

@Data
public class Download{

    private ApiURL apiURL;
    private String suffix;
    private String msgId;
    private Object[] params;
    private boolean saveByDay;

    public Download(ApiURL apiURL, String... params){
        this.apiURL = apiURL;
        this.params = params;
        this.suffix = apiURL.getSuffix();
    }

    public Download msgId(String msgId){
        this.msgId = msgId;
        return this;
    }

    public Download suffix(String suffix){
        this.suffix = suffix;
        return this;
    }

    public Download saveByDay(){
        this.saveByDay = true;
        return this;
    }

    public String getFileName(){
        return this.msgId + this.suffix;
    }

    public String getDir(WechatBot bot){
        return bot.getConfig().assetsDir() + "/" + apiURL.getDir();
    }
}
