package org.wah.cloned.bot.entity;

import io.github.biezhi.wechat.api.annotation.Bind;
import io.github.biezhi.wechat.api.client.BotClient;
import io.github.biezhi.wechat.api.constant.Config;
import io.github.biezhi.wechat.api.enums.AccountType;
import io.github.biezhi.wechat.api.enums.MsgType;
import io.github.biezhi.wechat.api.model.HotReload;
import io.github.biezhi.wechat.api.model.LoginSession;
import io.github.biezhi.wechat.api.model.WeChatMessage;
import io.github.biezhi.wechat.exception.WeChatException;
import io.github.biezhi.wechat.utils.OkHttpUtils;
import io.github.biezhi.wechat.utils.WeChatUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import org.wah.cloned.bot.service.WechatApi;
import org.wah.cloned.commons.security.consts.CacheParamName;
import org.wah.cloned.commons.security.context.ApplicationContextUtils;
import org.wah.cloned.commons.utils.CacheUtils;
import org.wah.doraemon.utils.RedisUtils;
import redis.clients.jedis.ShardedJedisPool;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class WechatBot{

    @Getter
    @Setter
    private String wechatId;

    @Getter
    @Setter
    private Config config;

    /**
     * 登录会话
     */
    @Getter
    @Setter
    private LoginSession session;

    @Getter
    @Setter
    private boolean running;

    /**
     * 最后一次正常检查时间戳
     */
    @Getter
    @Setter
    private long lastCheckTs;

    /**
     * 接收消息重试次数
     */
    @Getter
    private final int receiveRetryCount = 5;

    /**
     * 调用HTTP请求的客户端
     */
    @Getter
    @Setter
    private BotClient botClient;

    @Getter
    @Setter
    private WechatApi wechatApi;

    /**
     * 注解绑定的函数映射
     */
    @Getter
    private final Map<MsgType, List<Invoke>> mapping = new HashMap<MsgType, List<Invoke>>(8);

    public WechatBot(Builder builder){
        this.config = builder.config;
        this.botClient = builder.botClient;
        this.session = new LoginSession();
//        init();
    }

    public WechatBot(Config config){
        this(new Builder().config(config));
    }

    private void init(){
        Method[] methods = this.getClass().getMethods();

        for(Method method : methods){
            Bind bind = method.getAnnotation(Bind.class);

            if(null == bind){
                continue;
            }
            if(method.getParameterTypes().length != 1){
                throw new WeChatException("方法 " + method.getName() + " 参数个数不对，请检查");
            }
            if(!method.getParameterTypes()[0].equals(WeChatMessage.class)){
                throw new WeChatException("方法 " + method.getName() + " 参数类型不对，请检查");
            }

            MsgType[] msgTypes = bind.msgType();
            for(MsgType msgType : msgTypes){
                List<Invoke> invokes = mapping.get(msgType);
                if(null == mapping.get(msgType)){
                    invokes = new ArrayList<>();
                }

                invokes.add(new Invoke(method, Arrays.asList(bind.accountType()), msgType));
                mapping.put(msgType, invokes);
            }
        }
    }

    public static final class Builder{

        private Config config = Config.me();
        private BotClient botClient;
        private OkHttpClient okHttpClient;

        public Builder(){
            botClient = new BotClient(client(null));
        }

        public Builder okHttpClient(OkHttpClient client){
            okHttpClient = client;
            return this;
        }

        public Builder config(Config config){
            this.config = config;
            return this;
        }

        public WechatBot build(){
            if(okHttpClient != null){
                botClient = new BotClient(okHttpClient);
            }
            return new WechatBot(this);
        }

        private static OkHttpClient client(Interceptor interceptor){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpUtils.configureToIgnoreCertificate(builder);
            if(interceptor != null){
                builder.addInterceptor(interceptor);
            }
            return builder.build();
        }
    }

    /**
     * 更新最后一次正常心跳时间
     */
    public void updateLastCheck(){
        this.lastCheckTs = System.currentTimeMillis();

        if(this.getConfig().autoLogin()){
            String file = this.getConfig().assetsDir() + "/login.json";
            WeChatUtils.writeJson(file, HotReload.build(this.getSession()));
        }
    }

    public void addMessages(List<WeChatMessage> list){
//        try{
//            if(null == messages || messages.size() == 0){
//                return;
//            }
//            for(WeChatMessage message : messages){
//                this.getMessages().put(message);
//            }
//        }catch(InterruptedException e){
//            log.error("向队列添加 Message 出错", e);
//        }

        if(list != null && !list.isEmpty()){
            ShardedJedisPool pool = (ShardedJedisPool) ApplicationContextUtils.getById("shardedJedisPool");
            RedisUtils.rpush(pool.getResource(), CacheParamName.WECHAT_MESSAGE_LIST, list);
        }
    }

    /**
     * 回调微信消息给客户端、存储器
     *
     * @param invokes 执行器
     * @param message 消息
     */
    public void callBack(List<Invoke> invokes, WeChatMessage message){
        if(null != invokes && invokes.size() > 0 && null != message){
            for(Invoke invoke : invokes){
                invoke.call(this, message);
            }
        }
    }

    /**
     * 好友消息验证
     */
    @Bind(msgType = MsgType.ADD_FRIEND)
    public void addFriend(WeChatMessage message){
        this.getWechatApi().verify(message.getRaw().getRecommend());
    }
}
