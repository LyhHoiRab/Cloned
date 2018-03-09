package org.wah.cloned.bot.service;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import io.github.biezhi.wechat.api.client.BotClient;
import io.github.biezhi.wechat.api.constant.Constant;
import io.github.biezhi.wechat.api.constant.StateCode;
import io.github.biezhi.wechat.api.enums.AccountType;
import io.github.biezhi.wechat.api.enums.ApiURL;
import io.github.biezhi.wechat.api.enums.RetCode;
import io.github.biezhi.wechat.api.model.*;
import io.github.biezhi.wechat.api.request.BaseRequest;
import io.github.biezhi.wechat.api.request.FileRequest;
import io.github.biezhi.wechat.api.request.JsonRequest;
import io.github.biezhi.wechat.api.request.StringRequest;
import io.github.biezhi.wechat.api.response.*;
import io.github.biezhi.wechat.utils.DateUtils;
import io.github.biezhi.wechat.utils.QRCodeUtils;
import io.github.biezhi.wechat.utils.StringUtils;
import io.github.biezhi.wechat.utils.WeChatUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.wah.cloned.bot.entity.ChatLoop;
import org.wah.cloned.bot.entity.Download;
import org.wah.cloned.bot.entity.WechatBot;

import java.io.File;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.github.biezhi.wechat.api.constant.Constant.*;

@Slf4j
public class WechatApi{

    private static final Pattern UUID_PATTERN          = Pattern.compile("window.QRLogin.code = (\\d+); window.QRLogin.uuid = \"(\\S+?)\";");
    private static final Pattern CHECK_LOGIN_PATTERN   = Pattern.compile("window.code=(\\d+)");
    private static final Pattern PROCESS_LOGIN_PATTERN = Pattern.compile("window.redirect_uri=\"(\\S+)\";");
    private static final Pattern SYNC_CHECK_PATTERN    = Pattern.compile("window.synccheck=\\{retcode:\"(\\d+)\",selector:\"(\\d+)\"}");

    private String uuid;
    private boolean logging;
    private int memberCount;
    private WechatBot bot;
    private BotClient client;

    /**
     * 所有账号
     */
    @Getter
    private Map<String, Account> accountMap = new HashMap<>();

    /**
     * 特殊账号
     */
    @Getter
    private List<Account> specialUsersList = Collections.EMPTY_LIST;

    /**
     * 公众号、服务号
     */
    @Getter
    private List<Account> publicUsersList = Collections.EMPTY_LIST;

    /**
     * 好友列表
     */
    @Getter
    private List<Account> contactList = Collections.EMPTY_LIST;

    /**
     * 群组
     */
    @Getter
    private List<Account> groupList = Collections.EMPTY_LIST;

    /**
     * 群UserName列表
     */
    @Getter
    private Set<String> groupUserNames = new HashSet<String>();

    //心跳包线程
    private Thread thread;

    public WechatApi(WechatBot bot){
        this.bot = bot;
        this.client = bot.getBotClient();
    }

    public void login(){
        if(bot.isRunning() || logging){
            log.error("微信已登陆");
            return;
        }

        this.logging = true;

        while(logging){
            this.uuid = pushLogin();

            if(this.uuid == null){
                while(getUUID() == null){
                    DateUtils.sleep(100);
                }

                this.getQrImage(this.uuid, bot.getConfig().showTerminal());
            }

            Boolean isLoggedIn = false;
            while(isLoggedIn == null || !isLoggedIn){
                String status = this.checkLogin(this.uuid);

                if(StateCode.SUCCESS.equals(status)){
                    isLoggedIn = true;
                }else if("201".equals(status)){
                    if(isLoggedIn != null){
                        log.info("请在手机上确认登录");
                        isLoggedIn = null;
                    }
                }else if("408".equals(status)){
                    break;
                }else if(StateCode.FAIL.equals(status)){
                    break;
                }

                DateUtils.sleep(500);
            }

            if(isLoggedIn != null && isLoggedIn){
                break;
            }

            if(logging){
                log.info("登录超时，重新加载二维码");
            }
        }

        //初始化
        this.webInit();
        this.statusNotify();
        //加载好友列表
        this.loadContact(0);
        //加载群聊信息，群成员
        this.loadGroupList();

        this.startRevive();
        this.logging = false;
    }

