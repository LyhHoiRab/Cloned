package org.wah.cloned.rongcloud.commons.utils;

import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.wah.cloned.rongcloud.commons.security.exception.RongCloudException;
import org.wah.doraemon.utils.ObjectUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.*;

public class HttpClientUtils{

    private static final Charset CHARSET = Charset.forName("UTF-8");

    private static final String APP_KEY = "RC-App-Key";
    private static final String NONCE = "RC-Nonce";
    private static final String TIMESTAMP = "RC-Timestamp";
    private static final String SIGNATURE = "RC-Signature";

    private static CloseableHttpClient createHttpClient(){
        return HttpClients.createDefault();
    }

    private static void close(CloseableHttpClient client){
        if(client != null){
            try{
                client.close();
            }catch(IOException e){
                //忽略
            }
        }
    }

    public static String post(String appKey, String appSecret, String url, Map<String, String> params){
        if(StringUtils.isBlank(appKey)){
            throw new RongCloudException("API请求失败:[appKey不能为空]");
        }
        if(StringUtils.isBlank(appSecret)){
            throw new RongCloudException("API请求失败:[appSecret不能为空]");
        }
        if(StringUtils.isBlank(url)){
            throw new RongCloudException("API请求失败:[url不能为空]");
        }

        CloseableHttpClient client = null;

        try{
            String nonce = String.valueOf(new Random().nextInt());
            String timestamp = String.valueOf(new Date().getTime());
            String signature = SignatureUtils.sign(appSecret, nonce, timestamp);

            client = createHttpClient();
            HttpPost post = new HttpPost(url);
            //设置请求签名响应头
            post.setHeader(APP_KEY, appKey);
            post.setHeader(NONCE, nonce);
            post.setHeader(TIMESTAMP, timestamp);
            post.setHeader(SIGNATURE, signature);

            if(params != null && !params.isEmpty()){
                List<NameValuePair> list = new ArrayList<NameValuePair>();

                for(Map.Entry<String, String> param : params.entrySet()){
                    list.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                }

                post.setEntity(new UrlEncodedFormEntity(list, CHARSET));
            }

            CloseableHttpResponse response = client.execute(post);
            if(response != null){
                if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                    return EntityUtils.toString(response.getEntity());
                }

                throw new RongCloudException("API请求失败:[{0}:{1}]", response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), CHARSET));
            }

            throw new RongCloudException("API请求失败:[{0}]", url);
        }catch(Exception e){
            throw new RongCloudException(e.getMessage(), e);
        }finally{
            close(client);
        }
    }

    public static String post(String appKey, String appSecret, String url, Object params){
        if(StringUtils.isBlank(appKey)){
            throw new RongCloudException("API请求失败:[appKey不能为空]");
        }
        if(StringUtils.isBlank(appSecret)){
            throw new RongCloudException("API请求失败:[appSecret不能为空]");
        }
        if(StringUtils.isBlank(url)){
            throw new RongCloudException("API请求失败:[url不能为空]");
        }

        CloseableHttpClient client = null;

        try{
            String nonce = String.valueOf(new Random().nextInt());
            String timestamp = String.valueOf(new Date().getTime());
            String signature = SignatureUtils.sign(appSecret, nonce, timestamp);

            client = createHttpClient();
            HttpPost post = new HttpPost(url);
            //设置请求签名响应头
            post.setHeader(APP_KEY, appKey);
            post.setHeader(NONCE, nonce);
            post.setHeader(TIMESTAMP, timestamp);
            post.setHeader(SIGNATURE, signature);

            if(params != null){
                String json = ObjectUtils.serialize(params);

                Type type = new TypeToken<Map<String, Object>>(){}.getType();

                Map<String, Object> toMap = ObjectUtils.deserialize(json, type);
                if(toMap != null && !toMap.isEmpty()){
                    List<NameValuePair> list = new ArrayList<NameValuePair>();

                    for(Map.Entry<String, Object> param : toMap.entrySet()){
                        list.add(new BasicNameValuePair(param.getKey(), ObjectUtils.serialize(param.getValue())));
                    }

                    post.setEntity(new UrlEncodedFormEntity(list, CHARSET));
                }
            }

            CloseableHttpResponse response = client.execute(post);
            if(response != null){
                if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                    return EntityUtils.toString(response.getEntity());
                }

                throw new RongCloudException("API请求失败:[{0}:{1}]", response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), CHARSET));
            }

            throw new RongCloudException("API请求失败:[{0}]", url);
        }catch(Exception e){
            throw new RongCloudException(e.getMessage(), e);
        }finally{
            close(client);
        }
    }
}
