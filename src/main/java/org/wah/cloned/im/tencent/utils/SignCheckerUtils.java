package org.wah.cloned.im.tencent.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.wah.doraemon.security.exception.UtilsException;

import java.io.BufferedReader;
import java.io.FileReader;

public class SignCheckerUtils{

    public SignCheckerUtils(){

    }

    public static String get(String sdkAppid, String identifier, String privateKeyPath){
        if(StringUtils.isBlank(sdkAppid)){
            throw new UtilsException("AppId不能为空");
        }

        if(StringUtils.isBlank(identifier)){
            throw new UtilsException("管理员账号不能为空");
        }

        if(StringUtils.isBlank(privateKeyPath)){
            throw new UtilsException("密钥路径不能为空");
        }

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource resource = resolver.getResource(privateKeyPath);

        StringBuffer sb = new StringBuffer();
        String temp = "";

        try(BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()))){
            while((temp = reader.readLine()) != null){
                sb.append(temp).append("\n");
            }

            String privateKey = sb.toString();
            SignatureUtils.GenTLSSignatureResult result = SignatureUtils.genTLSSignatureEx(Long.parseLong(sdkAppid), identifier, privateKey);

            if(StringUtils.isBlank(result.getUrlSig())){
                throw new UtilsException("签名生成失败");
            }

            return result.getUrlSig();
        }catch(Exception e){
            throw new UtilsException(e.getMessage(), e);
        }
    }

    public static boolean check(String signature, String sdkAppid, String identifier, String publicKeyPath){
        if(StringUtils.isBlank(signature)){
            throw new UtilsException("签名不能为空");
        }

        if(StringUtils.isBlank(sdkAppid)){
            throw new UtilsException("AppId不能为空");
        }

        if(StringUtils.isBlank(identifier)){
            throw new UtilsException("管理员账号不能为空");
        }

        if(StringUtils.isBlank(publicKeyPath)){
            throw new UtilsException("公钥路径不能为空");
        }

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource resource = resolver.getResource(publicKeyPath);

        StringBuffer sb = new StringBuffer();
        String temp = "";

        try(BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()))){
            while((temp = reader.readLine()) != null){
                sb.append(temp).append("\n");
            }

            String publicKey = sb.toString();
            SignatureUtils.CheckTLSSignatureResult result = SignatureUtils.checkTLSSignatureEx(signature, Long.parseLong(sdkAppid), identifier, publicKey);

            return result.getVerifyResult();
        }catch(Exception e){
            throw new UtilsException(e.getMessage(), e);
        }
    }
}
