package org.wah.cloned.im.tencent.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.wah.cloned.im.tencent.consts.Operation;
import org.wah.cloned.im.tencent.entity.IMMessageBody;
import org.wah.cloned.im.tencent.entity.IMUser;
import org.wah.cloned.im.tencent.security.response.IMOperationResponse;
import org.wah.cloned.im.tencent.security.response.IMResponse;
import org.wah.doraemon.security.exception.UtilsException;
import org.wah.doraemon.utils.HttpClientUtils;
import org.wah.doraemon.utils.ObjectUtils;

import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 腾讯云通讯API工具
 * https://cloud.tencent.com/document/product/269/1608
 */
public class IMUtils{

    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final String SUCCESS = "OK";
    private static final String FAIL = "FAIL";

    //账号导入
    private static final String OPEN_LOGIN = "https://console.tim.qq.com/v4/im_open_login_svc/account_import?usersig={0}&identifier={1}&sdkappid={2}&contenttype=json";
    //账号批量导入
    private static final String MULTI_OPEN_LOGIN = "https://console.tim.qq.com/v4/im_open_login_svc/multiaccount_import?usersig={0}&identifier={1}&sdkappid={2}&contenttype=json";
    //单聊
    private static final String SEND_MSG = "https://console.tim.qq.com/v4/openim/sendmsg?usersig={0}&identifier={1}&sdkappid={2}&contenttype=json";
    //30天运营数据
    private static final String APP_INFO = "https://console.tim.qq.com/v4/openconfigsvr/getappinfo?usersig={0}&identifier={1}&sdkappid={2}&contenttype=json";

    public static void openLogin(String sig, String sdkAppid, String identifier, IMUser user){
        if(StringUtils.isBlank(sig)){
            throw new UtilsException("认证签名不能为空");
        }
        if(StringUtils.isBlank(sdkAppid)){
            throw new UtilsException("腾讯云通讯应用AppId不能为空");
        }
        if(StringUtils.isBlank(identifier)){
            throw new UtilsException("腾讯云通讯应用管理员不能为空");
        }
        if(user == null || StringUtils.isBlank(user.getName())){
            throw new UtilsException("腾讯云通讯用户信息不全");
        }

        String url = MessageFormat.format(OPEN_LOGIN, sig, identifier, sdkAppid);
        //参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("Identifier", user.getName());
        params.put("Nick", user.getName());
        params.put("FaceUrl", user.getHeadImgUrl());

        CloseableHttpClient client = HttpClientUtils.createHttpsClient();
        String result = HttpClientUtils.post(client, url, params, CHARSET);

        IMResponse response = ObjectUtils.deserialize(result, IMResponse.class);

        if(response.getStatus().equalsIgnoreCase(FAIL)){
            throw new UtilsException(response.getErrorInfo());
        }
    }

    public static void multiOpenLogin(String sig, String sdkAppid, String identifier, List<String> users){
        if(StringUtils.isBlank(sig)){
            throw new UtilsException("认证签名不能为空");
        }
        if(StringUtils.isBlank(sdkAppid)){
            throw new UtilsException("腾讯云通讯应用AppId不能为空");
        }
        if(StringUtils.isBlank(identifier)){
            throw new UtilsException("腾讯云通讯应用管理员不能为空");
        }
        if(users == null || users.isEmpty()){
            throw new UtilsException("腾讯云通讯用户组信息不全");
        }

        String url = MessageFormat.format(MULTI_OPEN_LOGIN, sig, identifier, sdkAppid);
        //参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("Accounts", users);

        CloseableHttpClient client = HttpClientUtils.createHttpsClient();
        String result = HttpClientUtils.post(client, url, (Object) params, CHARSET);

        IMResponse response = ObjectUtils.deserialize(result, IMResponse.class);

        if(response.getStatus().equalsIgnoreCase(FAIL)){
            throw new UtilsException(response.getErrorInfo());
        }
    }

    public static void sendMsg(String sig, String sdkAppid, String identifier, IMMessageBody message){
        if(StringUtils.isBlank(sig)){
            throw new UtilsException("认证签名不能为空");
        }
        if(StringUtils.isBlank(sdkAppid)){
            throw new UtilsException("腾讯云通讯应用AppId不能为空");
        }
        if(StringUtils.isBlank(identifier)){
            throw new UtilsException("腾讯云通讯应用管理员不能为空");
        }
        if(message == null){
            throw new UtilsException("腾讯云通讯消息不能为空");
        }

        String url = MessageFormat.format(SEND_MSG, sig, identifier, sdkAppid);
        //时间戳
        long timestamp = new Date().getTime();
        //参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("SyncOtherMachine", 1);
        params.put("From_Account", message.getFromAccount());
        params.put("To_Account", message.getToAccount());
        params.put("MsgTimeStamp", timestamp / 1000);
        params.put("MsgRandom", Math.round(Math.random() * 4294967296L));
        params.put("MsgBody", message.getMessages());

        CloseableHttpClient client = HttpClientUtils.createHttpsClient();
        String result = HttpClientUtils.post(client, url, (Object) params, CHARSET);

        IMResponse response = ObjectUtils.deserialize(result, IMResponse.class);

        if(response.getStatus().equalsIgnoreCase(FAIL)){
            throw new UtilsException(response.getErrorInfo());
        }
    }

    public static IMOperationResponse appInfo(String sig, String sdkAppid, String identifier){
        if(StringUtils.isBlank(sig)){
            throw new UtilsException("认证签名不能为空");
        }
        if(StringUtils.isBlank(sdkAppid)){
            throw new UtilsException("腾讯云通讯应用AppId不能为空");
        }
        if(StringUtils.isBlank(identifier)){
            throw new UtilsException("腾讯云通讯应用管理员不能为空");
        }

        String url = MessageFormat.format(APP_INFO, sig, identifier, sdkAppid);
        //参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("RequestField", Operation.values());

        CloseableHttpClient client = HttpClientUtils.createHttpsClient();
        String result = HttpClientUtils.post(client, url, (Object) params, CHARSET);

        IMOperationResponse response = ObjectUtils.deserialize(result, IMOperationResponse.class);

        if(response.getErrorCode() != 0){
            throw new UtilsException(response.getErrorInfo());
        }

        return response;
    }
}