    /**
     * 推送登录
     *
     * @return 返回uuid
     */
    private String pushLogin(){
        String uin = this.client.cookie("wxUin");
        if(StringUtils.isEmpty(uin)){
            return null;
        }

        String url = String.format("%s/cgi-bin/mmwebwx-bin/webwxpushloginurl?uin=%s", Constant.BASE_URL, uin);

        JsonResponse jsonResponse = this.client.send(new JsonRequest(url));
        return jsonResponse.getString("uuid");
    }

    /**
     * 获取UUID
     *
     * @return 返回uuid
     */
    private String getUUID(){
        // 登录
        ApiResponse response = this.client.send(new StringRequest("https://login.weixin.qq.com/jslogin").add("appid", "wx782c26e4c19acffb").add("fun", "new"));

        Matcher matcher = UUID_PATTERN.matcher(response.getRawBody());
        if(matcher.find() && StateCode.SUCCESS.equals(matcher.group(1))){
            this.uuid = matcher.group(2);
        }
        return this.uuid;
    }

    /**
     * 读取二维码图片
     *
     * @param uuid         二维码uuid
     * @param terminalShow 是否在终端显示输出
     */
    private void getQrImage(String uuid, boolean terminalShow){
        String uid    = null != uuid ? uuid : this.uuid;
        String imgDir = bot.getConfig().assetsDir();

        FileResponse fileResponse = this.client.download(new FileRequest(String.format("%s/qrcode/%s", Constant.BASE_URL, uid)));

        InputStream inputStream = fileResponse.getInputStream();
        File qrCode = WeChatUtils.saveFile(inputStream, imgDir, "qrcode.png");
        DateUtils.sleep(200);

        try{
            QRCodeUtils.showQrCode(qrCode, terminalShow);
        }catch(Exception e){
            this.getQrImage(uid, terminalShow);
        }
    }

    /**
     * 检查是否登录
     *
     * @param uuid 二维码uuid
     * @return 返回登录状态码
     */
    private String checkLogin(String uuid) {
        String uid  = null != uuid ? uuid : this.uuid;
        String url  = String.format("%s/cgi-bin/mmwebwx-bin/login", Constant.BASE_URL);
        Long time = System.currentTimeMillis();

        ApiResponse response = this.client.send(new StringRequest(url)
                .add("loginicon", true)
                .add("uuid", uid)
                .add("tip", "1")
                .add("_", time)
                .add("r", (int) (-time / 1000) / 1579)
                .timeout(30));

        Matcher matcher = CHECK_LOGIN_PATTERN.matcher(response.getRawBody());
        if(matcher.find()){
            if(StateCode.SUCCESS.equals(matcher.group(1))){
                if(!this.processLoginSession(response.getRawBody())){
                    return StateCode.FAIL;
                }
                return StateCode.SUCCESS;
            }
            return matcher.group(1);
        }
        return StateCode.FAIL;
    }

    /**
     * 处理登录session
     *
     * @param loginContent 登录text
     * @return 返回是否处理成功
     */
    private boolean processLoginSession(String loginContent){
        LoginSession loginSession = bot.getSession();
        Matcher matcher = PROCESS_LOGIN_PATTERN.matcher(loginContent);

        if(matcher.find()){
            loginSession.setUrl(matcher.group(1));
        }

        ApiResponse response = this.client.send(new StringRequest(loginSession.getUrl()).noRedirect());
        loginSession.setUrl(loginSession.getUrl().substring(0, loginSession.getUrl().lastIndexOf("/")));

        String body = response.getRawBody();

        List<String> fileUrl = new ArrayList<>();
        List<String> syncUrl = new ArrayList<>();

        for(int i = 0; i < FILE_URL.size(); i++){
            fileUrl.add(String.format("https://%s/cgi-bin/mmwebwx-bin", FILE_URL.get(i)));
            syncUrl.add(String.format("https://%s/cgi-bin/mmwebwx-bin", WEB_PUSH_URL.get(i)));
        }

        boolean flag = false;
        for(int i = 0; i < FILE_URL.size(); i++){
            String indexUrl = INDEX_URL.get(i);
            if(loginSession.getUrl().contains(indexUrl)){
                loginSession.setFileUrl(fileUrl.get(i));
                loginSession.setSyncUrl(syncUrl.get(i));
                flag = true;
                break;
            }
        }
        if(!flag){
            loginSession.setFileUrl(loginSession.getUrl());
            loginSession.setSyncUrl(loginSession.getUrl());
        }

        loginSession.setDeviceId("e" + String.valueOf(System.currentTimeMillis()));

        BaseRequest baseRequest = new BaseRequest();
        loginSession.setBaseRequest(baseRequest);

        loginSession.setSKey(WeChatUtils.match("<skey>(\\S+)</skey>", body));
        loginSession.setWxSid(WeChatUtils.match("<wxsid>(\\S+)</wxsid>", body));
        loginSession.setWxUin(WeChatUtils.match("<wxuin>(\\S+)</wxuin>", body));
        loginSession.setPassTicket(WeChatUtils.match("<pass_ticket>(\\S+)</pass_ticket>", body));

        baseRequest.setSkey(loginSession.getSKey());
        baseRequest.setSid(loginSession.getWxSid());
        baseRequest.setUin(loginSession.getWxUin());
        baseRequest.setDeviceID(loginSession.getDeviceId());

        return true;
    }

