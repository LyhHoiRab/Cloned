package org.wah.cloned.rongcloud.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.wah.cloned.rongcloud.commons.response.CodeResponse;
import org.wah.cloned.rongcloud.commons.response.OnlineStatusResponse;
import org.wah.cloned.rongcloud.commons.response.TokenResponse;
import org.wah.cloned.rongcloud.commons.security.exception.RongCloudException;
import org.wah.doraemon.utils.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户Token工具
 */
public class UserUtils{

    //获取token
    private static final String GET_TOKEN_API = "http://api.cn.ronghub.com/user/getToken.json";
    //刷新用户信息
    private static final String REFRESH_API = "http://api.cn.ronghub.com/user/refresh.json";
    //用户线上状态检查
    private static final String CHECK_ONLINE_API = "http://api.cn.ronghub.com/user/checkOnline.json";

    public UserUtils(){

    }

    /**
     * 查询用户Token
     */
    public static TokenResponse getToken(String appKey, String appSecret, String userId, String name, String portraitUri){
        if(StringUtils.isBlank(userId)){
            throw new RongCloudException("获取Token失败:[userId不能为空]");
        }
        if(StringUtils.isBlank(name)){
            throw new RongCloudException("获取Token失败:[name不能为空]");
        }
        if(StringUtils.isBlank(portraitUri)){
            throw new RongCloudException("获取Token失败:[portraitUri不能为空]");
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userId);
        params.put("name", name);
        params.put("portraitUri", portraitUri);

        String result = HttpClientUtils.post(appKey, appSecret, GET_TOKEN_API, params);
        return ObjectUtils.deserialize(result, TokenResponse.class);
    }

    /**
     * 刷新用户信息
     */
    public static CodeResponse refresh(String appKey, String appSecret, String userId, String name, String portraitUri){
        if(StringUtils.isBlank(userId)){
            throw new RongCloudException("获取Token失败:[userId不能为空]");
        }
        if(StringUtils.isBlank(name)){
            throw new RongCloudException("获取Token失败:[name不能为空]");
        }
        if(StringUtils.isBlank(portraitUri)){
            throw new RongCloudException("获取Token失败:[portraitUri]");
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userId);
        params.put("name", name);
        params.put("portraitUri", portraitUri);

        String result = HttpClientUtils.post(appKey, appSecret, REFRESH_API, params);
        return ObjectUtils.deserialize(result, CodeResponse.class);
    }

    /**
     * 查询用户线上状态
     */
    public static OnlineStatusResponse checkOnline(String appKey, String appSecret, String userId, String name, String portraitUri){
        try{
            if(StringUtils.isBlank(userId)){
                throw new RongCloudException("获取Token失败:[userId不能为空]");
            }
            if(StringUtils.isBlank(name)){
                throw new RongCloudException("获取Token失败:[name不能为空]");
            }
            if(StringUtils.isBlank(portraitUri)){
                throw new RongCloudException("获取Token失败:[portraitUri]");
            }

            Map<String, String> params = new HashMap<String, String>();
            params.put("userId", userId);
            params.put("name", name);
            params.put("portraitUri", portraitUri);

            String result = HttpClientUtils.post(appKey, appSecret, CHECK_ONLINE_API, params);
            return ObjectUtils.deserialize(result, OnlineStatusResponse.class);
        }catch(Exception e){
            throw new RongCloudException(e.getMessage(), e);
        }
    }
}
