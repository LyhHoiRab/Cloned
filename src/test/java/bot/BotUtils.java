package bot;

import io.github.biezhi.wechat.api.constant.Config;
import io.github.biezhi.wechat.api.enums.MsgType;
import io.github.biezhi.wechat.api.model.WeChatMessage;
import org.wah.cloned.bot.entity.WechatBot;
import org.wah.cloned.bot.service.WechatApi;

public class BotUtils{

    public static void main(String[] args){
//        final WechatBot bot = new WechatBot(Config.me().autoLogin(false).showTerminal(false).autoAddFriend(false));
//        WechatApi api = new WechatApi(bot);
//        bot.setWechatApi(api);
//        api.login();
//
//        Thread msgHandler = new Thread(
//            new Runnable(){
//
//                @Override
//                public void run(){
//                    while(true){
//                        if(bot.hasMessage()){
//                            WeChatMessage weChatMessage = bot.nextMessage();
//                            bot.callBack(bot.getMapping().get(MsgType.ALL), weChatMessage);
//                            bot.callBack(bot.getMapping().get(weChatMessage.getMsgType()), weChatMessage);
//                        }
//                    }
//                }
//            }
//        );
//
//        msgHandler.start();
    }
}