    /**
     * web 初始化
     */
    private void webInit(){
        log.info("微信初始化...");
        int r = (int) (-System.currentTimeMillis() / 1000) / 1579;
        String url = String.format("%s/webwxinit?r=%d&pass_ticket=%s", bot.getSession().getUrl(), r, bot.getSession().getPassTicket());

        JsonResponse response = this.client.send(new JsonRequest(url).post().jsonBody().add("BaseRequest", bot.getSession().getBaseRequest()));

        WebInitResponse webInitResponse = response.parse(WebInitResponse.class);

        List<Account> contactList = webInitResponse.getContactList();
        this.syncRecentContact(contactList);

        Account account = webInitResponse.getAccount();
        SyncKey syncKey = webInitResponse.getSyncKey();

        bot.getSession().setInviteStartCount(webInitResponse.getInviteStartCount());
        bot.getSession().setAccount(account);
        bot.getSession().setUserName(account.getUserName());
        bot.getSession().setNickName(account.getNickName());
        bot.getSession().setSyncKey(syncKey);
    }

    /**
     * 同步最近联系人
     * <p>
     * 避免新建群聊无法同步
     *
     * @param contactList 联系人列表
     */
    public void syncRecentContact(List<Account> contactList){
        if(contactList != null && contactList.size() > 0){
            for(Account account : contactList){
                accountMap.put(account.getUserName(), account);
            }
        }
    }

    /**
     * 开启状态通知
     */
    private void statusNotify(){
        log.info("开启状态通知");

        String url = String.format("%s/webwxstatusnotify?lang=zh_CN&pass_ticket=%s", bot.getSession().getUrl(), bot.getSession().getPassTicket());

        this.client.send(new JsonRequest(url).post().jsonBody()
                                                    .add("BaseRequest", bot.getSession().getBaseRequest())
                                                    .add("Code", 3)
                                                    .add("FromUserName", bot.getSession().getUserName())
                                                    .add("ToUserName", bot.getSession().getUserName())
                                                    .add("ClientMsgId", System.currentTimeMillis() / 1000));
    }

    /**
     * 加载联系人信息
     *
     * @param seq 默认为0，当一次无法加载完全则大于0
     */
    public void loadContact(int seq){
        log.info("开始获取联系人信息");

        while(true){
            String url = String.format("%s/webwxgetcontact?r=%s&seq=%s&skey=%s", bot.getSession().getUrl(), System.currentTimeMillis(), seq, bot.getSession().getSKey());

            JsonResponse response = this.client.send(new JsonRequest(url).jsonBody());

            JsonObject jsonObject = response.toJsonObject();
            seq = jsonObject.get("Seq").getAsInt();

            this.memberCount += jsonObject.get("MemberCount").getAsInt();
            List<Account> memberList = WeChatUtils.fromJson(WeChatUtils.toJson(jsonObject.getAsJsonArray("MemberList")), new TypeToken<List<Account>>(){});

            for(Account account : memberList){
                if(account.getUserName() != null){
                    accountMap.put(account.getUserName(), account);
                }
            }
            //查看seq是否为0，0表示好友列表已全部获取完毕，若大于0，则表示好友列表未获取完毕，当前的字节数（断点续传）
            if(seq == 0){
                break;
            }
        }

        this.contactList = new ArrayList<Account>(this.getAccountByType(AccountType.TYPE_FRIEND));

        this.publicUsersList = new ArrayList<Account>(this.getAccountByType(AccountType.TYPE_MP));
        this.specialUsersList = new ArrayList<Account>(this.getAccountByType(AccountType.TYPE_SPECIAL));

        Set<Account> groupAccounts = this.getAccountByType(AccountType.TYPE_GROUP);
        for(Account groupAccount : groupAccounts){
            groupUserNames.add(groupAccount.getUserName());
        }
    }

