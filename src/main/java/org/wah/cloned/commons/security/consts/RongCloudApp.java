package org.wah.cloned.commons.security.consts;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RongCloudApp{

    public static String appKey;
    public static String appSecret;

    public String getAppKey(){
        return appKey;
    }

    @Value("${rongcloud.app.key}")
    public void setAppKey(String appKey){
        if(StringUtils.isBlank(this.appKey)){
            this.appKey = appKey;
        }
    }

    public String getAppSecret(){
        return appSecret;
    }

    @Value("${rongcloud.app.secret}")
    public void setAppSecret(String appSecret){
        if(StringUtils.isBlank(this.appSecret)){
            this.appSecret = appSecret;
        }
    }
}
