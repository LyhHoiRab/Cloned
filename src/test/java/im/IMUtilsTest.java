package im;

import io.github.biezhi.wechat.api.constant.Config;
import io.github.biezhi.wechat.api.model.Account;
import io.github.biezhi.wechat.api.model.WeChatMessage;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wah.cloned.bot.entity.WechatBot;
import org.wah.cloned.bot.service.WechatApi;
import org.wah.cloned.commons.utils.CacheUtils;
import org.wah.cloned.core.service.service.AllocationService;
import org.wah.cloned.core.wechat.entity.WechatFriend;
import org.wah.cloned.core.wechat.service.WechatFriendService;
import org.wah.cloned.im.tencent.consts.IMMessageType;
import org.wah.cloned.im.tencent.entity.IMMessage;
import org.wah.cloned.im.tencent.entity.IMMessageBody;
import org.wah.cloned.im.tencent.entity.IMUser;
import org.wah.cloned.im.tencent.service.IMUserService;
import org.wah.cloned.im.tencent.utils.IMUtils;
import org.wah.doraemon.consts.Sex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:springbean.xml"})
@ActiveProfiles(value = "test")
public class IMUtilsTest {

    @Autowired
    private AllocationService allocationService;

    @Autowired
    private WechatFriendService wechatFriendService;

    @Autowired
    private IMUserService imUserService;

    @Test
    public void im() throws Throwable{
        //微信ID
        final String wechatId = "bc4d7d2a5fb54f678b3e7f0201f36e30";

        //创建机器人
        final WechatBot boter = new WechatBot(Config.me().autoLogin(false).showTerminal(false).autoAddFriend(false));
        WechatApi api = new WechatApi(boter);
        boter.setWechatApi(api);
        boter.setWechatId(wechatId);
        //登录
        api.login();

        CacheUtils.putBot(boter);

        TestRunnable runner = new TestRunnable(){
            @Override
            public void runTest() throws Throwable{
                while(true){
//                    if(bot.hasMessage()){
                    if(CacheUtils.hasMessage()){
//                        WeChatMessage message = bot.nextMessage();
                        WeChatMessage message = CacheUtils.nextMessage();
                        WechatBot bot = CacheUtils.getBot(message.getWechatId());
                        //查询消息好友ID
                        Account account = bot.getWechatApi().getAccountById(message.getFromUserName());

                        if(StringUtils.isBlank(account.getRemarkName())){
                            //分配客服
                            String serviceId = allocationService.getServiceByPool(bot.getWechatId());

                            //创建好友
                            WechatFriend friend = new WechatFriend();
                            friend.setNickname(account.getNickName());
                            friend.setSex(Sex.getById(account.getSex()));
                            friend.setWechatId(bot.getWechatId());
                            friend.setServiceId(serviceId);
                            wechatFriendService.save(friend);

                            //备注好友
                            bot.getWechatApi().remark(message.getFromUserName(), friend.getRemarkname());
                            account.setRemarkName(friend.getRemarkname());
                        }
                        //查询好友
                        WechatFriend friend = wechatFriendService.getByWechatIdAndRemarkname(bot.getWechatId(), account.getRemarkName());
                        if(friend == null){
                            //分配客服
                            String serviceId = allocationService.getServiceByPool(bot.getWechatId());
                            //创建好友
                            friend = new WechatFriend();
                            friend.setNickname(account.getNickName());
                            friend.setSex(Sex.getById(account.getSex()));
                            friend.setWechatId(bot.getWechatId());
                            friend.setServiceId(serviceId);
                            wechatFriendService.save(friend);
                        }

                        //客服
                        IMUser service = imUserService.getByName(friend.getServiceId());
                        //微信
                        IMUser wechat = imUserService.getByName(bot.getWechatId());
                        //管理员
                        IMUser admin = imUserService.getAdminByAppletId(service.getAppletId());
                        //消息组
                        List<IMMessage> messages = new ArrayList<IMMessage>();
                        //消息
                        IMMessageBody body = new IMMessageBody();
                        body.setFromAccount(wechat.getName());
                        body.setToAccount(service.getName());
                        body.setMessages(messages);

                        switch(message.getMsgType()){
                            case TEXT:
                                //消息
                                Map<String, String> content = new HashMap<String, String>();
                                content.put("Text", message.getText());
                                IMMessage imMessage = new IMMessage();
                                imMessage.setType(IMMessageType.TEXT);
                                imMessage.setContent(content);
                                messages.add(imMessage);
                                //转发
                                IMUtils.sendMsg(admin.getSig(), admin.getAppId(), admin.getName(), body);
                                break;
                            case EMOTICONS:
                                break;
                            case IMAGE:
                                break;
                            case VOICE:
                                break;
                            default:
                                break;
                        }

//                        bot.callBack(bot.getMapping().get(MsgType.ALL), message);
//                        bot.callBack(bot.getMapping().get(message.getMsgType()), message);
                    }
                }
            }
        };

        TestRunnable[] trs = new TestRunnable[]{runner};
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
        mttr.runTestRunnables();
    }
}