    /**
     * 根据账号类型筛选
     *
     * @param accountType 账户类型
     * @return 返回筛选后的账户列表
     */
    public Set<Account> getAccountByType(AccountType accountType){
        Set<Account> accountSet = new HashSet<Account>();

        for(Account account : accountMap.values()){
            if(account.getAccountType().equals(accountType)){
                accountSet.add(account);
            }
        }
        return accountSet;
    }

    /**
     * 加载群信息
     */
    public void loadGroupList(){
        log.info("加载群聊信息");

        //群账号
        List<Map<String, String>> list = new ArrayList<>(groupUserNames.size());

        for(String groupUserName : groupUserNames){
            Map<String, String> map = new HashMap<String, String>(2);
            map.put("UserName", groupUserName);
            map.put("EncryChatRoomId", "");
            list.add(map);
        }

        String url = String.format("%s/webwxbatchgetcontact?type=ex&r=%s&pass_ticket=%s", bot.getSession().getUrl(), System.currentTimeMillis() / 1000, bot.getSession().getPassTicket());

        // 加载群信息
        JsonResponse jsonResponse = this.client.send(new JsonRequest(url).post().jsonBody()
                .add("BaseRequest", bot.getSession().getBaseRequest())
                .add("Count", groupUserNames.size())
                .add("List", list));

        this.groupList = WeChatUtils.fromJson(WeChatUtils.toJson(jsonResponse.toJsonObject().getAsJsonArray("ContactList")), new TypeToken<List<Account>>(){});
    }

