package org.wah.cloned.rongcloud.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.wah.cloned.rongcloud.commons.security.exception.RongCloudException;
import org.wah.doraemon.utils.SHAUtils;

/**
 * 融云签名校验工具
 */
public class SignatureUtils{

    public SignatureUtils(){

    }

    /**
     * 生成签名
     */
    public static String sign(String appSecret, String nonce, String timestamp){
        if(StringUtils.isBlank(appSecret)){
            throw new RongCloudException("签名生成失败:[appSecret不能为空]");
        }

        if(StringUtils.isBlank(nonce)){
            throw new RongCloudException("签名生成失败:[nonce不能为空]");
        }

        if(StringUtils.isBlank(timestamp)){
            throw new RongCloudException("签名生成失败:[timestamp不能为空]");
        }

        return SHAUtils.bySHA1(appSecret + nonce + timestamp, false);
    }

    /**
     * 校验签名
     */
    public static boolean validate(String signature, String appSecret, String nonce, String timestamp){
        if(StringUtils.isBlank(signature)){
            throw new RongCloudException("签名校验失败:[signature不能为空]");
        }

        String sign = sign(appSecret, nonce, timestamp);

        return signature.equalsIgnoreCase(sign);
    }
}