    /**
     * 开启一个县城接收监听
     */
    private void startRevive(){
        bot.setRunning(true);
        thread = new Thread(new ChatLoop(bot));
        thread.setName("wechat-listener");
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * 心跳检查
     *
     * @return SyncCheckRet
     */
    public SyncCheckRet syncCheck(){
        String url = String.format("%s/synccheck", bot.getSession().getSyncOrUrl());

        try{
            ApiResponse response = this.client.send(new StringRequest(url)
                                                        .add("r", System.currentTimeMillis())
                                                        .add("skey", bot.getSession().getSKey())
                                                        .add("sid", bot.getSession().getWxSid())
                                                        .add("uin", bot.getSession().getWxUin())
                                                        .add("deviceid", bot.getSession().getDeviceId())
                                                        .add("synckey", bot.getSession().getSyncKeyStr())
                                                        .add("_", System.currentTimeMillis())
                                                        .timeout(30));

            Matcher matcher = SYNC_CHECK_PATTERN.matcher(response.getRawBody());
            if(matcher.find()){
                if(!"0".equals(matcher.group(1))){
                    return new SyncCheckRet(RetCode.parse(Integer.valueOf(matcher.group(1))), 0);
                }
                return new SyncCheckRet(RetCode.parse(Integer.valueOf(matcher.group(1))), Integer.valueOf(matcher.group(2)));
            }
            return new SyncCheckRet(RetCode.UNKNOWN, 0);
        }catch(Exception e){
            if(e instanceof SocketTimeoutException){
                log.error("心跳检查超时");
                return syncCheck();
            }
            log.error("心跳检查出错", e);
            return new SyncCheckRet(RetCode.UNKNOWN, 0);
        }
    }

    /**
     * 退出登录
     */
    public void logout(){
        if(bot.isRunning()){
            String url = String.format("%s/webwxlogout", bot.getSession().getUrl());

            this.client.send(new StringRequest(url)
                                    .add("redirect", 1)
                                    .add("type", 1)
                                    .add("sKey", bot.getSession().getSKey()));
            bot.setRunning(false);
        }

        this.logging = false;
        this.client.cookies().clear();
        String file = bot.getConfig().assetsDir() + "/login.json";
        new File(file).delete();

        if(thread.isAlive()){
            thread.stop();
        }
    }

    /**
     * 获取最新消息
     *
     * @return WebSyncResponse
     */
    public WebSyncResponse webSync(){
        String url = String.format("%s/webwxsync?sid=%s&sKey=%s&passTicket=%s", bot.getSession().getUrl(), bot.getSession().getWxSid(), bot.getSession().getSKey(), bot.getSession().getPassTicket());

        JsonResponse response = this.client.send(new JsonRequest(url).post().jsonBody()
                                                                            .add("BaseRequest", bot.getSession().getBaseRequest())
                                                                            .add("SyncKey", bot.getSession().getSyncKey())
                                                                            .add("rr", ~(System.currentTimeMillis() / 1000)));

        WebSyncResponse webSyncResponse = response.parse(WebSyncResponse.class);
        if(!webSyncResponse.success()){
            log.error("获取消息失败");
            return webSyncResponse;
        }

        bot.getSession().setSyncKey(webSyncResponse.getSyncKey());
        return webSyncResponse;
    }

    /**
     * 处理新消息
     *
     * @param messages 要处理的消息列表
     */
    public List<WeChatMessage> handleMsg(List<Message> messages){
        if(messages != null && messages.size() > 0){

            List<WeChatMessage> weChatMessages = new ArrayList<WeChatMessage>(messages.size());
            boolean hashNewMsg = false;

            for(Message message : messages){
                WeChatMessage weChatMessage = this.processMsg(message);
                if(null != weChatMessage){
                    weChatMessages.add(weChatMessage);
                    hashNewMsg = true;
                }
            }

            return weChatMessages;
        }
        return null;
    }

    private WeChatMessage processMsg(Message message){
        Integer type = message.getType();
        String name = this.getUserRemarkName(message.getFromUserName());
        String msgId = message.getId();
        String content = message.getContent();

        //不处理自己发的消息
        if(message.getFromUserName().equals(bot.getSession().getUserName())){
            return null;
        }

        if(message.isGroup()){
            //如果本地缓存的群名列表没有当前群，则添加进去，下次更新使用
            if(message.getFromUserName().contains(GROUP_IDENTIFY) && !groupUserNames.contains(message.getFromUserName())){
                this.groupUserNames.add(message.getFromUserName());
            }

            if (message.getToUserName().contains(GROUP_IDENTIFY) && !groupUserNames.contains(message.getToUserName())){
                this.groupUserNames.add(message.getToUserName());
            }

            if(content.contains(GROUP_BR)){
                content = content.substring(content.indexOf(GROUP_BR) + 6);
            }
        }

        content = WeChatUtils.formatMsg(content);

        WeChatMessage.WeChatMessageBuilder weChatMessageBuilder = WeChatMessage.builder().raw(message)
                                                                                        .id(message.getId())
                                                                                        .fromUserName(message.getFromUserName())
                                                                                        .toUserName(message.getToUserName())
                                                                                        .mineUserName(bot.getSession().getUserName())
                                                                                        .mineNickName(bot.getSession().getNickName())
                                                                                        .msgType(message.msgType())
                                                                                        .text(content);

        Account fromAccount = this.getAccountById(message.getFromUserName());

        if(fromAccount == null){

        }else{
            weChatMessageBuilder.fromNickName(fromAccount.getNickName()).fromRemarkName(fromAccount.getRemarkName());
        }

        switch(message.msgType()){
            case TEXT:
                //被艾特的消息
                if(content.startsWith("@" + bot.getSession().getNickName())){
                    content = content.substring(content.indexOf(" "));
                }
                //位置消息
                if(content.contains(LOCATION_IDENTIFY)){
                    int pos = content.indexOf(":");
                    content = content.substring(0, pos);
                    weChatMessageBuilder.isLocation(true).text(content);
                }
                return weChatMessageBuilder.text(content).build();
            //聊天图片
            case IMAGE:
                String imgPath = this.downloadImg(msgId);
                return weChatMessageBuilder.imagePath(imgPath).build();
            //语音
            case VOICE:
                String voicePath = this.downloadVoice(msgId);
                return weChatMessageBuilder.voicePath(voicePath).build();
            //好友请求
            case ADD_FRIEND:
                return weChatMessageBuilder.text(message.getRecommend().getContent()).build();
            //名片
            case PERSON_CARD:
                return weChatMessageBuilder.recommend(message.getRecommend()).build();
            //视频
            case VIDEO:
                String videoPath = this.downloadVideo(msgId);
                return weChatMessageBuilder.videoPath(videoPath).build();
            //动画表情
            case EMOTICONS:
                String imgUrl = this.searchContent("cdnurl", content);
                return weChatMessageBuilder.imagePath(imgUrl).build();
            //分享
            case SHARE:
                String shareUrl = message.getUrl();
                return weChatMessageBuilder.text(shareUrl).build();
            //联系人初始化
            case CONTACT_INIT:
                return null;
            // 系统消息
            case SYSTEM:
                break;
            //撤回消息
            case REVOKE_MSG:
                return weChatMessageBuilder.build();
            default:
                break;
        }
        return weChatMessageBuilder.build();
    }

    private String getUserRemarkName(String id){
        String name = id.contains("@@") ? "未知群" : "陌生人";

        if(id.equals(this.bot.getSession().getUserName())){
            return this.bot.getSession().getNickName();
        }

        Account account = accountMap.get(id);
        if(account != null){
            return name;
        }

        String nickName = StringUtils.isNotEmpty(account.getRemarkName()) ? account.getRemarkName() : account.getNickName();
        return StringUtils.isNotEmpty(nickName) ? nickName : name;
    }

    /**
     * 根据UserName查询Account
     *
     * @param id 用户UserName唯一标识
     * @return 返回找到的账户
     */
    public Account getAccountById(String id){
        return accountMap.get(id);
    }

    /**
     * 下载图片到本地
     *
     * @param msgId 图片消息id
     * @return 返回图片本地路径
     */
    private String downloadImg(String msgId){
        return this.downloadFile(new Download(ApiURL.IMAGE, bot.getSession().getUrl(), msgId, bot.getSession().getSKey()).msgId(msgId).saveByDay().suffix("jpg"));
    }

    public String downloadFile(Download download){
        String url = String.format(download.getApiURL().getUrl(), download.getParams());

        FileResponse response = this.client.download(new FileRequest(url));
        InputStream  inputStream = response.getInputStream();

        String id  = download.getFileName();
//        String dir = download.getDir(bot);
        String dir = "C:\\Users\\xiuxiu\\Desktop\\wechatTest";
        return WeChatUtils.saveFileByDay(inputStream, dir, id, download.isSaveByDay()).getPath();
    }

    /**
     * 下载音频到本地
     *
     * @param msgId 音频消息id
     * @return 返回音频本地路径
     */
    public String downloadVoice(String msgId){
        return this.downloadFile(new Download(ApiURL.VOICE, bot.getSession().getUrl(), msgId, bot.getSession().getSKey()).msgId(msgId).saveByDay().suffix(".mp3"));
    }

    /**
     * 下载视频到本地
     *
     * @param msgId 视频消息id
     * @return 返回视频本地路径
     */
    public String downloadVideo(String msgId){
        return this.downloadFile(new Download(ApiURL.VIDEO, bot.getSession().getUrl(), msgId, bot.getSession().getSKey()).msgId(msgId).saveByDay());
    }

    private String searchContent(String key, String content){
        String r = WeChatUtils.match(key + "\\s?=\\s?\"([^\"<]+)\"", content);
        if(StringUtils.isNotEmpty(r)){
            return r;
        }

        r = WeChatUtils.match(String.format("<%s>([^<]+)</%s>", key, key), content);
        if(StringUtils.isNotEmpty(r)){
            return r;
        }

        r = WeChatUtils.match(String.format("<%s><\\!\\[CDATA\\[(.*?)\\]\\]></%s>", key, key), content);
        if(StringUtils.isNotEmpty(r)){
            return r;
        }

        return "";
    }

    /**
     * 好友验证
     */
    public boolean verify(Recommend recommend) {
        String url = String.format("%s/webwxverifyuser?r=%s&lang=zh_CN&pass_ticket=%s", bot.getSession().getUrl(), System.currentTimeMillis() / 1000, bot.getSession().getPassTicket());

        List<Map<String, Object>> verifyUserList = new ArrayList<>();
        Map<String, Object> verifyUser = new HashMap<>(2);
        verifyUser.put("Value", recommend.getUserName());
        verifyUser.put("VerifyUserTicket", recommend.getTicket());
        verifyUserList.add(verifyUser);

        JsonResponse response = client.send(new JsonRequest(url).post().jsonBody()
                .add("BaseRequest", bot.getSession().getBaseRequest())
                .add("Opcode", 3)
                .add("VerifyUserListSize", 1)
                .add("VerifyUserList", verifyUserList)
                .add("VerifyContent", "")
                .add("SceneListCount", 1)
                .add("SceneList", Arrays.asList(33))
                .add("skey", bot.getSession().getSyncKeyStr()));

        return null != response && response.success();
    }

    /**
     * 备注好友
     */
    public void remark(String username, String remark){
        JsonResponse response = client.send(new JsonRequest("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxoplog").post().jsonBody()
                .add("BaseRequest", bot.getSession().getBaseRequest())
                .add("UserName", username)
                .add("CmdId", 2)
                .add("RemarkName", remark));

        if(!response.success()){
            log.error("获取消息失败");
        }
    }
}
